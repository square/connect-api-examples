import { Card } from 'flowbite-react';
import { SubscriptionPlanData, SubscriptionPlanVariation } from '..';
import ComponentLayout from '../../ComponentLayout';
import { useContext } from 'react';
import { AppContext, AppDispatchContext } from '../../../context/AppContext';

interface SubscriptionTileProps {
  subscriptionPlan: SubscriptionPlanData;
  showButton: boolean;
}

const SubscriptionTile: React.FC<SubscriptionTileProps> = ({subscriptionPlan, showButton}) => {
  const { selectedSubscriptionPlanVariation } = useContext(AppContext);
  const dispatch = useContext(AppDispatchContext);

  return (
    <Card className={`max-w-sm mr-4 mb-4`}>
      <ComponentLayout title="src/components/SubscriptionPlans/SubscriptionTile/index.tsx">
      <h5 className="mb-4 text-xl font-bold text-gray-700">{subscriptionPlan.subscriptionPlanData.name}</h5>
      { showButton ?
          subscriptionPlan?.subscriptionPlanData?.subscriptionPlanVariations.map((subscriptionPlanVariation: SubscriptionPlanVariation, i:number) => (
                <Card
                className={`${subscriptionPlanVariation.id === selectedSubscriptionPlanVariation.id ? 'bg-blue-200' : ''}`}
                key={i}
                >
                    <h5 className='text-lg font-bold'>{subscriptionPlanVariation.subscriptionPlanVariationData.name}</h5>
                      <button
                          type="button"
                          className="inline-flex w-full mt-4 justify-center rounded-lg bg-cyan-600 px-5 py-2.5 text-center text-sm font-medium text-white hover:bg-cyan-700 focus:outline-none focus:ring-4 focus:ring-cyan-200 dark:focus:ring-cyan-900"
                          onClick={() =>  dispatch({
                            type: 'SET_SUBSCRIPTION', 
                            payload: {
                              subscriptionPlan, 
                              eligibleCategoryIds: subscriptionPlan.subscriptionPlanData.eligibleCategoryIds, 
                              selectedSubscriptionPlanVariation: subscriptionPlanVariation,
                            }})
                          }>
                          Choose plan
                      </button>
                </Card>
          ))
          :
          <h5 className='text-lg font-bold'>{selectedSubscriptionPlanVariation.subscriptionPlanVariationData.name}</h5>         
    }
      </ComponentLayout>
    </Card>
  );
}

export default SubscriptionTile
