import { PaymentForm, CreditCard } from 'react-square-web-payments-sdk';
import { useContext } from 'react';
import { AppContext, AppDispatchContext } from '../../../context/AppContext';
import ComponentLayout from '../../ComponentLayout';

interface NewCreditCardInputProps {}

const NewCreditCardInput: React.FC<NewCreditCardInputProps> = ({}) => {
     // TODO: don't hardcode appId
     const { selectedCustomer, selectedSubscriptionPlan, selectedItems } = useContext(AppContext);
     const dispatch = useContext(AppDispatchContext);

    return <div className="flex flex-col justify-center items-center bg-white rounded p-8">
        <ComponentLayout title="src/components/CreditCardGroup/NewCreditCardInput/index.tsx">
         <h4 className="text-lg font-semibold mb-2">Pay with a new card:</h4>
    <PaymentForm
      applicationId="sandbox-sq0idb-epIM-AU7Zh5_nZX1hv4Tzg"      
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