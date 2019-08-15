
# V1Payment

### Description

A payment represents a paid transaction between a Square merchant and a customer. Payment details are usually available from Connect API endpoints within a few minutes after the transaction completes.  Each Payment object includes several fields that end in `_money`. These fields describe the various amounts of money that contribute to the payment total:  <ul> <li> Monetary values are <b>positive</b> if they represent an <em>increase</em> in the amount of money the merchant receives (e.g., <code>tax_money</code>, <code>tip_money</code>). </li> <li> Monetary values are <b>negative</b> if they represent an <em>decrease</em> in the amount of money the merchant receives (e.g., <code>discount_money</code>, <code>refunded_money</code>). </li> </ul>

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **String** | The payment&#39;s unique identifier. |  [optional]
**merchantId** | **String** | The unique identifier of the merchant that took the payment. |  [optional]
**createdAt** | **String** | The time when the payment was created, in ISO 8601 format. Reflects the time of the first payment if the object represents an incomplete partial payment, and the time of the last or complete payment otherwise. |  [optional]
**creatorId** | **String** | The unique identifier of the Square account that took the payment. |  [optional]
**device** | [**Device**](Device.md) | The device that took the payment. |  [optional]
**paymentUrl** | **String** | The URL of the payment&#39;s detail page in the merchant dashboard. The merchant must be signed in to the merchant dashboard to view this page. |  [optional]
**receiptUrl** | **String** | The URL of the receipt for the payment. Note that for split tender payments, this URL corresponds to the receipt for the first tender listed in the payment&#39;s tender field. Each Tender object has its own receipt_url field you can use to get the other receipts associated with a split tender payment. |  [optional]
**inclusiveTaxMoney** | [**V1Money**](V1Money.md) | The sum of all inclusive taxes associated with the payment. |  [optional]
**additiveTaxMoney** | [**V1Money**](V1Money.md) | The sum of all additive taxes associated with the payment. |  [optional]
**taxMoney** | [**V1Money**](V1Money.md) | The total of all taxes applied to the payment. This is always the sum of inclusive_tax_money and additive_tax_money. |  [optional]
**tipMoney** | [**V1Money**](V1Money.md) | The total of all tips applied to the payment. |  [optional]
**discountMoney** | [**V1Money**](V1Money.md) | The total of all discounts applied to the payment. |  [optional]
**totalCollectedMoney** | [**V1Money**](V1Money.md) | The total of all discounts applied to the payment. |  [optional]
**processingFeeMoney** | [**V1Money**](V1Money.md) | The total of all processing fees collected by Square for the payment. |  [optional]
**netTotalMoney** | [**V1Money**](V1Money.md) | The amount to be deposited into the merchant&#39;s bank account for the payment. |  [optional]
**refundedMoney** | [**V1Money**](V1Money.md) | The total of all refunds applied to the payment. |  [optional]
**swedishRoundingMoney** | [**V1Money**](V1Money.md) | The total of all sales, including any applicable taxes, rounded to the smallest legal unit of currency (e.g., the nearest penny in USD, the nearest nickel in CAD) |  [optional]
**grossSalesMoney** | [**V1Money**](V1Money.md) | The total of all sales, including any applicable taxes. |  [optional]
**netSalesMoney** | [**V1Money**](V1Money.md) | The total of all sales, minus any applicable taxes. |  [optional]
**inclusiveTax** | [**List&lt;V1PaymentTax&gt;**](V1PaymentTax.md) | All of the inclusive taxes associated with the payment. |  [optional]
**additiveTax** | [**List&lt;V1PaymentTax&gt;**](V1PaymentTax.md) | All of the additive taxes associated with the payment. |  [optional]
**tender** | [**List&lt;V1Tender&gt;**](V1Tender.md) | All of the tenders associated with the payment. |  [optional]
**refunds** | [**List&lt;V1Refund&gt;**](V1Refund.md) | All of the refunds applied to the payment. Note that the value of all refunds on a payment can exceed the value of all tenders if a merchant chooses to refund money to a tender after previously accepting returned goods as part of an exchange. |  [optional]
**itemizations** | [**List&lt;V1PaymentItemization&gt;**](V1PaymentItemization.md) | The items purchased in the payment. |  [optional]
**surchargeMoney** | [**V1Money**](V1Money.md) | The total of all surcharges applied to the payment. |  [optional]
**surcharges** | [**List&lt;V1PaymentSurcharge&gt;**](V1PaymentSurcharge.md) | A list of all surcharges associated with the payment. |  [optional]
**isPartial** | **Boolean** | Indicates whether or not the payment is only partially paid for. If true, this payment will have the tenders collected so far, but the itemizations will be empty until the payment is completed. |  [optional]



