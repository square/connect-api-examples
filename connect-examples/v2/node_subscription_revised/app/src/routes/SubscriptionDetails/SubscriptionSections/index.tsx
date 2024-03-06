import { Button, Card, Datepicker, Spinner, Textarea } from "flowbite-react"
import { useState } from "react";
import ComponentLayout from "../../../components/ComponentLayout";

export const CancelSubscriptionSection = ({ subscription, subscriptionChanged, setSubscriptionChanged }: any) => {
    const [isCancelButtonLoading, setIsCancelButtonLoading] = useState<boolean>(false);

    const handleCancelSubscription = async () => {
        setIsCancelButtonLoading(true);
        try {
          const response = await fetch(`/subscriptions/${subscription.id}/cancel`, {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json'
            },
          });
          const data = response.json();
          setIsCancelButtonLoading(false);
          setSubscriptionChanged(!subscriptionChanged);
        } catch(e) {
          console.error('Error cancelling subscription:', e);
          setIsCancelButtonLoading(false);
        }
      }

    return <ComponentLayout title="src/routes/SubscriptionDetails/ManageSubscription/SubscriptionSections/index.tsx">
    <div className="flex flex-col mt-4">
      <Button
        className='mr-2'
        color="red" 
        onClick={() => handleCancelSubscription()}>
           {isCancelButtonLoading ? <Spinner/> :  'Cancel Subscription'}
      </Button> 
    </div>
    </ComponentLayout>
}

export const PauseSubscriptionSection = ({ subscription, setIsLoading, subscriptionChanged, setSubscriptionChanged }: any) => {
    const [datePickerValue, setDatePickerValue] = useState<string>(new Date().toDateString());
    const [textareaValue, setTextareaValue] = useState<string>("");
    
    const handlePauseSubscription = async () => {
        setIsLoading(true);
        try {
          const response = await fetch(`/subscriptions/${subscription.id}/pause`, {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json'
            },
            body: JSON.stringify({
              pauseEffectiveDate: new Date(datePickerValue).toISOString().split('T')[0],
              pauseReason: textareaValue,
              pauseCycleDuration: 1,
            })
          });
          setSubscriptionChanged(!subscriptionChanged)
        } catch(e) {
          console.error('Error pausing subscription:', e);
          setIsLoading(false)
        }
      }
    
    return <ComponentLayout title="src/routes/SubscriptionDetails/ManageSubscription/SubscriptionSections/index.tsx">
    <div className="border-b-2">
    <p className="text-xl pt-4">Pause Subscription</p>
    <div id="pause-sub section" className="p-4">
          <label htmlFor="datepicker" id="datepickerlabel">Pause Start Date</label>
          <div className="flex mb-4">
            <Datepicker 
              id="datepicker" 
              name="datepicker" 
              minDate={new Date()} 
              maxDate={new Date(new Date().setFullYear(new Date().getFullYear() + 1))}
              value={datePickerValue}
              onSelectedDateChanged={(date: Date) => {setDatePickerValue(date.toDateString())}}
            />
            <Textarea
              className="ml-4 max-w-xs"
              placeholder="Reason for pausing" rows={2}
              value={textareaValue}
              onChange={(e: React.ChangeEvent<HTMLTextAreaElement>) => {setTextareaValue(e.target.value)}}
            />
        </div>
    </div>
        <div className="flex justify-center mb-4">
            <Button 
                color="yellow" 
                onClick={() => handlePauseSubscription()}>
              Pause Subscription
            </Button>
        </div>
    </div>
  </ComponentLayout>
}

export const ActionsSection = ({actions, subscriptionId, actionId, setIsLoading, setSubscriptionChanged, subscriptionChanged}: any) => {
    const handleCancelActions = async () => {
        setIsLoading(true);
        try {
            const result = await fetch(`/subscriptions/${subscriptionId}/actions/${actionId}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    actions: actions
                })
            });
            result.json();
            setSubscriptionChanged(!subscriptionChanged);
        } catch(e) {
            console.error('Error canceling actions:', e);
        }
    }
    return <ComponentLayout title="src/routes/SubscriptionDetails/ManageSubscription/SubscriptionSections/index.tsx">
    <p className="text-xl">Upcoming Actions</p>
    <div className="flex">
        {actions.map((action: any, i: number) => {
            return <Card className="max-w-xs mb-4 mr-4" key={i}>
            <h5 className="text-lg font-bold tracking-tight text-gray-900 dark:text-white">
              Action Type: {action.type}
            </h5>
            <p className="text-sm text-gray-500 dark:text-gray-400">
              id: {action.id}
            </p> 
            <p className="text-sm text-gray-500 dark:text-gray-400">
              Effective Date: {new Date(action.effectiveDate).toDateString()}
            </p>
          </Card>
        })}
    </div>
    <Button color="blue"
    onClick={()=>{handleCancelActions()}}
    >Cancel Actions</Button>
  </ComponentLayout>

}

export const CanceledInfoSection = ({ cancelData }: any) => {
    return <ComponentLayout title="src/routes/SubscriptionDetails/ManageSubscription/SubscriptionSections/index.tsx">
    <p className="text-xl pt-4 text-red-400">Subscription Canceled</p>
    <span className="text-sm text-gray-500 dark:text-gray-400">Subscription Active Until: {new Date(cancelData.effectiveDate).toDateString()}</span>

</ComponentLayout>
}

export const PausedInfoSection = ({ pauseData, resumeData }: any) => {
    return <ComponentLayout title="src/routes/SubscriptionDetails/ManageSubscription/SubscriptionSections/index.tsx">
      <div className="flex flex-col">
        <p className="text-xl text-yellow-400">Subscription Paused</p>
        <span className="text-sm text-gray-500 dark:text-gray-400">Subscription Active Until: {new Date(pauseData.effectiveDate).toDateString()}</span>
        <span className="text-sm text-gray-500 dark:text-gray-400">Subscription will Resume: {new Date(resumeData.effectiveDate).toDateString()}</span>
      </div>
    </ComponentLayout>
}

export const ResumeSection = ({ subscription, resumeEffectiveDate }: any) => {

    const handleResumeSubscription = async () => {
        try {
          const response = await fetch(`/subscriptions/${subscription.id}/resume`, {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json'
            },
            body: JSON.stringify({
              resumeEffectiveDate: resumeEffectiveDate,
              resumeChangeTiming: 'IMMEDIATE'
            })
          });
          const data = response.json();
          console.log('resume subscription data:', data)
        } catch(e) {
          console.error('Error resuming subscription:', e);
        }
      }

    return <ComponentLayout title="src/routes/SubscriptionDetails/ManageSubscription/SubscriptionSections/index.tsx">
    <div className="flex justify-start">
        <Button color="green"
            onClick={()=> handleResumeSubscription()}
        >Resume Subscription</Button>
    </div>
    </ComponentLayout>
}

// TODO: implement this component in the app if there is demand for it
export const SwapSubscription = ({ subscription }: any) => {
    return <ComponentLayout title="src/routes/SubscriptionDetails/ManageSubscription/SubscriptionSections/index.tsx">
    <p className="text-xl pt-4">Swap Subscription</p>
    <div className="flex p-8 justify-center border-b-2">
        {subscription.phases[subscription.phases.length-1].order.lineItems.map((item: any, i: number) => {
            return  <Card className="max-w-xs mb-4 mr-4" key={i}>
            <h5 className="text-lg font-bold tracking-tight text-gray-900 dark:text-white">
              {item.name}
            </h5>
            <p className="text-sm text-gray-500 dark:text-gray-400">
              ${item.basePriceMoney.amount / 100} {item.basePriceMoney.currency}
            </p>
          </Card>
        })}
    </div>
</ComponentLayout>
}
