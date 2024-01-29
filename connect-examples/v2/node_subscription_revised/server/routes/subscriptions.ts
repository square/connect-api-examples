import express, { Request, Response } from 'express';
import squareClient from '../square-client';
import crypto from 'crypto';

const router = express.Router();

// Request to get a list of subscription plans
router.get('/', async (req: Request, res: Response) => {
    try {
      // Fetch subscriptions using the imported Square client
      const { result: {objects} } = await squareClient.catalogApi.searchCatalogObjects({
        objectTypes: [
          "SUBSCRIPTION_PLAN"
        ]
      })
      
      // Send the subscription objects as the response
      res.json(objects);
    } catch (error) {
      console.error('Error fetching Square Subscription Plans:', error);
      res.status(500).json({ error: 'Internal Server Error' });
    }
  });

  // Request to create a subscription
  // TODO: Link to Subscription guide, quick explanation that this is a two step process
  router.post('/', async (req: Request, res: Response) => {
    let locationId = ""
    const templateOrderIds = []
    // fetch the location ID for the main location
    try {
      const { result: {location} } = await squareClient.locationsApi.retrieveLocation('main');
      if (location?.id) {
        locationId = location.id
      }
    } catch (error) {
      console.error('Error fetching Square location:', error);
      res.status(500).json({ error: 'Internal Server Error' });
    }
  
    // create a draft order with Square for each item being purchased
    // TODO: Need to actually loop on the phases that exist in the subscription plan
    for(let i=0; i < 2; i++) {
      try {
        const { result: {order} } = await squareClient.ordersApi.createOrder({
          order: {
            locationId: locationId,
            customerId: req.body.customerId,
            lineItems: [
              {
                quantity: '1',
                // Needs to be the item Variation ID
                catalogObjectId: req.body.itemIds[0]
              },
              {
                quantity: '1',
                // Needs to be the item Variation ID
                catalogObjectId: req.body.itemIds[1]
              }
            ],
            // Need to handle the case that the customer does not have an address on file
            fulfillments: [
              {
                type: 'SHIPMENT',
                state: 'PROPOSED',
                shipmentDetails: {
                  recipient: {
                    customerId: req.body.customerId
                  },
                  expectedShippedAt: new Date().toISOString()
                }
              }
            ],
            state: 'DRAFT'
          }, idempotencyKey: crypto.randomUUID()
        })
        templateOrderIds.push(order?.id)
      } catch (error) {
        console.error('Error creating subscription:', error);
        res.status(500).json({ error: 'Internal Server Error' });
      }
    }
  
    // create a subscription with Square
    try {
      const { result: {subscription} } = await squareClient.subscriptionsApi.createSubscription({
        locationId: locationId,
        planVariationId: req.body.subscriptionVariationId,
        customerId: req.body.customerId,
        idempotencyKey: req.body.idempotencyKey,
        phases: [
          {
            ordinal: BigInt(0),
            orderTemplateId: templateOrderIds[0]
          },
          {
            ordinal: BigInt(1),
            orderTemplateId: templateOrderIds[1]
          }
        ]
      })
      res.json(subscription)
    } catch (error) {
      console.error('Error creating subscription:', error);
      res.status(500).json({ error: 'Internal Server Error' });
    }
  
  });

export default router;