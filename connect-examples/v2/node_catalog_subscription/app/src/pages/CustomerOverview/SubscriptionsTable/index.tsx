import { Table } from "flowbite-react";
import { useNavigate } from "react-router-dom";
import { SubscriptionPlanData } from "../../../components/SubscriptionPlans";
import Skeleton from "../../../components/Skeleton";
import ComponentLayout from "../../../components/ComponentLayout";
import { Status } from "../../../components/HelperComponents/Status";

interface SubscriptionsTableProps {
  subscriptions: SubscriptionPlanData[];
  isLoading: boolean;
}

const SubscriptionsTable: React.FC<SubscriptionsTableProps> = ({
  subscriptions,
  isLoading,
}) => {
  const navigate = useNavigate();

  return (
    <>
      <ComponentLayout title="src/routes/CustomerOverview/SubscriptionsTable/index.tsx">
        {isLoading && (
          <div className="max-w-[1000px] max-h-[600px] overflow-y-auto">
            <Table hoverable>
              <Table.Head>
                <Table.HeadCell>Subscription</Table.HeadCell>
                <Table.HeadCell>Start date</Table.HeadCell>
                <Table.HeadCell>Charged Through Date</Table.HeadCell>
                <Table.HeadCell>Status</Table.HeadCell>
                <Table.HeadCell>Billing</Table.HeadCell>
                <Table.HeadCell>
                  <span className="sr-only">Details</span>
                </Table.HeadCell>
              </Table.Head>
            </Table>
            <Skeleton />
          </div>
        )}

        {subscriptions.length === 0 && isLoading === false && (
          <p className="text-center text-gray-900">No subscriptions found</p>
        )}

        {subscriptions.length > 0 && isLoading === false && (
          <div className="max-w-[1000px] max-h-[600px] overflow-y-auto">
            <Table hoverable>
              <Table.Head>
                <Table.HeadCell>Subscription</Table.HeadCell>
                <Table.HeadCell>Start date</Table.HeadCell>
                <Table.HeadCell>Charged Through Date</Table.HeadCell>
                <Table.HeadCell>Status</Table.HeadCell>
                <Table.HeadCell>Billing</Table.HeadCell>
                <Table.HeadCell>
                  <span className="sr-only">Details</span>
                </Table.HeadCell>
              </Table.Head>
              <Table.Body className="divide-y">
                {subscriptions &&
                  subscriptions.map((subscription: any, i: number) => {
                    return (
                      <Table.Row key={i} className="bg-white">
                        <Table.Cell className="whitespace-wrap font-medium text-gray-900">
                          {subscription.name.substr(0, 20) +
                            (subscription.name.length > 20 ? "..." : "")}
                        </Table.Cell>
                        <Table.Cell>{subscription.startDate}</Table.Cell>
                        <Table.Cell>
                          {subscription.chargedThroughDate}
                        </Table.Cell>
                        <Table.Cell>
                          <Status status={subscription.status} />
                        </Table.Cell>
                        <Table.Cell>
                          {subscription.paidLastInvoice ? (
                            <p className="text-green-500">Paid</p>
                          ) : (
                            <p className="text-red-500">Overdue</p>
                          )}
                        </Table.Cell>
                        <Table.Cell>
                          <p
                            className="font-medium text-cyan-600 cursor-pointer hover:underline"
                            onClick={() => {
                              navigate(`/subscription/${subscription.id}`);
                            }}
                          >
                            Details
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

export default SubscriptionsTable;
