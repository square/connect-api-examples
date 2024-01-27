import { SubscriptionPlanIds } from "../../App";
import { CustomerData } from "../Customers";
import CustomerTile from "../Customers/CustomerTile";
import { Item } from "../ItemCatalog";
import ItemTile from "../ItemCatalog/ItemTile";
import { SubscriptionPlanData } from "../SubscriptionsPlans";
import SubscriptionTile from "../SubscriptionsPlans/SubscriptionTile";

interface ReviewDetailsProps {
    customer: CustomerData;
    subscriptionPlanIds: SubscriptionPlanIds;
    subscriptionPlan: SubscriptionPlanData;
    subscribed_items: Item[];
}

const ReviewDetails: React.FC<ReviewDetailsProps> = ({customer, subscriptionPlanIds, subscriptionPlan, subscribed_items}) => {

    const onSubmitOrder = async () => {
        try {
            const response = await fetch('/create_subscription', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    customerId: customer.id,
                    subscriptionVariationId: subscriptionPlanIds.subscriptionPlanVariationId,
                    itemIds: subscribed_items.map((item) => item.itemData.variations[0].id)
                })
            });
            const data = await response.json();
            console.log('this is the data', data)
        } catch (error) {
            console.error('Error fetching customer data:', error);
        }
    }

    return (
        <div className="flex-col">
            <div className="flex">
                <div className="mr-4 min-w-96">
                    <CustomerTile 
                        customer={customer}
                        handleCustomerSelection={null}
                        selectedCustomerId={null}
                    />
                </div>
                <div className="mr-4 min-w-96">
                    <SubscriptionTile
                        selectedSubscription={null}
                        subscriptionPlan={subscriptionPlan}
                        handleSubscriptionPlanSelection={null}
                    />
                </div>
            </div>
            <div className="flex mt-4">
                {
                    subscribed_items.map((item, i) => (
                        <div key={i} className="mr-4 min-w-96">
                            <ItemTile
                                item={item}
                                onHandleItemSelection={null}
                                selectedItems={[]}
                                setIsNextDisabled={() => {}}
                            />
                        </div>
                    ))
                }
            </div>
            <button
            className={`px-4 py-2 text-white bg-blue-500 hover:bg-blue-700`}
             onClick={onSubmitOrder}>Submit Order</button>
        </div>
    )
}

export default ReviewDetails