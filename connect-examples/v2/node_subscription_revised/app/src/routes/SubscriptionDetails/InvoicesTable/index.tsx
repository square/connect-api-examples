import React from 'react';
import { Table } from 'flowbite-react';
import Skeleton from '../../../components/Skeleton';
import ComponentLayout from '../../../components/ComponentLayout';
import { Invoice } from '..';

interface InvoicesTableProps {
  invoices: Invoice[];
}

const InvoicesTable: React.FC<InvoicesTableProps> = ({
  invoices,
}) => {
  return <>
   <ComponentLayout title="src/routes/SubscriptionDetails/InvoicesTable/index.tsx">

  {invoices.length === 0 && (
    <p className="text-center text-gray-900 dark:text-white">No invoices found</p>
  )}
   

  { invoices.length > 0 && (
    <div className="max-w-[1000px] max-h-[600px] overflow-y-auto rounded border">
      <Table hoverable>
        <Table.Head>
          <Table.HeadCell>Invoice #</Table.HeadCell>
          <Table.HeadCell>Amount</Table.HeadCell>
          <Table.HeadCell>Status</Table.HeadCell>
        <Table.HeadCell>
            <span className="sr-only">Invoice URL</span>
          </Table.HeadCell>
        </Table.Head>
        <Table.Body className="divide-y">
          {invoices && invoices.map((invoice: Invoice, i:number) => {
                return <Table.Row key={i} className="bg-white dark:border-gray-700 dark:bg-gray-800">
                <Table.Cell>{invoice.invoiceNumber}</Table.Cell>
                <Table.Cell>${(invoice.paymentRequests[0].totalCompletedAmountMoney.amount / 100).toFixed(2)}</Table.Cell>
                <Table.Cell>
                    {invoice.status === 'PAID' ? <span className='text-green-500'>{invoice.status}</span> : <span className='text-red-500'>{invoice.status}</span> }
                </Table.Cell>
                <Table.Cell>
                  <p className="font-medium text-cyan-600 cursor-pointer hover:underline dark:text-cyan-500">
                    <a href={invoice.publicUrl} target="_blank">
                        Invoice URL                    
                    </a>
                  </p>
                </Table.Cell>
              </Table.Row>
              })
            }
          </Table.Body>
      </Table>
    </div>
  )}
  </ComponentLayout>
    </>
}

export default InvoicesTable
