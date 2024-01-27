// src/app.ts

import express, { Request, Response } from 'express';
import crypto from 'crypto';
import squareClient from './square-client';

// # for TypeScript
(BigInt.prototype as any).toJSON = function() {
  return this.toString()
} 

const app = express();

app.use(express.json());

app.get('/', async (req: Request, res: Response) => {
  try {
    // Fetch locations using the imported Square client
    const { result: {locations} } = await squareClient.locationsApi.listLocations();
    
    // Extract relevant information from the locations
    const locationNames = locations?.map(location => location.name);

    // Send the location names as the response
    res.json({ locations: locationNames });
  } catch (error) {
    console.error('Error fetching Square locations:', error);
    res.status(500).json({ error: 'Internal Server Error' });
  }
});

app.get('/customers', async (req: Request, res: Response) => {
  try {
    // Fetch locations using the imported Square client
    const { result: {customers} } = await squareClient.customersApi.listCustomers();
    
    // Extract relevant information from the locations

    // Send the location names as the response
    res.json(customers);
  } catch (error) {
    console.error('Error fetching Square locations:', error);
    res.status(500).json({ error: 'Internal Server Error' });
  }
});

app.get('/subscription_plans', async (req: Request, res: Response) => {
  try {
    // Fetch locations using the imported Square client
    const { result: {objects} } = await squareClient.catalogApi.searchCatalogObjects({
      objectTypes: [
        "SUBSCRIPTION_PLAN"
      ]
    })
    
    // Extract relevant information from the locations

    // Send the location names as the response
    res.json(objects);
  } catch (error) {
    console.error('Error fetching Square Subscription Plans:', error);
    res.status(500).json({ error: 'Internal Server Error' });
  }
});

app.post('/catalog_items', async (req: Request, res: Response) => {
  try {
    // Fetch locations using the imported Square client
    const { result: {items} } = await squareClient.catalogApi.searchCatalogItems({
      categoryIds: req.body.categoryIds
    })

    const {result:{objects}} = await squareClient.catalogApi.searchCatalogObjects({
      objectTypes: [
        "IMAGE"
      ]
    })
    const final:any = []
    items?.forEach(item => {
      objects?.forEach(image => {
        if(image.id === item?.itemData?.imageIds?.[0]) {
         final.push({...item, image: image.imageData?.url})
        }
      })
    })

    // Send the location names as the response
    res.json(final);
  } catch (error) {
    console.error('Error fetching Square Catalog Items:', error);
    res.status(500).json({ error: 'Internal Server Error' });
  }
});

app.post('/create_subscription', async (req: Request, res: Response) => {
  // Get location from Square
  let locationId = ""
  const templateOrderIds = []
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

const PORT = process.env.PORT || 4000;

app.listen(PORT, () => {
  console.log(`Server is running on http://localhost:${PORT}`);
});
