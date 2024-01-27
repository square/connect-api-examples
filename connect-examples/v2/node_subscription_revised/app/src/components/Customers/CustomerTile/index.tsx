
import { Card, Avatar } from 'flowbite-react';
import { CustomerData } from '..';

interface CustomerTileProps {
    customer: CustomerData;
    selectedCustomerId: string | null;
    handleCustomerSelection: ((customer: CustomerData) => void) | null;
}

const CustomerTile: React.FC<CustomerTileProps> = ({customer, selectedCustomerId, handleCustomerSelection}) => {
    return (
        <Card href="#" className={`max-w-sm ${selectedCustomerId === customer.id ? 'bg-gray-100' : ''} ${handleCustomerSelection ? `` : `cursor-default hover:bg-white`}`}
            onClick={handleCustomerSelection ? () => handleCustomerSelection(customer) : () => {}}
        >
            <div className='flex items-center'>
                <Avatar rounded className='pr-4'/>
                <h5 className="text-2xl font-bold tracking-tight text-gray-900 dark:text-white">
                    {`${customer.givenName} ${customer.familyName}`}
                </h5>
            </div>
            <p className="font-normal text-gray-700 dark:text-gray-400">
            {`Email: ${customer.emailAddress}`}
            </p>

            {customer?.cards?.length ? (
                <div className='mt-4'>
                    <h4 className="text-lg font-semibold mb-2">Card Details:</h4>
                    <p>Customer Card on File</p>
                </div>
            ) : <div className='mt-4'>
                <h4 className="text-lg font-semibold mb-2">No card on file</h4>
            </div>
            }
        </Card>
    );

}

export default CustomerTile
