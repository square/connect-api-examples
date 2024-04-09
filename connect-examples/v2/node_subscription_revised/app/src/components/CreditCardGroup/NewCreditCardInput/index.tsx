import { PaymentForm, CreditCard } from "react-square-web-payments-sdk";
import { useContext } from "react";
import { AppContext, AppDispatchContext } from "../../../context/AppContext";
import ComponentLayout from "../../ComponentLayout";

interface NewCreditCardInputProps {}

const NewCreditCardInput: React.FC<NewCreditCardInputProps> = () => {
  const { selectedCustomer, selectedSubscriptionPlan, selectedItems } =
    useContext(AppContext);
  const dispatch = useContext(AppDispatchContext);
  // Check if the Square Application ID is set in the .env file
  // If not, display an error message
  if (!process.env.REACT_APP_SQUARE_APP_ID) {
    return <h1>Set your Square Application ID in your app/.env</h1>;
  }
  return (
    <div className="flex flex-col justify-center items-center bg-white rounded p-8">
      <ComponentLayout title="src/components/CreditCardGroup/NewCreditCardInput/index.tsx">
        <h4 className="text-lg font-semibold mb-2">Pay with a new card:</h4>
        <div className="min-w-[200px]">
          <PaymentForm
            locationId="main"
            applicationId={process.env.REACT_APP_SQUARE_APP_ID}
            cardTokenizeResponseReceived={async (token) => {
              try {
                const cardResponse = await fetch("/cards", {
                  method: "POST",
                  headers: {
                    "Content-Type": "application/json",
                  },
                  body: JSON.stringify({
                    customerId: selectedCustomer.id,
                    token: token.token,
                    postalCode: token.details?.billing?.postalCode,
                  }),
                });
              
                if (!cardResponse.ok) {
                  throw new Error("Failed to create card data");
                }
              
                const card = await cardResponse.json();
              
                const subscriptionResponse = await fetch("/subscriptions", {
                  method: "POST",
                  headers: {
                    "Content-Type": "application/json",
                  },
                  body: JSON.stringify({
                    customerId: selectedCustomer.id,
                    subscriptionVariationId: selectedSubscriptionPlan.subscriptionPlanData
                      .subscriptionPlanVariations[0].id,
                    itemIds: selectedItems.map((item) => item.itemData.variations[0].id),
                    cardId: card.id,
                  }),
                });
              
                if (!subscriptionResponse.ok) {
                  throw new Error("Failed to create subscription");
                }
              
                dispatch({ type: "SUBMIT_ORDER", payload: null });
              
                // Wait for 4 seconds
                await new Promise((resolve) => setTimeout(resolve, 4000));
              
                dispatch({ type: "DISMISS_TOAST", payload: null });
              
              } catch (error) {
                console.error("Error:", error);
              }              
            }}
          >
            <CreditCard>
              <button>Add Card on File</button>
            </CreditCard>
          </PaymentForm>
        </div>
      </ComponentLayout>
    </div>
  );
};

export default NewCreditCardInput;
