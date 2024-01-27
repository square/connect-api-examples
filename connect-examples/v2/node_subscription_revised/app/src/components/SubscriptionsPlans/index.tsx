import React, { useEffect, useState } from 'react';
import { SubscriptionPlanIds } from '../../App';
import SubscriptionTile from './SubscriptionTile';

interface SubscriptionPlanProps {
    onSelectSubscriptionPlanIds: (subscriptionPlanIds: SubscriptionPlanIds) => void;
    selectedSubscriptionPlanId: string | null;
    setEligibleCategoryIds: (eligibleCategoryIds: string[]) => void;
    setSubscriptionPlan: (subscriptionPlan: SubscriptionPlanData) => void;
    setIsNextDisabled: (isNextDisabled: boolean) => void;
}

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


const SubscriptionPlans: React.FC<SubscriptionPlanProps> = ({onSelectSubscriptionPlanIds, setIsNextDisabled, setEligibleCategoryIds, selectedSubscriptionPlanId, setSubscriptionPlan}) => {
    const [subscriptionPlans, setSubscriptionPlans] = useState<SubscriptionPlanData[]>([]);

    useEffect(() => {
        const fetchData = async () => {
          try {
            const response = await fetch('/subscription_plans');
            const data = await response.json();
            setSubscriptionPlans(data);
          } catch (error) {
            console.error('Error fetching customer data:', error);
          }
        };
    
        fetchData();
      }, []); // Empty dependency array ensures the effect runs once when the component mounts
    
      const handleSubscriptionPlanSelection = (subscriptionPlan: SubscriptionPlanData, subscriptionPlanVariationId: string) => {
        onSelectSubscriptionPlanIds({subscriptionPlanId:subscriptionPlan.id, subscriptionPlanVariationId});
        setIsNextDisabled(false);
        setEligibleCategoryIds(subscriptionPlan.subscriptionPlanData.eligibleCategoryIds)
        setSubscriptionPlan(subscriptionPlan)
      };
    
    return (
        <>
        {subscriptionPlans.map((subscriptionPlan: SubscriptionPlanData, i:number) => (
            <SubscriptionTile key={i} subscriptionPlan={subscriptionPlan} handleSubscriptionPlanSelection={handleSubscriptionPlanSelection} selectedSubscription={selectedSubscriptionPlanId}/>
        ))}
      </>
    );
}

export default SubscriptionPlans;
