import express, { Request, Response } from 'express';
import squareClient from '../square-client';

const router = express.Router();

router.get('/', async (req: Request, res: Response) => {
    try {
      // Fetch customers using the imported Square client
      const { result: {customers} } = await squareClient.customersApi.listCustomers();
  
      // Send the customers as the response
      res.json(customers);
    } catch (error) {
      console.error('Error fetching Square locations:', error);
      res.status(500).json({ error: 'Internal Server Error' });
    }
  });

export default router;