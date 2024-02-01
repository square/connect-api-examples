import { Table } from 'flowbite-react';
import { useEffect, useState } from 'react';
import Skeleton from '../../../components/Skeleton';

interface SubscriptionsTableProps {
  customerId: string
  setOpenModal: (openModal: boolean) => void
}

const SubscriptionsTable: React.FC<SubscriptionsTableProps> = ({
  customerId,
  setOpenModal
}) => {
  const [subscriptions, setSubscriptions] = useState<any[]>([]); // [1
  const [isDataFetched, setIsDataFetched] = useState(false);
  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch('/subscriptions/search',{
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({
            customerId: customerId
          })
        });
        const data = await response.json();
        setSubscriptions(data);
        setIsDataFetched(true);
      } catch (error) {
        console.error('Error fetching customer data:', error);
      }
    };
    if (!isDataFetched) {
      fetchData();
    }
  }, [isDataFetched]); // Empty dependency array ensures the effect runs once when the component mounts


  return (
    <div className="max-w-[600px]">
      <Table hoverable>
        <Table.Head>
          <Table.HeadCell>Subscription</Table.HeadCell>
          <Table.HeadCell>Start date</Table.HeadCell>
          <Table.HeadCell>Status</Table.HeadCell>
          <Table.HeadCell>
            <span className="sr-only">Edit</span>
          </Table.HeadCell>
        </Table.Head>
        {isDataFetched ? 
          <Table.Body className="divide-y">
            {subscriptions.map((subscription: any, i:number) => {
              return <Table.Row key={i} className="bg-white dark:border-gray-700 dark:bg-gray-800">
              <Table.Cell className="whitespace-nowrap font-medium text-gray-900 dark:text-white">
                {subscription.name}
              </Table.Cell>
              <Table.Cell>{subscription.startDate}</Table.Cell>
              <Table.Cell>{subscription.status}</Table.Cell>
              <Table.Cell>
                <p className="font-medium text-cyan-600 cursor-pointer hover:underline dark:text-cyan-500"
                onClick={() => setOpenModal(true)}>
                  Edit
                </p>
              </Table.Cell>
            </Table.Row>
            })}
          </Table.Body> : <Skeleton/>        
      }
      </Table>
    </div>
  );
}

export default SubscriptionsTable
