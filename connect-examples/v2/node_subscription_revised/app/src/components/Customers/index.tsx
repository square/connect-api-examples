import React, { useState, useEffect } from 'react';
import CustomerTile from './CustomerTile';

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
  onSelectCustomer: (customer: CustomerData) => void;
  setIsNextDisabled: (isNextDisabled: boolean) => void;
}

const Customers: React.FC<CustomerProps> = ({ onSelectCustomer, setIsNextDisabled }) => {
  const [selectedCustomerId, setSelectedCustomer] = useState<string | null>(null);
  const [customers, setCustomers] = useState<CustomerData[]>([]);


  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch('/customers');
        const data = await response.json();
        setCustomers(data);
      } catch (error) {
        console.error('Error fetching customer data:', error);
      }
    };

    fetchData();
  }, []); // Empty dependency array ensures the effect runs once when the component mounts


  const handleCustomerSelection = (customer: CustomerData) => {
    setSelectedCustomer(customer?.id);
    onSelectCustomer(customer);
    setIsNextDisabled(false);
  };

  return (
    <>
    {customers.map((customer, i) =>
     <CustomerTile key={i} customer={customer} selectedCustomerId={selectedCustomerId} handleCustomerSelection={handleCustomerSelection}/>
    )}
    </>
  );
};

export default Customers;
