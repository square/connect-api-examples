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
  constructor(order){
    this.order = order;
    this.date_format = { weekday: "long", month: "long", day: "numeric", hour: "numeric", minute: "2-digit" };
    // populate line Items
    this.line_items = order.line_items.map(line_item => ({
      name: line_item.name,
      quantity: line_item.quantity,
      total_money: (line_item.total_money.amount / 100).toFixed(2),
    }));
  }
  // Returns order ID
  get orderId() {
    return this.order.id;
  }
  // Returns the recipient's name
  get recipientName(){
    return this.order.fulfillments[0].pickup_details.recipient.display_name;
  }
  // Returns the pickup time of the order.
  get pickupTime(){
    const pickupDate = new Date(this.order.fulfillments[0].pickup_details.pickup_at).toLocaleDateString("en-US", this.date_format);
    return pickupDate;
  }
  // Returns true if fulfillments info exist
  get hasFulfillments() {
    return !!this.order.fulfillments;
  }
  // Returns the line items ordered in the order
  get lineItems(){
    return this.line_items;
  }
  // Returns location Id
  get locationId(){
    return this.order.location_id;
  }
  // Returns creation date
  get createdAt(){
    return this.order.created_at;
  }
  // Returns money spent in order
  get totalMoney(){
    return (this.order.total_money.amount / 100).toFixed(2);
  }
  // Returns fulfillment status
  get fulfillmentState() {
    return this.order.fulfillments[0].state;
  }
}

module.exports = OrderInfo;
