import React from "react";
import { Table } from "flowbite-react";
import ComponentLayout from "../../../components/ComponentLayout";
import { Invoice } from "..";

interface InvoicesTableProps {
  invoices: Invoice[];
}

const InvoicesTable: React.FC<InvoicesTableProps> = ({ invoices }) => {
  return (
    <>
      <ComponentLayout
        title="src/routes/SubscriptionDetails/InvoicesTable/index.tsx"
        note={
          <span>
            Square subscriptions are powered by invoices - read more about it{" "}
            <a
              className="underline text-blue-600"
              href="https://developer.squareup.com/docs/subscriptions-api/subscription-billing"
              rel="noreferrer"
              target="_blank"
            >
              here
            </a>
          </span>
        }
      >
        {invoices.length === 0 && (
          <p className="text-center text-gray-900">No invoices found</p>
        )}

        {invoices.length > 0 && (
          <div className="max-w-[1000px] max-h-[600px] overflow-y-auto rounded border">
            <Table hoverable>
              <Table.Head>
                <Table.HeadCell>Invoice #</Table.HeadCell>
                <Table.HeadCell>Amount</Table.HeadCell>
                <Table.HeadCell>Status</Table.HeadCell>
                <Table.HeadCell>Created At</Table.HeadCell>
                <Table.HeadCell>
                  <span className="sr-only">Invoice URL</span>
                </Table.HeadCell>
              </Table.Head>
              <Table.Body className="divide-y">
                {invoices &&
                  invoices.map((invoice: Invoice, i: number) => {
                    return (
                      <Table.Row key={i} className="bg-white">
                        <Table.Cell>{invoice.invoiceNumber}</Table.Cell>
                        <Table.Cell>
                          $
                          {(
                            invoice.paymentRequests[0].totalCompletedAmountMoney
                              .amount / 100
                          ).toFixed(2)}
                        </Table.Cell>
                        <Table.Cell>
                          {invoice.status === "PAID" ? (
                            <span className="text-green-500">
                              {invoice.status}
                            </span>
                          ) : (
                            <span className="text-red-500">
                              {invoice.status}
                            </span>
                          )}
                        </Table.Cell>
                        <Table.Cell>
                          {new Date(invoice.createdAt).toLocaleDateString()}
                        </Table.Cell>
                        <Table.Cell>
                          <p className="font-medium text-cyan-600 cursor-pointer hover:underline">
                            <a
                              href={invoice.publicUrl}
                              rel="noreferrer"
                              target="_blank"
                            >
                              Invoice URL
                            </a>
                          </p>
                        </Table.Cell>
                      </Table.Row>
                    );
                  })}
              </Table.Body>
            </Table>
          </div>
        )}
      </ComponentLayout>
    </>
  );
};

export default InvoicesTable;
