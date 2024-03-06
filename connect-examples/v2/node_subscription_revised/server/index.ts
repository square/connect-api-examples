// src/app.ts

import express from 'express';

import customerRoutes from './routes/customers'
import subscriptionRoutes from './routes/subscriptions'
import catalogRoutes from './routes/catalog'
import cardRoutes from './routes/cards'
import invoiceRoutes from './routes/invoices'

// for TypeScript & Square serialization issue
(BigInt.prototype as any).toJSON = function() {
  return this.toString()
} 

const app = express();
app.use(express.json());

app.use('/customers', customerRoutes);
app.use('/subscriptions', subscriptionRoutes);
app.use('/catalog', catalogRoutes);
app.use('/cards', cardRoutes);
app.use('/invoices', invoiceRoutes);


const PORT = process.env.PORT || 4000;

app.listen(PORT, () => {
  console.log(`Server is running on http://localhost:${PORT}`);
});
