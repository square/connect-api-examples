import express, { Request, Response } from 'express';
import squareClient from '../square-client';

const router = express.Router();

router.post('/', async (req: Request, res: Response) => {
    const {invoiceIds} = req.body;
    if (!invoiceIds) {
        res.status(400).json({error: 'invoiceIds is required'});
        return;
    }

    // for each invoiceId, get the invoice
    const invoices = [];
    for (const invoiceId of invoiceIds) {
        try {
            const {result: {invoice}} = await squareClient.invoicesApi.getInvoice(invoiceId.toString());
            invoices.push(invoice);
        } catch (error) {
            console.error('Error fetching invoice:', error);
            res.status(500).json({error: 'Internal Server Error'});
            return;
        }
    }
    res.json(invoices);
});
    
    

export default router;