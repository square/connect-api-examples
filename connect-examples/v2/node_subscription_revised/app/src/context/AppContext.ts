import { createContext } from "react";
import { CustomerData } from "../components/Customers";
import {
  SubscriptionPlanData,
  SubscriptionPlanVariation,
} from "../components/SubscriptionPlans";
import { Item } from "../components/ItemCatalog";

type State = {
  currentStep: number;
  showDebug: boolean;
  isNextDisabled: boolean;
  selectedCustomer: CustomerData;
  selectedSubscriptionPlan: SubscriptionPlanData;
  selectedSubscriptionPlanVariation: SubscriptionPlanVariation;
  eligibleCategoryIds: string[];
  selectedItems: Item[];
  showToast: boolean;
};

type Action = {
  type:
    | "SET_CUSTOMER"
    | "SET_SUBSCRIPTION"
    | "SET_CURRENT_STEP"
    | "SET_SHOW_DEBUG"
    | "SET_ITEMS"
    | "SET_NEXT_STATE"
    | "SUBMIT_ORDER"
    | "DISMISS_TOAST"
    | "TOGGLE_DEBUG"
    | "RESET";
  payload: any;
};

export const initialState: State = {
  currentStep: 0,
  showDebug: false,
  isNextDisabled: true,
  selectedCustomer: { id: "", givenName: "", familyName: "", emailAddress: "" },
  selectedSubscriptionPlan: {
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
  },
  selectedSubscriptionPlanVariation: {
    id: "",
    type: "",
    updatedAt: "",
    version: "",
    isDeleted: false,
    presentAtAllLocations: true,
    subscriptionPlanVariationData: { name: "", phases: [] },
  },
  selectedItems: [],
  eligibleCategoryIds: [],
  showToast: false,
};

export const AppContext = createContext(initialState);

export const AppDispatchContext = createContext(
  (() => {}) as React.Dispatch<Action>,
);

export const appReducer = (state: State, action: Action) => {
  switch (action.type) {
    case "SET_NEXT_STATE":
      return {
        ...state,
        isNextDisabled: action.payload,
      };
    case "SET_CUSTOMER":
      return {
        ...state,
        selectedCustomer: action.payload,
        isNextDisabled: false,
      };
    case "SET_SUBSCRIPTION":
      return {
        ...state,
        selectedSubscriptionPlan: action.payload.subscriptionPlan,
        eligibleCategoryIds: action.payload.eligibleCategoryIds,
        isNextDisabled: false,
        selectedSubscriptionPlanVariation:
          action.payload.selectedSubscriptionPlanVariation,
      };
    case "SET_ITEMS":
      return {
        ...state,
        selectedItems: action.payload,
      };
    case "SET_CURRENT_STEP":
      return {
        ...state,
        currentStep: action.payload,
        isNextDisabled: true,
      };
    case "SUBMIT_ORDER":
      return {
        ...state,
        currentStep: 0,
        isNextDisabled: true,
        selectedCustomer: {
          id: "",
          givenName: "",
          familyName: "",
          emailAddress: "",
        },
        selectedSubscriptionPlan: {
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
        },
        selectedSubscriptionPlanVariation: {
          id: "",
          type: "",
          updatedAt: "",
          version: "",
          isDeleted: false,
          presentAtAllLocations: true,
          subscriptionPlanVariationData: { name: "", phases: [] },
        },
        selectedItems: [],
        eligibleCategoryIds: [],
        showToast: true,
      };
    case "RESET":
      return {
        ...state,
        currentStep: 0,
        isNextDisabled: true,
        selectedCustomer: {
          id: "",
          givenName: "",
          familyName: "",
          emailAddress: "",
        },
        selectedSubscriptionPlan: {
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
        },
        selectedSubscriptionPlanVariation: {
          id: "",
          type: "",
          updatedAt: "",
          version: "",
          isDeleted: false,
          presentAtAllLocations: true,
          subscriptionPlanVariationData: { name: "", phases: [] },
        },
        selectedItems: [],
        eligibleCategoryIds: [],
        showToast: false,
      };
    case "DISMISS_TOAST":
      return {
        ...state,
        showToast: false,
      };
    case "TOGGLE_DEBUG":
      return {
        ...state,
        showDebug: action.payload,
      };
    default:
      return state;
  }
};
