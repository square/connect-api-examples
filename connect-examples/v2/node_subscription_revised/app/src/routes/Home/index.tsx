import React, { useContext } from "react";
import Customers from "../../components/Customers"; // Adjust the path accordingly
import SubscriptionPlans from "../../components/SubscriptionPlans";
import ItemCatalog from "../../components/ItemCatalog";
import Layout from "../../components/Layout";
import ReviewOrderDetails from "../../components/ReviewOrderDetails";
import { Toast } from "flowbite-react";
import { FcOk } from "react-icons/fc";
import { AppContext, AppDispatchContext } from "../../context/AppContext";

export interface SubscriptionPlanIds {
  subscriptionPlanId: string;
  subscriptionPlanVariationId: string;
}

interface HomeProps {}

const Home: React.FC<HomeProps> = () => {
  const { currentStep, showToast } = useContext(AppContext);
  const dispatch = useContext(AppDispatchContext);

  return (
    <>
      {currentStep === 0 && (
        <Layout
          title="Select a Customer"
          subTitle="src/components/Customers/index.tsx"
        >
          <Customers />
        </Layout>
      )}
      {currentStep === 1 && (
        <Layout
          title="Select a Subscription Plan"
          subTitle="src/components/SubscriptionPlans/index.tsx"
        >
          <SubscriptionPlans />
        </Layout>
      )}
      {currentStep === 2 && (
        <Layout
          title="Select Catalog Items"
          subTitle="src/components/ItemCatalog/index.tsx"
        >
          <ItemCatalog />
        </Layout>
      )}
      {currentStep === 3 && (
        <Layout
          title="Review Details"
          subTitle="src/components/ReviewOrderDetails/index.tsx"
        >
          <ReviewOrderDetails />
        </Layout>
      )}
      <div className="">
        {showToast && (
          <Toast className="fixed bottom-8 left-1/2 transform -translate-x-1/2 ">
            <FcOk className="h-5 w-5" />
            <div className=" ml-4 text-sm font-normal">
              Subscription Successfully Created
            </div>
            <Toast.Toggle
              onDismiss={() =>
                dispatch({ type: "DISMISS_TOAST", payload: null })
              }
            />
          </Toast>
        )}
      </div>
    </>
  );
};

export default Home;
