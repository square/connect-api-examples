import React, { useContext, useState } from "react";
import { Spinner } from "flowbite-react";
import { AppContext, AppDispatchContext } from "../../../context/AppContext";
import ComponentLayout from "../../ComponentLayout";

interface CardsOnFileProps {}

const CardsOnFile: React.FC<CardsOnFileProps> = () => {
  const { selectedCustomer, selectedSubscriptionPlan, selectedItems } =
    useContext(AppContext);
  const dispatch = useContext(AppDispatchContext);
  const [selectedCard, setSelectedCard] = useState<string | undefined>(
    selectedCustomer?.cards?.[0]?.id,
  );
  const [isLoading, setIsLoading] = useState<boolean>(false);

  const handleCardSelection = (event: any) => {
    setSelectedCard(event.target.value);
  };

  const onSubmitOrder = async () => {
    setIsLoading(true);
    try {
      await fetch("/subscriptions", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          customerId: selectedCustomer.id,
          subscriptionVariationId:
            selectedSubscriptionPlan.subscriptionPlanData
              .subscriptionPlanVariations[0].id,
          itemIds: selectedItems.map((item) => item.itemData.variations[0].id),
          cardId: selectedCard,
        }),
      });
      dispatch({ type: "SUBMIT_ORDER", payload: null });
      setIsLoading(false);
      await new Promise((resolve) => setTimeout(resolve, 4000));
      dispatch({ type: "DISMISS_TOAST", payload: null });
    } catch (error) {
      console.error("Error fetching customer data:", error);
    }
  };

  const CardDropdown = ({ cards }: any) => {
    if (cards.length === 0) return null;
    return (
      <form className="max-w-sm w-56 mx-auto mb-4">
        <label
          htmlFor="cards"
          className="block mb-2 text-sm font-medium text-gray-900"
        >
          Select a card
        </label>
        <select
          id="cards"
          className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
          onChange={handleCardSelection}
          value={selectedCard}
        >
          {cards.map((card: any, i: number) => {
            return (
              <option key={i} value={card.id}>
                {card.cardBrand} ending in {card.last4}
              </option>
            );
          })}
        </select>
      </form>
    );
  };

  return (
    <div className="mb-4 p-8 bg-white rounded">
      <ComponentLayout title="src/components/CreditCardGroup/CardsOnFile/index.tsx">
        <h4 className="text-lg font-semibold mb-2">Pay with Card on File:</h4>
        <div className="flex flex-col items-center p-4">
          <CardDropdown cards={selectedCustomer?.cards} />
          <button
            className={`px-4 py-2 max-w-48 text-white bg-blue-500 hover:bg-blue-700`}
            onClick={onSubmitOrder}
          >
            {isLoading ? (
              <Spinner aria-label="loading status" />
            ) : (
              <>Submit Order</>
            )}
          </button>
        </div>
      </ComponentLayout>
    </div>
  );
};

export default CardsOnFile;
