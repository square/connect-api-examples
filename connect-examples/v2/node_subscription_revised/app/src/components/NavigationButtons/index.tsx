import React from "react";
import { Button } from "flowbite-react";

interface NavigationButtonsProps {
  currentStep: number;
  onPrevClick: () => void;
  onNextClick: () => void;
  isNextDisabled: boolean;
}

const NavigationButtons: React.FC<NavigationButtonsProps> = ({
  currentStep,
  onPrevClick,
  onNextClick,
  isNextDisabled,
}) => {
  const isPrevDisabled = currentStep === 0;

  return (
    <div className="flex space-x-4">
      <Button
        className={`text-white ${
          isPrevDisabled
            ? "bg-gray-20 cursor-not-allowed"
            : "bg-blue hover:bg-blue-10"
        }`}
        onClick={onPrevClick}
        disabled={isPrevDisabled}
      >
        Previous
      </Button>
      {currentStep !== 3 && (
        <Button
          className={`${
            isNextDisabled
              ? "bg-gray-20 cursor-not-allowed"
              : "bg-blue hover:bg-blue-10"
          }`}
          onClick={onNextClick}
          disabled={isNextDisabled}
        >
          {currentStep === 0 ? "Subscription Flow" : `Step ${currentStep + 1}`}
        </Button>
      )}
    </div>
  );
};

export default NavigationButtons;
