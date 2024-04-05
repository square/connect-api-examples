import { PaymentForm, CreditCard } from 'react-square-web-payments-sdk';
import { useContext } from 'react';
import { AppContext, AppDispatchContext } from '../../../context/AppContext';
import ComponentLayout from '../../ComponentLayout';

interface NewCreditCardInputProps {}

const NewCreditCardInput: React.FC<NewCreditCardInputProps> = ({}) => {
     const { selectedCustomer, selectedSubscriptionPlan, selectedItems } = useContext(AppContext);
     const dispatch = useContext(AppDispatchContext);
     // Check if the Square Application ID is set in the .env file
     // If not, display an error message
    if (!process.env.REACT_APP_SQUARE_APP_ID) {
        return <h1>Set your Square Application ID in your app/.env</h1>
    }
    return <div className="flex flex-col justify-center items-center bg-white rounded p-8">
        <ComponentLayout title="src/components/CreditCardGroup/NewCreditCardInput/index.tsx">
         <h4 className="text-lg font-semibold mb-2">Pay with a new card:</h4>
    <PaymentForm
      applicationId={process.env.REACT_APP_SQUARE_APP_ID}
      cardTokenizeResponseReceived={async (token) => {
        try{
            const response = await fetch('/cards', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    customerId: selectedCustomer.id,
                    token: token.token,
                    postalCode: token.details?.billing?.postalCode
                })
            });
            const card = await response.json();

            try {
                await fetch('/subscriptions', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        customerId: selectedCustomer.id,
                        subscriptionVariationId: selectedSubscriptionPlan.subscriptionPlanData.subscriptionPlanVariations[0].id,
                        itemIds: selectedItems.map((item) => item.itemData.variations[0].id),
                        cardId: card.id
                    })
                });
                dispatch({type: 'SUBMIT_ORDER', payload: null});
            } catch (error) {
                console.error('Error fetching customer data:', error);
            }

        } catch (error) {
            console.error('Error fetching customer data:', error);
        }
      }}
      locationId='main'
    >
        <CreditCard>
            <button>Add Card on File</button>
        </CreditCard>
    </PaymentForm>
    </ComponentLayout>
  </div>
}

export default NewCreditCardInput;