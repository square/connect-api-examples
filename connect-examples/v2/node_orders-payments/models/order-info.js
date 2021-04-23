/*
Copyright 2019 Square Inc.

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

/**
 * Order information for the page.
 *
 * Description:
 *  This class is used to abstract away the order object created by createOrder api and more easily access
 *  its information using getters.
 */
class OrderInfo {
  constructor(order) {
    this.order = order;
    this.dateFormat = {
      weekday: "long",
      month: "long",
      day: "numeric",
      hour: "numeric",
      minute: "2-digit",
    };
    // populate line Items
    this._lineItems = order.lineItems.map((lineItem) => ({
      name: lineItem.name,
      quantity: lineItem.quantity,
      grossSalesMoney: this.getDecimalAmount(parseInt(lineItem.grossSalesMoney.amount)),
      catalogObjectId: lineItem.catalogObjectId,
    }));

    this.orderDiscounts = [];
    if (order.discounts) {
      this.orderDiscounts = order.discounts.map((discount) => ({
        name: discount.name,
        appliedDiscountMoney: this.getDecimalAmount(parseInt(discount.appliedMoney.amount))
      }));
    }
  }
  // Returns order ID
  get orderId() {
    return this.order.id;
  }
  // Returns if the order is a pickup order
  get isPickup() {
    return this.order.fulfillments[0].type === "PICKUP";
  }
  // Returns the recipient's name
  get recipientName() {
    return this.isPickup ?
      this.order.fulfillments[0].pickupDetails.recipient.displayName :
      this.order.fulfillments[0].shipmentDetails.recipient.displayName;
  }
  // Returns the expected pickup time of the order.
  get pickupTime() {
    const pickupDate = new Date(
      this.order.fulfillments[0].pickupDetails.pickupAt
    ).toLocaleDateString("en-US", this.dateFormat);
    return pickupDate;
  }
  // Returns the expected delivery time of the order.
  get deliveryTime() {
    const deliveryTime = new Date(
      this.order.fulfillments[0].shipmentDetails.expectedShippedAt
    ).toLocaleDateString("en-US", this.dateFormat);
    return deliveryTime;
  }
  // Returns true if fulfillments info exist
  get hasFulfillments() {
    return !!this.order.fulfillments;
  }
  // Returns the line items ordered in the order
  get lineItems() {
    return this._lineItems;
  }
  // Returns location Id
  get locationId() {
    return this.order.locationId;
  }
  // Returns creation date
  get createdAt() {
    return this.order.createdAt;
  }
  // Returns discounts
  get discounts() {
    return this.orderDiscounts;
  }
  // Returns discount in order
  get totalDiscountMoney() {
    return this.getDecimalAmount(parseInt(this.order.totalDiscountMoney.amount));
  }
  // Returns service fee in order
  get totalServiceChargeMoney() {
    return this.getDecimalAmount(parseInt(this.order.totalServiceChargeMoney.amount));
  }
  // Returns tax in order
  get totalTaxMoney() {
    return this.getDecimalAmount(parseInt(this.order.totalTaxMoney.amount));
  }
  // The subtotal money before tax applied
  get preTaxTotalMoney() {
    return (this.totalMoney - this.totalTaxMoney).toFixed(2);
  }
  // Returns money spent in order
  get totalMoney() {
    return this.getDecimalAmount(parseInt(this.order.totalMoney.amount));
  }
  // Returns fulfillment status
  get fulfillmentState() {
    return this.order.fulfillments[0].state;
  }
  // Returns delivery street address
  get deliveryAddress() {
    return this.order.fulfillments[0].shipmentDetails.recipient.address
      .addressLine1;
  }
  // Returns delivery city, state, postal code as one line string
  get deliveryCityAndPostalCode() {
    const address = this.order.fulfillments[0].shipmentDetails.recipient
      .address;
    return `${address.locality}, ${address.administrativeDistrictLevel1}, ${address.postalCode}`;
  }
  // Returns the payment card details of this order
  get card() {
    // In this example, there is only one tender for each order
    // so we can always assume the first tender will be the payment card details
    return this.order.tenders && this.order.tenders.length > 0 ?
      this.order.tenders[0].cardDetails.card : null;
  }
  // Returns the rewards applied to this order
  get rewards() {
    return this.order.rewards;
  }
  // Hepler function to convert the amount money to decimal amount
  getDecimalAmount(amount) {
    return (amount / 100).toFixed(2);
  }
}

module.exports = OrderInfo;
