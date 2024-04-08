import React, { useEffect, useState } from 'react';
import SubscriptionTile from './SubscriptionTile';
import { Card } from 'flowbite-react';
import Skeleton from '../Skeleton';

interface SubscriptionPlanProps {}

export interface Phase {
    uid: string;
    cadence: string;
    periods: number;
    ordinal: number;
    pricing: {
        type: string;
    }
}

export interface SubscriptionPlanVariation {
    type: string;
    id: string;
    updatedAt: string;
    version: string;
    isDeleted: boolean;
    presentAtAllLocations: boolean;
    subscriptionPlanVariationData: {
        name: string;
        phases: Phase[];
    }
}

export interface SubscriptionPlanData {
    type: string;
    id: string;
    updatedAt: string;
    version: string;
    isDeleted: boolean;
    presentAtAllLocations: true;
    subscriptionPlanData: {
        name: string;
        eligibleCategoryIds: string[];
        subscriptionPlanVariations: SubscriptionPlanVariation[]
    },
}


const SubscriptionPlans: React.FC<SubscriptionPlanProps> = () => {
    const [subscriptionPlans, setSubscriptionPlans] = useState<SubscriptionPlanData[]>([]);
    const [isLoading, setIsLoading] = useState<boolean>(true);

    useEffect(() => {
        const fetchData = async () => {
          try {
            const response = await fetch('/subscriptions');
            const data = await response.json();
            setSubscriptionPlans(data);
            setIsLoading(false);
          } catch (error) {
            console.error('Error fetching customer data:', error);
          }
        };
    
        fetchData();
      }, []); // Empty dependency array ensures the effect runs once when the component mounts
        
    return (
        <>
        {isLoading ? <Card>
            <Skeleton/>
        </Card> :
        subscriptionPlans.map((subscriptionPlan: SubscriptionPlanData, i:number) => (
            <SubscriptionTile key={i} subscriptionPlan={subscriptionPlan} showButton={true}/>
        ))}
      </>
    );
}

export default SubscriptionPlans;
