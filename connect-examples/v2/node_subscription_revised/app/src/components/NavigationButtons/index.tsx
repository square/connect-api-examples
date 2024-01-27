import React from 'react';
import { Button } from 'flowbite-react';

interface NavigationButtonsProps {
  currentStep: number;
  onPrevClick: () => void;
  onNextClick: () => void;
  isNextDisabled: boolean;
}

const NavigationButtons: React.FC<NavigationButtonsProps> = ({ currentStep, onPrevClick, onNextClick, isNextDisabled }) => {
  const isPrevDisabled = currentStep === 0;

  return (
    <div className="flex space-x-4">
      <Button
        className={`text-white ${isPrevDisabled ? 'bg-gray-400 cursor-not-allowed' : 'bg-blue-500 hover:bg-blue-700'}`}
        onClick={onPrevClick}
        disabled={isPrevDisabled}
      >
        Previous
      </Button>
      <Button
        className={`${isNextDisabled ? 'bg-gray-400 cursor-not-allowed' : 'bg-blue-500 hover:bg-blue-700'}`}
        onClick={onNextClick}
        disabled={isNextDisabled}
      >
        Step {currentStep + 2}
      </Button>
    </div>
  )
}

export default NavigationButtons;
