import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import Skeleton from "../../components/Skeleton";
import { formattedDate } from "../../utils/helpers";
import {
  PauseSubscriptionSection,
  PausedInfoSection,
  CanceledInfoSection,
  CancelSubscriptionSection,
  ActionsSection,
} from "./SubscriptionSections";
import ComponentLayout from "../../components/ComponentLayout";
import InvoicesTable from "./InvoicesTable";
import { Status } from "../../components/HelperComponents/Status";

interface SubscriptionDetailsProps {}

export interface Invoice {
  id: string;
  startDate: string;
  createdAt: string;
  invoiceNumber: string;
  chargedThroughDate: string;
  status: string;
  publicUrl: string;
  paymentRequests: [
    {
      totalCompletedAmountMoney: {
        amount: number;
        currency: string;
      };
    },
  ];
}

const SubscriptionDetails: React.FC<SubscriptionDetailsProps> = () => {
  const [subscription, setSubscription] = useState<any>({});
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [subscriptionChanged, setSubscriptionChanged] =
    useState<boolean>(false);
  const [subscriptionPlanVariationData, setSubscriptionPlanVariationData] =
    useState<any>({});
  const [invoices, setInvoices] = useState<Invoice[]>([]);

  const { subscriptionId } = useParams();

  useEffect(() => {
    // Fetch subscription data
    const fetchSubscriptionData = async () => {
      let data: any = {};
      try {
        const response = await fetch("/subscriptions/" + subscriptionId);
        data = await response.json();
        setSubscription(data.subscription);
        setSubscriptionPlanVariationData(data.object);
      } catch (error) {
        console.error("Error fetching subscription data:", error);
      }
      // Fetch invoices
      try {
        const response = await fetch("/invoices", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            invoiceIds: data.subscription.invoiceIds,
          }),
        });
        const invoiceData = await response.json();
        setInvoices(invoiceData);
        setIsLoading(false);
      } catch (error) {
        console.error("Error fetching invoices:", error);
      }
    };
    fetchSubscriptionData();
  }, [subscriptionChanged, subscriptionId]);

  const checkSubscriptionStatus = () => {
    // The sub can be paused, canceled, or active
    // The actions can exist which are waiting to be executed.

    // The status is also always showing dependent on the last executed action

    // First we want to check if any actions exist
    // Actions indicate that we have not changed the status of the subscription yet
    const subscriptionStatus: any = {
      toBeCanceled: false,
      toBePaused: false,
      resumeData: {},
      toBePausedData: {},
      toBeCanceledData: {},
    };
    if (subscription?.actions?.length >= 0) {
      subscription?.actions?.forEach((action: any) => {
        if (action.type === "PAUSE") {
          subscriptionStatus.paused = true;
          subscriptionStatus.toBePausedData = action;
        }
        if (action.type === "RESUME") {
          subscriptionStatus.resumeData = action;
        }
        if (action.type === "CANCEL") {
          subscriptionStatus.canceled = true;
          subscriptionStatus.toBeCanceledData = action;
        }
      });

      if (subscriptionStatus.paused) {
        return (
          <>
            <PausedInfoSection
              toBePausedData={subscriptionStatus.toBePausedData}
              resumeData={subscriptionStatus.resumeData}
            />
            <ActionsSection
              actions={subscription.actions}
              subscriptionId={subscription.id}
              actionId={subscriptionStatus.toBePausedData.id}
              setIsLoading={setIsLoading}
              subscriptionChanged={subscriptionChanged}
              setSubscriptionChanged={setSubscriptionChanged}
            />
          </>
        );
      }

      if (subscriptionStatus.canceled) {
        return (
          <>
            <CanceledInfoSection
              toBeCanceledData={subscriptionStatus.toBeCanceledData}
            />
            <ActionsSection
              actions={subscription.actions}
              subscriptionId={subscription.id}
              actionId={subscriptionStatus.toBeCanceledData.id}
              setIsLoading={setIsLoading}
              subscriptionChanged={setSubscriptionChanged}
              setSubscriptionChanged={setSubscriptionChanged}
            />
          </>
        );
      }
    }

    switch (subscription.status) {
      // Once the subscription is canceled, there is nothing else to do
      // No need to display anything further
      case "CANCELED":
        return null;

      case "DEACTIVATED":
        return null;

      case "PAUSED":
        return (
          <>
            {subscription?.actions?.length > 0 && (
              <ActionsSection
                actions={subscription.actions}
                subscriptionId={subscription.id}
                actionId={subscriptionStatus.resumeData.id}
                setIsLoading={setIsLoading}
                subscriptionChanged={subscriptionChanged}
                setSubscriptionChanged={setSubscriptionChanged}
              />
            )}
          </>
        );
      default:
        return (
          <>
            <PauseSubscriptionSection
              subscription={subscription}
              setIsLoading={setIsLoading}
              subscriptionChanged={subscriptionChanged}
              setSubscriptionChanged={setSubscriptionChanged}
            />
            <CancelSubscriptionSection
              subscription={subscription}
              setIsLoading={setIsLoading}
              subscriptionChanged={subscriptionChanged}
              setSubscriptionChanged={setSubscriptionChanged}
            />
          </>
        );
    }
  };

  return (
    <div className="flex justify-center">
      {isLoading ? (
        <div className="bg-white min-w-[800px] p-8 mt-4">
          <Skeleton />
        </div>
      ) : (
        <div className="bg-white p-8 mt-4 min-w-[800px] max-w-[1000px] rounded">
          <ComponentLayout title="src/routes/SubscriptionDetails/SubscriptionDetails/index.tsx">
            <div className="space-y-6">
              <div>
                <h3 className="text-lg font-semibold text-gray-900">
                  {
                    subscriptionPlanVariationData.subscriptionPlanVariationData
                      .name
                  }
                </h3>
                <p className="text-sm text-gray-500">
                  <span className="font-bold text-black">Subscription ID:</span>{" "}
                  {subscription.id}
                </p>
                <p className="text-sm text-gray-500">
                  <span className="font-bold text-black">Start Date:</span>{" "}
                  {formattedDate(subscription.startDate)}
                </p>
                <p className="text-sm text-gray-500">
                  <span className="font-bold text-black">
                    Charge Through Date:
                  </span>{" "}
                  {formattedDate(subscription.chargedThroughDate)}
                </p>
                <p className="text-sm text-gray-500 mb-4">
                  <span className="font-bold text-black">Status:</span>{" "}
                  <Status status={subscription.status} />
                </p>
              </div>
              {/* Determine which section to show the user */}
              {checkSubscriptionStatus()}
              <InvoicesTable invoices={invoices} />
            </div>
          </ComponentLayout>
        </div>
      )}
    </div>
  );
};

export default SubscriptionDetails;
