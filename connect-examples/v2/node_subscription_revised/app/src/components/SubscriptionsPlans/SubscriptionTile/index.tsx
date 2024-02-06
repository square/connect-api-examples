import { Card } from "flowbite-react";
import { SubscriptionPlanData, SubscriptionPlanVariation } from "..";

interface SubscriptionTileProps {
  subscriptionPlan: SubscriptionPlanData;
  selectedSubscription: string | null;
  handleSubscriptionPlanSelection:
    | ((
        subscriptionPlan: SubscriptionPlanData,
        subscriptionPlanVariationId: string
      ) => void)
    | null;
}

const SubscriptionTile: React.FC<SubscriptionTileProps> = ({
  subscriptionPlan,
  handleSubscriptionPlanSelection,
  selectedSubscription,
}) => {
  return (
    <Card
      className={`max-w-sm mr-4 mb-4 border-gray-30 ${
        selectedSubscription === subscriptionPlan?.id ? "border-blue" : ""
      }`}
    >
      <h5 className="mb-4 text-xl font-medium text-gray-100 dark:text-gray-40">
        {subscriptionPlan.subscriptionPlanData.name}
      </h5>
      {subscriptionPlan?.subscriptionPlanData?.subscriptionPlanVariations.map(
        (subscriptionPlanVariation: SubscriptionPlanVariation, i: number) => (
          <div className="cursor-pointer" key={i}>
            <h5>
              {subscriptionPlanVariation.subscriptionPlanVariationData.name}
            </h5>
            {handleSubscriptionPlanSelection ? (
              <button
                type="button"
                className="inline-flex w-full mt-4 justify-center rounded-lg bg-blue px-5 py-2.5 text-center text-sm font-medium text-white hover:bg-blue-10 focus:outline-none focus:ring-4 focus:ring-cyan-200"
                onClick={() =>
                  handleSubscriptionPlanSelection(
                    subscriptionPlan,
                    subscriptionPlanVariation.id
                  )
                }
              >
                Choose plan
              </button>
            ) : null}
          </div>
        )
      )}
    </Card>
  );
};

export default SubscriptionTile;
