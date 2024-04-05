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
  // More information on subscribing and order templates: https://developer.squareup.com/docs/subscriptions-api/manage-subscriptions#phases-and-order-templates
  router.post('/', async (req: Request, res: Response) => {
    let locationId = ""
    let templateOrderId = "";
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

    let phases: any = []
    try{
      const {result : {object}} = await squareClient.catalogApi.retrieveCatalogObject(req.body.subscriptionVariationId)
      if (object && object.subscriptionPlanVariationData && object.subscriptionPlanVariationData.phases)
      phases = object.subscriptionPlanVariationData.phases
    } catch(e) {
      console.error('Error retrieving catalog object: ', e)
      res.status(500).json({ error: 'Internal Server Error' });
    }
    // create a draft order with Square for each phase in the subscription
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
      if(order && order.id) {
        templateOrderId = order.id
      }
    } catch (error) {
      console.error('Error creating subscription:', error);
      res.status(500).json({ error: 'Internal Server Error' });
    }
  
    // create a subscription with Square
    // set our oderTemplateId on the second part of the phase
    phases[1].orderTemplateId = templateOrderId
    // remove the uid from the phase
    phases = phases.filter((phase: any) => delete(phase.uid))

    try {
      const { result: {subscription} } = await squareClient.subscriptionsApi.createSubscription({
        locationId: locationId,
        planVariationId: req.body.subscriptionVariationId,
        customerId: req.body.customerId,
        cardId: req.body.cardId,
        idempotencyKey: crypto.randomUUID(),
        phases: phases
      })
      res.json(subscription)
    } catch (error) {
      console.error('Error creating subscription:', error);
      res.status(500).json({ error: 'Internal Server Error' });
    }
  
  });

  // Search for subscriptions by customer ID
  router.post('/search', async (req: Request, res: Response) => {
    try {
      const {result: {subscriptions}} = await squareClient.subscriptionsApi.searchSubscriptions({
        query: {
          filter: {
            customerIds: [
              req.body.customerId
            ]
          }
        }
      });

      // no subscriptions found for user
      if(!subscriptions){
        res.json([])
        return
      }
      const subscriptionVariationIds: string[] = []
      const subscriptionIdInvoiceMap: {[key: string]: boolean} = {}

      if (subscriptions?.length){
        for(let i=0; i < subscriptions?.length; i++) {
          const invoiceId = subscriptions[i]?.invoiceIds?.pop()
          
          if (invoiceId && subscriptions[i].id) {
            const {result: {invoice}} = await squareClient.invoicesApi.getInvoice(invoiceId)
            if (invoice?.status === 'PAID') {
              //@ts-ignore
              subscriptionIdInvoiceMap[subscriptions[i].id] = true
            } else {
              //@ts-ignore
              subscriptionIdInvoiceMap[subscriptions[i].id] = false
            }
          }
          
          if (subscriptions[i].planVariationId) {
            //@ts-ignore
            subscriptionVariationIds.push(subscriptions[i].planVariationId)
          }
        }
      }
      
      if (subscriptionVariationIds.length > 0) {
        const { result: {objects} } = await squareClient.catalogApi.batchRetrieveCatalogObjects({
          objectIds: subscriptionVariationIds,
        })

        const final = subscriptions?.map((subscription) => {
          const subscriptionVariation = objects?.find((object) => {
            return object.id === subscription.planVariationId
          })

          return {
            ...subscription,
            name: subscriptionVariation?.subscriptionPlanVariationData?.name,
            //@ts-ignore
            paidLastInvoice: subscriptionIdInvoiceMap[subscription.id]
          }
        })
        res.json(final)
      }

    } catch (error) {
      console.error('Error searching Square Subscription Plans:', error);
      res.status(500).json({ error: 'Internal Server Error' });
    }
  });

  // Update subscription Action
  router.post('/:subscriptionId/actions/:actionId', async (req: Request, res: Response) => {
      try {
        const { result: { subscription } } = await squareClient.subscriptionsApi.deleteSubscriptionAction(req.params.subscriptionId, req.params.actionId);
        res.json(subscription);
      } catch (error) {
        console.error(`Error performing action on Square Subscription (Action ID: ${req.params.actionId}):`, error);
        res.json({ error: 'Internal Server Error' })
      }
  });
    
  // Retrieve a subscription by ID
  router.get('/:subscriptionId', async (req: Request, res: Response) => {
    try {
      const { result: {subscription} } = await squareClient.subscriptionsApi.retrieveSubscription(req.params.subscriptionId, 'actions')
      if (subscription?.planVariationId) {
        const { result: {object}} = await squareClient.catalogApi.retrieveCatalogObject(subscription.planVariationId)
        //@ts-ignore
        if (subscription?.phases?.length > 0) {
          //@ts-ignore
          for (let i=0; i < subscription.phases.length; i++) {
            if(subscription?.phases?.[i].orderTemplateId === undefined) continue
            //@ts-ignore
            const {result: {order}} = await squareClient.ordersApi.retrieveOrder(subscription.phases[i].orderTemplateId)
            //@ts-ignore
            //@ts-ignore
            subscription.phases[i].order = order
          }
          res.json({subscription, object})
        }
      } else {
        res.json({subscription: [], object: []})
      }
    } catch (error) {
      console.error('Error fetching Square Subscription:', error);
      res.status(500).json({ error: 'Internal Server Error' });
    }
  });

  // Cancel a subscription
  router.post('/:subscriptionId/cancel', async (req: Request, res: Response) => {
    try {
      const { result: {subscription} } = await squareClient.subscriptionsApi.cancelSubscription(req.params.subscriptionId)
      res.json(subscription)
    } catch (error) {
      console.error('Error canceling Square Subscription:', error);
      res.status(500).json({ error: 'Internal Server Error' });
    }
  })
  
  // Pause a subscription
  router.post('/:subscriptionId/pause', async (req: Request, res: Response) => {
    try {
      const { result: {subscription} } = await squareClient.subscriptionsApi.pauseSubscription(req.params.subscriptionId, {
        pauseCycleDuration: req.body.pauseCycleDuration,
        pauseEffectiveDate: req.body.pauseEffectiveDate,
        pauseReason: req.body.pauseReason || "",
      })
      res.json(subscription)
    } catch (error) {
      console.error('Error pausing Square Subscription:', error);
      res.status(500).json({ error: 'Internal Server Error' });
    }
  })

export default router;