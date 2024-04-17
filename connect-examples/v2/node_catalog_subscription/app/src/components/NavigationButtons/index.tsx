import React, { useContext } from "react";
import { Button } from "flowbite-react";
import ComponentLayout from "../ComponentLayout";
import { AppContext, AppDispatchContext } from "../../context/AppContext";

interface NavigationButtonsProps {}

const NavigationButtons: React.FC<NavigationButtonsProps> = () => {
  const { currentStep, isNextDisabled } = useContext(AppContext);
  const dispatch = useContext(AppDispatchContext);

  return (
    <div>
      <ComponentLayout title="src/components/NavigationButtons/index.tsx">
        <div className="flex space-x-4">
          {currentStep !== 0 && (
            <Button
              className="text-white bg-blue-500 hover:bg-blue-700"
              onClick={() =>
                dispatch({ type: "SET_CURRENT_STEP", payload: currentStep - 1 })
              }
            >
              Previous
            </Button>
          )}
          {currentStep !== 3 && (
            <Button
              className={`${isNextDisabled ? "bg-gray-400 cursor-not-allowed" : "bg-blue-500 hover:bg-blue-700"}`}
              onClick={() =>
                dispatch({ type: "SET_CURRENT_STEP", payload: currentStep + 1 })
              }
              disabled={isNextDisabled}
            >
              {currentStep === 0
                ? "+ New Subscription"
                : `Step ${currentStep + 1}`}
            </Button>
          )}
        </div>
      </ComponentLayout>
    </div>
  );
};

export default NavigationButtons;
