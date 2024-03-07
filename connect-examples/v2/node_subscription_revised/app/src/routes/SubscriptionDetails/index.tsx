import { useState, useEffect } from "react";
import {useParams} from 'react-router-dom';
import Skeleton from "../../components/Skeleton";
import { 
  PauseSubscriptionSection, 
  PausedInfoSection,
  ResumeSection,
  CanceledInfoSection,
  CancelSubscriptionSection,
  ActionsSection
} from "./SubscriptionSections";
import ComponentLayout from "../../components/ComponentLayout";
import InvoicesTable from "./InvoicesTable";

interface SubscriptionDetailsProps {
}

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


const SubscriptionDetails: React.FC<SubscriptionDetailsProps> = ({}) => {
  const [subscription, setSubscription] = useState<any>({});
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [subscriptionChanged, setSubscriptionChanged] = useState<boolean>(false);
  const [subscriptionPlanVariationData, setSubscriptionPlanVariationData] = useState<any>({});
  const [invoices, setInvoices] = useState<Invoice[]>([]);

  const {subscriptionId} = useParams();

  useEffect(() => {
    // Fetch subscription data
    const fetchSubscriptionData = async () => {
        let data:any = {};
        try {
          const response = await fetch('/subscriptions/' + subscriptionId);
          data = await response.json();
          setSubscription(data.subscription);
          setSubscriptionPlanVariationData(data.object);
        } catch (error) {
          console.error('Error fetching subscription data:', error);
        }
        // Fetch invoices
        try {
          const response = await fetch('/invoices', {
              method: 'POST',
              headers: {
                  'Content-Type': 'application/json',
              },
              body: JSON.stringify({
                  invoiceIds: data.subscription.invoiceIds
              })
          });
          const invoiceData = await response.json();
          setInvoices(invoiceData);
          setIsLoading(false);
        } catch (error) {
            console.error('Error fetching invoices:', error);
        }

      }
    fetchSubscriptionData();
  }, [subscriptionChanged])


 

  const checkSubStatus = () => {

    // The sub can be paused, canceled, or active
    // The actions can exist which are waiting to be executed.

    // The status is also always showing dependent on the last executed action

    // Firstly Check the status, cause if the subscription status is canceled, or paused then we can
    // show the user the reactivation button, which calls for a RESUME action.


    // First we want to check if any actions exist
    if (subscription?.actions?.length >= 0) {
      const subStatus: any = {
        canceled: false,
        paused: false,
        resumeData: {},
        pauseData: {},
        cancelData: {}
      }
      subscription?.actions?.forEach((action: any) => {
        if (action.type === 'PAUSE') {
          subStatus.paused = true;
          subStatus.pauseData = action;
        }
        if (action.type === 'RESUME') {
          subStatus.resumeData = action;
        }
        if(action.type === 'CANCEL') {
          subStatus.canceled = true;
          subStatus.cancelData = action;
        }
      })

      if (subStatus.paused) {
        return <>
          <PausedInfoSection 
            pauseData={subStatus.pauseData} 
            resumeData={subStatus.resumeData}
          />
          <ActionsSection 
            actions={subscription.actions} 
            subscriptionId={subscription.id} 
            actionId={subStatus.pauseData.id} 
            setIsLoading={setIsLoading} 
            subscriptionChanged={subscriptionChanged}
            setSubscriptionChanged={setSubscriptionChanged}
          />
        </>
      }

      if (subStatus.canceled) {
        return<>
          <CanceledInfoSection cancelData={subStatus.cancelData}/>
          <ActionsSection 
            actions={subscription.actions}
            subscriptionId={subscription.id} 
            actionId={subStatus.cancelData.id}
            setIsLoading={setIsLoading} 
            subscriptionChanged={setSubscriptionChanged}
            setSubscriptionChanged={setSubscriptionChanged}
          />
        </>
        
      }
    }

    switch(subscription.status) {
      case 'CANCELED':
        return <ResumeSection 
          subscription={subscription} 
          resumeEffectiveDate={new Date().toISOString().split('T')[0]}
          subscriptionChanged={subscriptionChanged}
          setSubscriptionChanged={setSubscriptionChanged}
        />
      case 'DEACTIVATED':
        return <ResumeSection 
          subscription={subscription} 
          resumeEffectiveDate={new Date().toISOString().split('T')[0]}
          subscriptionChanged={subscriptionChanged}
          setSubscriptionChanged={setSubscriptionChanged}
        />
      case 'PAUSED':
        return <ResumeSection 
          subscription={subscription}
          subscriptionChanged={subscriptionChanged}
          setSubscriptionChanged={setSubscriptionChanged}
        />
      case 'ACTIVE':
        return <>
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
      default:
        return <>
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
    }
  }

  return (
    <div className="flex justify-center">
    {isLoading ?
        <div className="bg-white min-w-[800px] p-8 mt-4">
        <Skeleton />
    </div>
    :
    <div className="bg-white p-8 mt-4 min-w-[800px] max-w-[1000px] rounded">
    <ComponentLayout title="src/routes/SubscriptionDetails/SubscriptionDetails/index.tsx">
      <div className="space-y-6">
        <div>
          <h3 className="text-lg font-semibold text-gray-900 dark:text-white">{subscriptionPlanVariationData.subscriptionPlanVariationData.name}</h3>
          <p className="text-sm text-gray-500 dark:text-gray-400"><span className="font-bold">ID:</span> {subscription.id}</p>
          <p className="text-sm text-gray-500 dark:text-gray-400"><span className="font-bold">Start Date:</span> {new Date(subscription.startDate).toDateString()}</p>
          <p className="text-sm text-gray-500 dark:text-gray-400"><span className="font-bold">Charge Through Date:</span> {new Date(subscription.chargedThroughDate).toDateString()}</p>
          <p className="text-sm text-gray-500 dark:text-gray-400 mb-4"><span className="font-bold">Status:</span> {subscription.status}</p>
        </div>
          <InvoicesTable
            invoices={invoices}
          />
        {checkSubStatus()}
      </div>
      </ComponentLayout>
     </div>
    }
    </div>);
}

export default SubscriptionDetails;