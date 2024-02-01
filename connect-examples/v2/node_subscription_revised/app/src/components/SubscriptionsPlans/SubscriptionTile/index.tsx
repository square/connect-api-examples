import { Card } from 'flowbite-react';
import { SubscriptionPlanData, SubscriptionPlanVariation } from '..';

interface SubscriptionTileProps {
    subscriptionPlan: SubscriptionPlanData;
    selectedSubscription: string | null;
    handleSubscriptionPlanSelection: ((subscriptionPlan:SubscriptionPlanData, subscriptionPlanVariationId: string) => void) | null;
}

const SubscriptionTile: React.FC<SubscriptionTileProps> = ({ subscriptionPlan, handleSubscriptionPlanSelection, selectedSubscription }) => {
  return (
    <Card className={`max-w-sm mr-4 ${selectedSubscription === subscriptionPlan?.id ? 'bg-gray-100' : ''}`}>
      <h5 className="mb-4 text-xl font-medium text-gray-500 dark:text-gray-400">{subscriptionPlan.subscriptionPlanData.name}</h5>
      {subscriptionPlan?.subscriptionPlanData?.subscriptionPlanVariations.map((subscriptionPlanVariation: SubscriptionPlanVariation, i:number) => (
            <div
            className="cursor-pointer"
            key={i}
            >
                <h5>{subscriptionPlanVariation.subscriptionPlanVariationData.name}</h5>
                { handleSubscriptionPlanSelection ? 
                  <button
                      type="button"
                      className="inline-flex w-full mt-4 justify-center rounded-lg bg-cyan-600 px-5 py-2.5 text-center text-sm font-medium text-white hover:bg-cyan-700 focus:outline-none focus:ring-4 focus:ring-cyan-200 dark:focus:ring-cyan-900"
                      onClick={() => handleSubscriptionPlanSelection(subscriptionPlan, subscriptionPlanVariation.id)}
                  >
                      Choose plan
                  </button>
                  : 
                  null
              
              }
            </div>
        ))}
    </Card>
  );
}

export default SubscriptionTile
