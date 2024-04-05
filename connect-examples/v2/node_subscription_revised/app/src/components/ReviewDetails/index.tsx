import { useContext } from "react";
import CustomerTile from "../Customers/CustomerTile";
import ItemTile from "../ItemCatalog/ItemTile";
import SubscriptionTile from "../SubscriptionPlans/SubscriptionTile";
import CreditCardGroup from "../CreditCardGroup";
import { AppContext } from "../../context/AppContext";
import { Card } from "flowbite-react";

interface ReviewDetailsProps {}

const ReviewDetails: React.FC<ReviewDetailsProps> = ({}) => {

    const {
        selectedCustomer,
        selectedSubscriptionPlan,
        selectedItems,
    } = useContext(AppContext)

    const originalTotal = selectedItems.reduce((acc, item) => {
        return acc + Number(item.itemData.variations[0].itemVariationData.priceMoney.amount);
      }, 0);
      
      // Calculate the total price after applying a 10% discount
      const discountedTotal = originalTotal * 0.9;
      
      const formatMoney = (amount: number) => {
        return (amount / 100).toFixed(2);
      }
      const oneWeekFromNow = new Date();

      oneWeekFromNow.setDate(oneWeekFromNow.getDate() + 7);
      
      const options: Intl.DateTimeFormatOptions = {
        weekday: undefined,
        year: 'numeric',
        month: 'long',
        day: 'numeric',
      };
      
      const formattedDate = oneWeekFromNow.toLocaleDateString('en-US', options);
    return (
        <div className="flex"> 
            <div className="flex-col bg-white p-4 rounded mr-4">
                {/* container */}
                <div className="my-4 flex justify-around">
                    {/* Promotion */}
                    <div className="mr-4">
                        {/* Due Today */}
                        <p className="text-xl font-semibold">Due Today: <span className="text-green-500 font-bold">$1.00</span></p>
                        <p className="font-semibold mb-2 text-gray-500">1st Week Promo</p>
                    </div>
                <div>
                    {/* Subscription Starts */}
                    <p className="text-lg font-semibold mb-2">Starting:  <span className="p-2 bg-gray-200 rounded-xl mr-2">{formattedDate}</span></p>
                    {/* Total Prices */}
                    <div className="flex flex-col">
                        <div className="flex items-center">
                            <p className="font-bold">Regular Total:</p>
                            <span className="line-through text-gray-500 ml-2 font-bold">${formatMoney(originalTotal)}</span>
                        </div>

                        <div className="flex items-center">
                            <p className="font-bold">You Pay:</p>
                            <span className="text-green-500 font-bold ml-2">${formatMoney(discountedTotal)}</span>
                            <span className="p-1 rounded-xl bg-green-200 ml-2 font-bold text-green-500">Save 10%</span>
                        </div>
                    </div>
                </div>
            </div>

                <div className="flex">
                    <div className="mr-4 min-w-96">
                        <CustomerTile 
                            customer={selectedCustomer}
                            isActionable={false}
                        />
                    </div>
                    <div className="mr-4 min-w-96">
                        <SubscriptionTile
                            showButton={false}
                            subscriptionPlan={selectedSubscriptionPlan}
                        />
                    </div>
                </div>
                <div className="flex mt-4">
                    {
                        selectedItems.map((item, i) => (
                            <div key={i} className="mr-4 min-w-96">
                                <ItemTile
                                    item={item}
                                    isActionable={false}
                                />
                            </div>
                        ))
                    }
                </div>
        </div>
        <CreditCardGroup />
    </div>
    )
}

export default ReviewDetails