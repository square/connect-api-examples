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
    this.date_format = {
      weekday: "long",
      month: "long",
      day: "numeric",
      hour: "numeric",
      minute: "2-digit",
    };
    // populate line Items
    this.line_items = order.line_items.map((line_item) => ({
      name: line_item.name,
      quantity: line_item.quantity,
      gross_sales_money: this.getDecimalAmount(line_item.gross_sales_money.amount),
      catalog_object_id: line_item.catalog_object_id,
    }));

    this.order_discounts = [];
    if (order.discounts) {
      this.order_discounts = order.discounts.map((discount) => ({
        name: discount.name,
        applied_discount_money: this.getDecimalAmount(discount.applied_money.amount)
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
      this.order.fulfillments[0].pickup_details.recipient.display_name :
      this.order.fulfillments[0].shipment_details.recipient.display_name;
  }
  // Returns the expected pickup time of the order.
  get pickupTime() {
    const pickupDate = new Date(
      this.order.fulfillments[0].pickup_details.pickup_at
    ).toLocaleDateString("en-US", this.date_format);
    return pickupDate;
  }
  // Returns the expected delivery time of the order.
  get deliveryTime() {
    const deliveryTime = new Date(
      this.order.fulfillments[0].shipment_details.expected_shipped_at
    ).toLocaleDateString("en-US", this.date_format);
    return deliveryTime;
  }
  // Returns true if fulfillments info exist
  get hasFulfillments() {
    return !!this.order.fulfillments;
  }
  // Returns the line items ordered in the order
  get lineItems() {
    return this.line_items;
  }
  // Returns location Id
  get locationId() {
    return this.order.location_id;
  }
  // Returns creation date
  get createdAt() {
    return this.order.created_at;
  }
  // Returns discounts
  get discounts() {
    return this.order_discounts;
  }
  // Returns discount in order
  get totalDiscountMoney() {
    return this.getDecimalAmount(this.order.total_discount_money.amount);
  }
  // Returns service fee in order
  get totalServiceChargeMoney() {
    return this.getDecimalAmount(this.order.total_service_charge_money.amount);
  }
  // Returns tax in order
  get totalTaxMoney() {
    return this.getDecimalAmount(this.order.total_tax_money.amount);
  }
  // The subtotal money before tax applied
  get preTaxTotalMoney() {
    return (this.totalMoney - this.totalTaxMoney).toFixed(2);
  }
  // Returns money spent in order
  get totalMoney() {
    return this.getDecimalAmount(this.order.total_money.amount);
  }
  // Returns fulfillment status
  get fulfillmentState() {
    return this.order.fulfillments[0].state;
  }
  // Returns delivery street address
  get deliveryAddress() {
    return this.order.fulfillments[0].shipment_details.recipient.address
      .address_line_1;
  }
  // Returns delivery city, state, postal code as one line string
  get deliveryCityAndPostalCode() {
    const address = this.order.fulfillments[0].shipment_details.recipient
      .address;
    return `${address.locality}, ${address.administrative_district_level_1}, ${address.postal_code}`;
  }
  // Returns the payment card details of this order
  get card() {
    // In this example, there is only one tender for each order
    // so we can always assume the first tender will be the payment card details
    return this.order.tenders && this.order.tenders.length > 0 ?
      this.order.tenders[0].card_details.card : null;
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
