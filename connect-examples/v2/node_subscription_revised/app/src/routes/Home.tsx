import React, { useState } from "react";
import Customers, { CustomerData } from "../components/Customers"; // Adjust the path accordingly
import NavigationButtons from "../components/NavigationButtons";
import SubscriptionPlans, {
  SubscriptionPlanData,
} from "../components/SubscriptionsPlans";
import ItemCatalog from "../components/ItemCatalog";
import { Item } from "../components/ItemCatalog";
import Layout from "../components/Layout";
import ReviewDetails from "../components/ReviewDetails";
import { Toast } from "flowbite-react";
import { FcOk } from "react-icons/fc";

export interface SubscriptionPlanIds {
  subscriptionPlanId: string;
  subscriptionPlanVariationId: string;
}

interface HomeProps {}

const Home: React.FC<HomeProps> = () => {
  const [selectedCustomer, setSelectedCustomer] = useState<CustomerData>({
    id: "",
    givenName: "",
    familyName: "",
    emailAddress: "",
  });
  const [subscriptionPlan, setSubscriptionPlan] =
    useState<SubscriptionPlanData>({
      id: "",
      type: "",
      updatedAt: "",
      version: "",
      isDeleted: false,
      presentAtAllLocations: true,
      subscriptionPlanData: {
        name: "",
        eligibleCategoryIds: [],
        subscriptionPlanVariations: [],
      },
    });
  const [subscriptionPlanIds, setSubscriptionPlanIds] =
    useState<SubscriptionPlanIds>({
      subscriptionPlanId: "",
      subscriptionPlanVariationId: "",
    });
  const [eligibleCategoryIds, setEligibleCategoryIds] = useState<string[]>([]);
  const [selectedItems, setSelectedItems] = useState<Item[]>([]);
  const [currentStep, setCurrentStep] = useState<number>(0);
  const [showToast, setShowToast] = useState<boolean>(false);
  const [isNextDisabled, setIsNextDisabled] = useState<boolean>(true);

  return (
    <div className="min-h-screen flex flex-col bg-gray-40">
      {/* Header */}
      <header className="bg-black text-white p-4 flex">
        <h1 className="text-2xl font-semibold">Munch.ie</h1>
      </header>

      {/* Main Content */}
      <main className="flex-1 container mx-auto my-8">
        <div className="flex justify-end">
          <NavigationButtons
            currentStep={currentStep}
            onPrevClick={() => setCurrentStep(currentStep - 1)}
            onNextClick={() => {
              setCurrentStep(currentStep + 1);
              setIsNextDisabled(true);
            }}
            isNextDisabled={isNextDisabled}
          />
        </div>
        {currentStep === 0 && (
          <Layout title="Select a Customer">
            <Customers
              onSelectCustomer={setSelectedCustomer}
              setIsNextDisabled={setIsNextDisabled}
            />
          </Layout>
        )}
        {currentStep === 1 && (
          <Layout title="Select a Subscription Plan">
            <SubscriptionPlans
              selectedSubscriptionPlanId={
                subscriptionPlanIds?.subscriptionPlanId
              }
              onSelectSubscriptionPlanIds={setSubscriptionPlanIds}
              setEligibleCategoryIds={setEligibleCategoryIds}
              setSubscriptionPlan={setSubscriptionPlan}
              setIsNextDisabled={setIsNextDisabled}
            />
          </Layout>
        )}
        {currentStep === 2 && (
          <Layout title="Select Catalog Items">
            <ItemCatalog
              categoryIds={eligibleCategoryIds}
              setIsNextDisabled={setIsNextDisabled}
              setSelectedItems={setSelectedItems}
              selectedItems={selectedItems}
            />
          </Layout>
        )}
        {currentStep === 3 && (
          <Layout title="Review Subscription Details">
            <ReviewDetails
              customer={selectedCustomer}
              subscriptionPlanIds={subscriptionPlanIds}
              subscriptionPlan={subscriptionPlan}
              subscribed_items={selectedItems}
              setShowToast={setShowToast}
              setCurrentStep={setCurrentStep}
              currentStep={currentStep}
            />
          </Layout>
        )}
        <div className="">
          {showToast && (
            <Toast className="fixed bottom-8 left-1/2 transform -translate-x-1/2 ">
              <FcOk className="h-5 w-5" />
              <div className=" ml-4 text-sm font-normal">
                Subscription Successfully Created
              </div>
              <Toast.Toggle onDismiss={() => setShowToast(false)} />
            </Toast>
          )}
        </div>
      </main>
    </div>
  );
};

export default Home;
