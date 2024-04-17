import { Button, Datepicker, Table, Textarea } from "flowbite-react";
import { useState } from "react";
import ComponentLayout from "../../../components/ComponentLayout";
import { formattedDate } from "../../../utils/helpers";

// Allow the user to cancel their subscription
// This will show if the Subscription is not already canceled, paused, or in the process of being paused
export const CancelSubscriptionSection = ({
  subscription,
  subscriptionChanged,
  setSubscriptionChanged,
  setIsLoading,
}: any) => {
  const handleCancelSubscription = async () => {
    setIsLoading(true);
    try {
      const response = await fetch(`/subscriptions/${subscription.id}/cancel`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
      });
      await response.json();
      setSubscriptionChanged(!subscriptionChanged);
    } catch (e) {
      console.error("Error cancelling subscription:", e);
      setIsLoading(false);
    }
  };

  return (
    <ComponentLayout title="src/routes/SubscriptionDetails/ManageSubscription/SubscriptionSections/index.tsx">
      <div className="flex flex-col mt-4">
        <Button
          className="mr-2"
          color="failure"
          onClick={() => handleCancelSubscription()}
        >
          Cancel Subscription
        </Button>
      </div>
    </ComponentLayout>
  );
};

// Allow the user to pause their subscription
// This will show if the Subscription is not already canceled, paused, or in the process of being paused
export const PauseSubscriptionSection = ({
  subscription,
  setIsLoading,
  subscriptionChanged,
  setSubscriptionChanged,
}: any) => {
  const [datePickerValue, setDatePickerValue] = useState<string>(
    new Date().toDateString(),
  );
  const [textareaValue, setTextareaValue] = useState<string>("");

  const handlePauseSubscription = async () => {
    setIsLoading(true);
    try {
      await fetch(`/subscriptions/${subscription.id}/pause`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          pauseEffectiveDate: new Date(datePickerValue)
            .toISOString()
            .split("T")[0],
          pauseReason: textareaValue,
          pauseCycleDuration: 1,
        }),
      });
      setSubscriptionChanged(!subscriptionChanged);
    } catch (e) {
      console.error("Error pausing subscription:", e);
      setIsLoading(false);
    }
  };

  return (
    <ComponentLayout title="src/routes/SubscriptionDetails/ManageSubscription/SubscriptionSections/index.tsx">
      <div className="border-b-2">
        <p className="text-xl pt-4">Pause Subscription</p>
        <div id="pause-sub section" className="p-4">
          <label htmlFor="datepicker" id="datepickerlabel">
            Pause Start Date
          </label>
          <div className="flex mb-4">
            <Datepicker
              id="datepicker"
              name="datepicker"
              minDate={new Date()}
              maxDate={
                new Date(new Date().setFullYear(new Date().getFullYear() + 1))
              }
              value={datePickerValue}
              onSelectedDateChanged={(date: Date) => {
                setDatePickerValue(date.toDateString());
              }}
            />
            <Textarea
              className="ml-4 max-w-xs"
              placeholder="Reason for pausing"
              rows={2}
              value={textareaValue}
              onChange={(e: React.ChangeEvent<HTMLTextAreaElement>) => {
                setTextareaValue(e.target.value);
              }}
            />
          </div>
        </div>
        <div className="flex justify-center mb-4">
          <Button color="yellow" onClick={() => handlePauseSubscription()}>
            Pause Subscription
          </Button>
        </div>
      </div>
    </ComponentLayout>
  );
};

// Show the upcoming actions for the subscription
// This will show if the subscription has been paused or canceled, but the actions have not yet been processed
export const ActionsSection = ({
  actions,
  subscriptionId,
  actionId,
  setIsLoading,
  setSubscriptionChanged,
  subscriptionChanged,
}: any) => {
  const handleCancelActions = async () => {
    setIsLoading(true);
    try {
      const result = await fetch(
        `/subscriptions/${subscriptionId}/actions/${actionId}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            actions: actions,
          }),
        },
      );
      await result.json();
      setSubscriptionChanged(!subscriptionChanged);
    } catch (e) {
      console.error("Error canceling actions:", e);
    }
  };
  return (
    <ComponentLayout title="src/routes/SubscriptionDetails/ManageSubscription/SubscriptionSections/index.tsx">
      <p className="text-xl">Upcoming Actions</p>
      <div className="">
        <Table>
          <Table.Head>
            <Table.HeadCell>Action Type</Table.HeadCell>
            <Table.HeadCell>ID</Table.HeadCell>
            <Table.HeadCell>Effective Date</Table.HeadCell>
          </Table.Head>
          <Table.Body>
            {actions.map((action: any, i: number) => {
              return (
                <Table.Row key={i}>
                  <Table.Cell>{action.type}</Table.Cell>
                  <Table.Cell>{action.id}</Table.Cell>
                  <Table.Cell>{formattedDate(action.effectiveDate)}</Table.Cell>
                </Table.Row>
              );
            })}
          </Table.Body>
        </Table>
      </div>
      <Button
        color="blue"
        onClick={() => {
          handleCancelActions();
        }}
      >
        Cancel Actions
      </Button>
    </ComponentLayout>
  );
};

export const CanceledInfoSection = ({ toBeCanceledData }: any) => {
  return (
    <ComponentLayout title="src/routes/SubscriptionDetails/ManageSubscription/SubscriptionSections/index.tsx">
      <p className="text-xl pt-4 text-red-400">Subscription Cancel Requested</p>
      <span className="text-sm text-gray-500">
        Subscription Active Until:{" "}
        {formattedDate(toBeCanceledData.effectiveDate)}
      </span>
    </ComponentLayout>
  );
};

export const PausedInfoSection = ({ toBePausedData, resumeData }: any) => {
  return (
    <ComponentLayout title="src/routes/SubscriptionDetails/ManageSubscription/SubscriptionSections/index.tsx">
      <div className="flex flex-col">
        <p className="text-xl text-yellow-400">Subscription Pause Requested</p>
        <p className="text-sm text-gray-500">
          <span className="font-bold text-black">Active until:</span>{" "}
          {formattedDate(toBePausedData.effectiveDate)}
        </p>
        <p className="text-sm text-gray-500">
          <span className="font-bold text-black">Resumes:</span>{" "}
          {formattedDate(resumeData.effectiveDate)}
        </p>
      </div>
    </ComponentLayout>
  );
};
