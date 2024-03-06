import express, { Request, Response } from 'express';
import squareClient from '../square-client';
import crypto from 'crypto';

const router = express.Router();


router.post('/', async (req: Request, res: Response) => {
    try {
        const {result: {card} } = await squareClient.cardsApi.createCard({
          idempotencyKey: crypto.randomUUID(),
          sourceId: req.body.token,
          card: {
            customerId: req.body.customerId,
            billingAddress: {
              addressLine1: "500 Electric Ave",
              locality: "New York",
              administrativeDistrictLevel1: "NY",
              postalCode: req.body.postalCode,
              country: "US",
            }
        }
        });
        console.log('Card created:', card);
        res.json(card);
      } catch(error) {
        console.error('Error creating card:', error);
        res.status(500).json({ error: 'Internal Server Error' });
      }
  });

  export default router;