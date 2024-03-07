
import { Card, Avatar } from 'flowbite-react';
import { CustomerData } from '..';
import { useNavigate } from 'react-router-dom';
import { useContext } from 'react';
import ComponentLayout from '../../ComponentLayout';
import { AppContext, AppDispatchContext } from '../../../context/AppContext';
import Skeleton from '../../Skeleton';


interface CustomerTileProps {
    customer: CustomerData | undefined;
    isActionable?: boolean;
    isLoading?: boolean;
}


const CustomerTile: React.FC<CustomerTileProps> = ({ customer, isActionable, isLoading }) => {
    const {selectedCustomer} = useContext(AppContext)
    const dispatch = useContext(AppDispatchContext);
    const navigate = useNavigate();

    return <>
    {isLoading && !customer && (
        <Card className="max-w-sm mr-4">
            <div className="flex items-center mb-4">
                <Avatar rounded className="pr-4" />
            </div>
            <Skeleton />

        </Card>
    )}
    {customer && (
            <Card className={`max-w-sm mr-4 cursor-pointer ${isActionable ? selectedCustomer.id === customer.id ? 'bg-blue-200' : '' : 'cursor-default hover:bg-white'}`}
            onClick={isActionable ? () => dispatch({type: 'SET_CUSTOMER', payload: customer}) : undefined}
        >
            <ComponentLayout title="src/Customers/CustomerTile/index.tsx">
            <div className="flex items-center mb-4">
                <Avatar rounded className="pr-4" />
                <div>
                    <h5 className="text-2xl font-bold tracking-tight text-gray-900 dark:text-white">
                        {`${customer.givenName} ${customer.familyName}`}
                    </h5>
                    <p className="font-normal text-gray-700 dark:text-gray-400">
                        {`Email: ${customer.emailAddress}`}
                    </p>
                </div>
            </div>
            <div>
                {customer.cards?.length ? (
                    <>
                        <h4 className="text-lg font-semibold mb-2">Card Details:</h4>
                        {customer.cards.map((card, i) => (
                            <ul key={i}>
                                <li>{`${card.cardBrand} - Ending in ${card.last4}`}</li>
                            </ul>
                        ))}
                    </>
                ) : (
                    <h4 className="text-lg font-semibold mb-2">No card on file</h4>
                )}
            </div>
            {
            isActionable ? 
                <button
                    type="button"
                    className={`w-full rounded-lg bg-cyan-600 px-5 py-2.5 text-center text-sm font-medium text-white hover:bg-cyan-700 focus:outline-none focus:ring-4 focus:ring-cyan-200 dark:focus:ring-cyan-900 mt-auto`}
                    onClick={() => navigate(`/customer/${customer.id}`)}
                >
                    Manage User Subscriptions
                </button>
                : 
                null            
            }
            </ComponentLayout>
        </Card>
    )}
    </>
};


export default CustomerTile
