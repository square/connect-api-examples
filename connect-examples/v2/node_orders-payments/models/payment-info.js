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
 * Payment information for the page.
 *
 * Description:
 *  This class is used to abstract away the payment object created by createPayment api and more easily access
 *  its information using getters.
 */
class PaymentInfo {
  constructor(payment){
    this.paymentInfo = payment;
  }
  // Returns paymentInfo id
  get paymentId() {
    return this.paymentInfo.id;
  }
  // Returns amount of money in the order.
  get amountMoney(){
    return this.paymentInfo.amount_money.amount;
  }
  // Returns currency of the payment
  get currency(){
    return this.paymentInfo.amount_money.currency;
  }
  // Returns card brand of the CC payment, if card was used.
  get cardBrand(){
    return this.paymentInfo.card_details.card.card_brand.replace("_", " ").split(" ").map( w =>  w.substring(0, 1).toUpperCase()+ w.substring(1).toLowerCase()).join(" ");
  }
  // Returns last 4 digits of CC payment, if card was used.
  get last4Digits(){
    return this.paymentInfo.card_details.card.last_4;
  }
  // Returns status of the payment
  get status(){
    return this.paymentInfo.status;
  }
  // Returns creation date
  get createdAt(){
    return this.paymentInfo.created_at;
  }
}

module.exports = PaymentInfo;
