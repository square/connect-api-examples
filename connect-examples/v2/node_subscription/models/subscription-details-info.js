/*
Copyright 2020 Square Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

// we use moment.js to handle dates calculation
const moment = require("moment");

// Define the metadata of plan cadence so that we can calculate the dates
// for each plan phase definition.
const planCadenceMetadata = {
  "DAILY": {
    count: 1,
    unit: "days",
    label: "Day"
  },
  "WEEKLY": {
    count: 1,
    unit: "weeks",
    label: "Week"
  },
  "EVERY_TWO_WEEKS": {
    count: 2,
    unit: "weeks",
    label: "Week"
  },
  "THIRTY_DAYS": {
    count: 30,
    unit: "days",
    label: "Day"
  },
  "SIXTY_DAYS": {
    count: 60,
    unit: "days",
    label: "Day"
  },
  "NINETY_DAYS": {
    count: 90,
    unit: "days",
    label: "Day"
  },
  "MONTHLY": {
    count: 1,
    unit: "months",
    label: "Month"
  },
  "EVERY_TWO_MONTHS": {
    count: 2,
    unit: "months",
    label: "Month"
  },
  "QUARTERLY": {
    count: 3,
    unit: "months",
    label: "Month"
  },
  "EVERY_FOUR_MONTHS": {
    count: 4,
    unit: "months",
    label: "Month"
  },
  "EVERY_SIX_MONTHS": {
    count: 6,
    unit: "months",
    label: "Month"
  },
  "ANNUAL": {
    count: 1,
    unit: "years",
    label: "Year"
  },
  "EVERY_TWO_YEARS": {
    count: 2,
    unit: "years",
    label: "Year"
  },
};

/**
 * The clase that translate subscriptionPlan and activeSubscription informaiton
 * into human readable messages and map the active subscription to a phase of subscription plan
 */
class SubscriptionDetailsInfo {
  constructor(subscriptionPlan, activeSubscription) {
    this.activeSubscription = activeSubscription;
    this.subscriptionPlan = subscriptionPlan;
    this.startDate = activeSubscription ? moment(activeSubscription.startDate) : undefined;
    this.phaseInfos = [];
    this.constructPlanPhaseInfos();
  }

  /**
   * Get the translated plan phase informaiton
   */
  get planPhaseInfos() {
    return this.phaseInfos;
  }

  /**
   * construct the translated subscription plan phase information
   */
  constructPlanPhaseInfos() {
    for (const key in this.subscriptionPlan.phases) {
      const phase = this.subscriptionPlan.phases[key];
      const prevPhaseInfo = key > 0 ? this.phaseInfos[key-1] : undefined;
      const phaseInfo = {};

      phaseInfo.title = phase.recurringPriceMoney.amount < 1 ?
        `${this.getPeriodDescription(phase)} Free Trial`
        : `${this.getPeriodDescription(phase)} Subscription`;
      const curDate = moment();

      if (this.startDate) {
        // the subscription started for this plan
        // so that we can calculate the specific time for each phase
        const phaseMetadata = planCadenceMetadata[phase.cadence];
        let phaseEndDate;
        if (phase.periods) {
          phaseEndDate =  prevPhaseInfo ?
            moment(prevPhaseInfo.endDate).add(phaseMetadata.count * phase.periods, phaseMetadata.unit)
            : moment(this.startDate).add(phaseMetadata.count * phase.periods, phaseMetadata.unit);
        }
        phaseInfo.endDate = phaseEndDate;

        // Set the label of the phase information
        if (curDate.isAfter(phaseEndDate)) {
          phaseInfo.label = "Expired";
        } else if (
          !prevPhaseInfo
          || (curDate.isAfter(prevPhaseInfo.endDate) && curDate.isBefore(phase.endDate))
          || (!phaseEndDate && curDate.isAfter(prevPhaseInfo.endDate))
        ) {
          phaseInfo.label = "Current";
        } else {
          phaseInfo.label = `Starts on ${prevPhaseInfo.endDate.format("YYYY-MM-DD")}`;
        }

        // Set the subtitle of the phase information
        const paymentDesc = phaseInfo.label === "Current" && phase.recurringPriceMoney.amount >= 100 ?
          ` - Payment due on ${this.activeSubscription.paidUntilDate}`
          : "";

        phaseInfo.subTitle = `$${(parseInt(phase.recurringPriceMoney.amount) / 100).toFixed(2)}${paymentDesc}`;
      } else {
        // The subscription plan has no active subscription
        phaseInfo.label =  "Starts on subscription";
        if (prevPhaseInfo) {
          phaseInfo.label = `Starts after ${prevPhaseInfo.title}`;
        }
        phaseInfo.subTitle = `$${(parseInt(phase.recurringPriceMoney.amount) / 100).toFixed(2)}`;
      }
      this.phaseInfos.push(phaseInfo);
    }
  }

  /**
   * Get the description of the plan phase's period
   * @param {*} phase the current plan phase
   */
  getPeriodDescription(phase) {
    return phase.periods ?
      `${phase.periods} ${planCadenceMetadata[phase.cadence].label}${phase.periods > 1 ? "s" : "" }`
      : phase.cadence[0] + phase.cadence.slice(1).toLowerCase();
  }
}

module.exports = SubscriptionDetailsInfo;
