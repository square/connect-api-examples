import React, { useState, useEffect, useContext } from 'react';
import CustomerTile from './CustomerTile';
import { Card as FlowCard} from 'flowbite-react';
import Skeleton from '../Skeleton';

export interface CustomerData {
  id: string;
  givenName: string;
  familyName: string;
  emailAddress: string;
  cards?: Card[];
}

interface Card {
  id: string;
  cardBrand: string;
  last4: string;
  expMonth: string;
  expYear: string;
  billingAddress: {
    postalCode: string;
  };
}

interface CustomerProps {
}

const Customers: React.FC<CustomerProps> = ({}) => {
  const [customers, setCustomers] = useState<CustomerData[]>([]);
  const [isLoading, setIsLoading] = useState<boolean>(true);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch('/customers');
        const data = await response.json();
        setCustomers(data);
        setIsLoading(false);
      } catch (error) {
        console.error('Error fetching customer data:', error);
      }
    };

    fetchData();
  }, []); // Empty dependency array ensures the effect runs once when the component mounts

  return (
    <>
    {isLoading ? <FlowCard>
        <Skeleton/>
      </FlowCard> :

    customers.map((customer, i) =>
     <CustomerTile key={i} customer={customer} isActionable={true}/>
    )}
    </>
  );
};

export default Customers;
