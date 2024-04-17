import { useEffect, useState } from "react";
import SubscriptionsTable from "./SubscriptionsTable";
import { useParams } from "react-router-dom";
import { FetchCustomer, FetchSubscriptions } from "../../utils/apiQueries";
import { CustomerData } from "../../components/Customers";
import { SubscriptionPlanData } from "../../components/SubscriptionPlans";
import CustomerTile from "../../components/Customers/CustomerTile";
import ComponentLayout from "../../components/ComponentLayout";

interface CustomerOverviewProps {}

const CustomerOverview: React.FC<CustomerOverviewProps> = () => {
  const { customerId } = useParams();
  const [customer, setCustomer] = useState<CustomerData>();
  const [subscriptions, setSubscriptions] = useState<SubscriptionPlanData[]>(
    [],
  );
  const [isCustomerLoading, setIsCustomerLoading] = useState<boolean>(true);
  const [isSubscriptionsLoading, setIsSubscriptionsLoading] =
    useState<boolean>(true);

  useEffect(() => {
    const getCustomer = async () => {
      try {
        if (!customerId) return;
        const customer = await FetchCustomer({ customerId });
        setIsCustomerLoading(false);
        setCustomer(customer);
      } catch (error) {
        console.error("Error fetching customer data:", error);
      }
    };
    const getSubscriptions = async () => {
      try {
        if (!customerId) return;
        const subscriptions = await FetchSubscriptions({ customerId });
        setSubscriptions(subscriptions);
        setIsSubscriptionsLoading(false);
      } catch (error) {
        console.error("Error fetching subscriptions:", error);
      }
    };
    getCustomer();
    getSubscriptions();
  }, [customerId]);

  if (!customerId) {
    return <div>Missing customer ID</div>;
  }
  return (
    <div className="flex flex-col items-center">
      <ComponentLayout title="src/routes/CustomerOverview/index.tsx">
        <div className="flex items-start">
          <CustomerTile
            customer={customer}
            isActionable={false}
            isLoading={isCustomerLoading}
          />
          <SubscriptionsTable
            subscriptions={subscriptions}
            isLoading={isSubscriptionsLoading}
          />
        </div>
      </ComponentLayout>
    </div>
  );
};

export default CustomerOverview;
