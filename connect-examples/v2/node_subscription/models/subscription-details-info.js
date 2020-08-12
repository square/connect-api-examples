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
const plan_cadence_metadata = {
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
 * The clase that translate subscrption_plan and active_subscription informaiton
 * into human readable messages and map the active subscription to a phase of subscription plan
 */
class SubscriptionDetailsInfo {
  constructor(subscription_plan, active_subscription) {
    this.active_subscription = active_subscription;
    this.subscription_plan = subscription_plan;
    this.start_date = active_subscription ? moment(active_subscription.start_date) : undefined;
    this.phase_infos = [];
    this.constructPlanPhaseInfos();
  }

  /**
   * Get the translated plan phase informaiton
   */
  get plan_phase_infos() {
    return this.phase_infos;
  }

  /**
   * construct the translated subscription plan phase information
   */
  constructPlanPhaseInfos() {
    for (const key in this.subscription_plan.phases) {
      const phase = this.subscription_plan.phases[key];
      const prev_phase_info = key > 0 ? this.phase_infos[key-1] : undefined;
      const phase_info = {};

      phase_info.title = phase.recurring_price_money.amount < 1 ?
        `${this.getPeriodDescription(phase)} Free Trial`
        : `${this.getPeriodDescription(phase)} Subscription`;
      const cur_date = moment();

      if (this.start_date) {
        // the subscription started for this plan
        // so that we can calculate the specific time for each phase
        const phase_metadata = plan_cadence_metadata[phase.cadence];
        let phase_end_date;
        if (phase.periods) {
          phase_end_date =  prev_phase_info ?
            moment(prev_phase_info.end_date).add(phase_metadata.count * phase.periods, phase_metadata.unit)
            : moment(this.start_date).add(phase_metadata.count * phase.periods, phase_metadata.unit);
        }
        phase_info.end_date = phase_end_date;

        // Set the label of the phase information
        if (cur_date.isAfter(phase_end_date)) {
          phase_info.label = "Expired";
        } else if (
          !prev_phase_info
          || (cur_date.isAfter(prev_phase_info.end_date) && cur_date.isBefore(phase.end_date))
          || (!phase_end_date && cur_date.isAfter(prev_phase_info.end_date))
        ) {
          phase_info.label = "Current";
        } else {
          phase_info.label = `Starts on ${prev_phase_info.end_date.format("YYYY-MM-DD")}`;
        }

        // Set the subtitle of the phase information
        const payment_desc = phase_info.label === "Current" && phase.recurring_price_money.amount >= 100 ?
          ` - Payment due on ${this.active_subscription.paid_until_date}`
          : "";

        phase_info.sub_title = `$${(phase.recurring_price_money.amount / 100).toFixed(2)}${payment_desc}`;
      } else {
        // The subscription plan has no active subscription
        phase_info.label =  "Starts on subscription";
        if (prev_phase_info) {
          phase_info.label = `Starts after ${prev_phase_info.title}`;
        }
        phase_info.sub_title = `$${(phase.recurring_price_money.amount / 100).toFixed(2)}`;
      }
      this.phase_infos.push(phase_info);
    }
  }

  /**
   * Get the description of the plan phase's period
   * @param {*} phase the current plan phase
   */
  getPeriodDescription(phase) {
    return phase.periods ?
      `${phase.periods} ${plan_cadence_metadata[phase.cadence].label}${phase.periods > 1 ? "s" : "" }`
      : phase.cadence[0] + phase.cadence.slice(1).toLowerCase();
  }
}

module.exports = SubscriptionDetailsInfo;
