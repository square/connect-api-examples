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
    this.orderInfo = order;
    this.dateFormat = { weekday: "long", month: "long", day: "numeric", hour: "numeric", minute: "2-digit" };
  }
  // Returns order ID
  get orderId() {
    return this.orderInfo.id;
  }
  // Returns the source of the order
  get source(){
    return this.orderInfo.source.name;
  }
  // Returns the recipient's name
  get recipientName(){
    return this.orderInfo.fulfillments[0].pickup_details.recipient.display_name;
  }
  // Returns the pickup time of the order.
  get pickupTime(){
    const pickupDate = new Date(this.orderInfo.fulfillments[0].pickup_details.pickup_at).toLocaleDateString("en-US", this.dateFormat);
    return pickupDate;
  }
  // Returns fulfillment type of the order
  get fulfillmentType(){
    return this.orderInfo.fulfillments.type;
  }
  // Returns the line items ordered in the order
  get lineItems(){
    return this.orderInfo.line_items;
  }
  // Returns location Id
  get locationId(){
    return this.orderInfo.location_id;
  }
  // Returns creation date
  get createdAt(){
    return this.orderInfo.created_at;
  }
  // Returns money spent in order
  get totalMoney(){
    return this.orderInfo.total_money;
  }
}

module.exports = OrderInfo;
