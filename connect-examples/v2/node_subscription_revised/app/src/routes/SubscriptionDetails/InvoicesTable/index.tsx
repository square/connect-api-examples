import React from 'react';
import { Table } from 'flowbite-react';
import Skeleton from '../../../components/Skeleton';
import ComponentLayout from '../../../components/ComponentLayout';
import { useEffect, useState } from 'react';

export interface Invoice {
    id: string;
    startDate: string;
    invoiceNumber: string;
    chargedThroughDate: string;
    status: string;
    publicUrl: string;
    paymentRequests: [{
        totalCompletedAmountMoney: {
            amount: number;
            currency: string;
        }
    }]
}

interface InvoicesTableProps {
  invoiceIds: string[];
}

const InvoicesTable: React.FC<InvoicesTableProps> = ({
  invoiceIds,
}) => {
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [invoices, setInvoices] = useState<Invoice[]>([]);
  
  //fetch invoices
  useEffect(() => {
    console.log('the effect is getting called: ', invoiceIds)
    // fetch invoices for all invoiceIds
    const fetchInvoices = async () => {
        try {
            const response = await fetch('/invoices', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    invoiceIds: invoiceIds
                })
            });
            const data = await response.json();
            console.log(data);
            setInvoices(data);
            setIsLoading(false);
        } catch (error) {
            console.error('Error fetching invoices:', error);
        }

    };
    fetchInvoices();
}, [])


  return <>
   {/* <ComponentLayout title="src/routes/SubscriptionDetails/InvoicesTable/index.tsx"> */}
   {isLoading && (
      <div className="max-w-[1000px] max-h-[600px] overflow-y-auto">
        <Table hoverable>
          <Table.Head>
            <Table.HeadCell>Invoice #</Table.HeadCell>
            <Table.HeadCell>Amount</Table.HeadCell>
            <Table.HeadCell>Status</Table.HeadCell>
            <Table.HeadCell>Invoice Url</Table.HeadCell>
            <Table.HeadCell>
              <span className="sr-only">Details</span>
            </Table.HeadCell>
          </Table.Head>
        </Table>
        <Skeleton />
      </div>
    )}

    {invoices.length === 0 && isLoading === false && (
      <p className="text-center text-gray-900 dark:text-white">No invoices found</p>
    )}
   

  { invoices.length > 0 && isLoading === false && (
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
  {/* </ComponentLayout> */}
    </>
}

export default InvoicesTable
