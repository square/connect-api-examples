<?php

/*******************************************************************************
 * @name: saveCheckoutId
 * @param:
 *   $currOrder - a string or object referencing the current order
 *   $checkoutId - string; the checkout ID returned by Square Checkout
 * @return: $success - boolean; success/failure of the save operation
 *
 * @desc:
 * Takes in an order reference or number from your shopping cart software and
 * a Square Checkout ID and adds the checkoutID to the order metadata
 ******************************************************************************/
function saveCheckoutId($currOrder, $checkoutId) {

  // add code to update the order metadata with the provided checkoutId

  return $success ;
}

/*******************************************************************************
 * @name: getCheckoutId
 * @param: $currOrder - a string or object referencing the current order
 * @return:
 *   $checkoutId - string; the checkout ID previously returned by Square
 *                 Checkout
 *
 * @desc:
 * Takes in an order reference or number from your shopping cart software and
 * returns the Square Checkout ID stored earlier
 ******************************************************************************/
function getCheckoutId($currOrder) {

  // add code to look up checkoutId from the stored order metadata

  return $checkoutId ;
}

/*******************************************************************************
 * @name: getOrderTotal
 * @param: $currOrder - a string or object referencing the current order
 * @return: $orderTotal - int; the order total including taxes and shipping
 *
 * @desc:
 * Takes in an order reference or number from your shopping cart software and
 * returns the total amount that should have been charged so the transaction
 * can be verified after payment processing
 ******************************************************************************/
function getOrderTotal($currOrder) {

    // add code to look up orderTotal from the stored order metadata

    return $orderTotal ;
}

/*******************************************************************************
 * @name: getSalesTax
 * @param: $currOrder - a string or object referencing the current order
 * @return: $salesTax - array;
 *   [0] = int; the total amount of tax to add to the order
 *   [1] - string; the rate applied (e.g., "9.75%")
 *
 * @desc:
 * Takes in an order reference or number from your shopping cart software and
 * returns the total amount that should be added for tax and the tax rate
 * applied to calculate the tax amount. This information should be added to
 * your order array as an additional line item
 ******************************************************************************/
function getSalesTax($currOrder) {

    // add code to look up or calculate the applicable sales tax for the order

    return $salesTax ;
}

/*******************************************************************************
 * @name: getShippingCost
 * @param: $currOrder - a string or object referencing the current order
 * @return: $shippingAmount - int; the total shipping cost
 *
 * @desc:
 * Takes in an order reference or number from your shopping cart software and
 * returns the total amount that should be added for shipping. This information
 * should be added to your order array as an additional line item
 ******************************************************************************/
function getShippingCost($currOrder) {

    // add code to look up or calculate the applicable sales tax for the order

    return $shippingAmount ;
}

/*******************************************************************************
 * @name: verifyTransaction
 * @param:
 *   $getResponse - the GET response from the Square Checkout API endpoint
 *   $apiResponse - the POST response from the Square Transactions API endpoint
 *   $savedCheckoutId - string; the previously stored checkout ID
 *   $savedOrderTotal - int; the previously stored order total
 * @return: success/failure of the save operation
 *
 * @desc:
 * Takes in endpoint responses and compares the stored Transaction data to the
 * GET data returned by the Square Checkout endpoint to verify the transaction
 * completed successfully and there was no man-in-the-middle interference
 ******************************************************************************/
function verifyTransaction(
  $getResponse,  $apiResponse,  $savedCheckoutId,  $savedOrderTotal) {

  // Grab the transation keys returned by Square Checkout
  $returnedCheckoutId = $getResponse["checkoutId"] ;
  $returnedOrderId = $getResponse["referenceId"] ;

  // HELPER FUNCTION: look up order metadata: Square Checkout checkoutId
  $savedCheckoutId = getCheckoutID($returnedOrderId) ;

  // HELPER FUNCTION: look up order metadata: order total
  $savedOrderTotal = getOrderTotal($returnedOrderId) ;

  // Initialize the verification variables
  $calculatedOrderTotal = 0 ; // the order total as calucluated by Checkout
  $cardCaptured = false ;     // indicates all cards were captured successfully
  $totalMatch = false ;       // stored and calculated totals match
  $checkoutIdMatch = false ;  // checkoutID stored with the order and retured
                              // with the transaction results match

  // The TransactionsApi returns an array that includes a list tender objects.
  // Calculate the total across all tender objects and confirm "CAPTURED" status
  // for all credit cards
  foreach ($apiResponse['transaction']['tenders'] as $tender) {

    // Add the calculated/tendered amount to the overall order total
    $calculatedOrderTotal += $tender['amount_money']['amount'] ;

    // If the tender type is a credit card, make sure it was captured
    // successfully. If it wasn't, we can stop here because we know the order
    // didn't complete properly
    if ($tender['type'] == "CARD") {
      $cardCaptured = ($tender['card_details']['status'] == "CAPTURED") ;
      if (!cardCaptured) { return false ; }
    }
  }

  // Verify the transaction results for checkoutID and order total
  $totalMatch = ($calculatedOrderTotal == $savedOrderTotal) ;
  $checkoutIdMatch = ($returnedCheckoutId == $savedCheckoutId) ;

  return ($totalMatch && $cardCaptured && $checkoutIdMatch) ;
}

/*******************************************************************************
 * @name: getOrderAsArray
 * @param: $currOrder - a string or object referencing the current order
 * @return: $orderArray - array; the order information packaged as an array
 *
 * @desc:
 * Takes in an order reference or number from your shopping cart software and
 * packages the order details as an array with the following form:
 *
 * array(
 *   "redirect_url" => "{{URL TO CONFIRMATION PAGE}}",
 *   "idempotency_key" => "{{UNIQUE STRING FOR THIS TRANSACTION}}",
 *   "order" => array(
 *     "reference_id" => "{{LOCAL ORDER ID}}",
 *     "line_items" => array(
 *
 *       // List each item in the order as an individual line item
 *       array(
 *         "name" => "{{ITEM NAME}}",
 *         "quantity" => "{{ITEM QUANTITY}}",
 *         "base_price_money" => array(
 *           "amount" => {{ITEM COST IN BASE MONETARY UNIT}},
 *           "currency" => "{{CURRENCY USED}}"
 *         )
 *       ),
 *
 *       // Taxes and shipping need to be calculated in advance and added as
 *       // independent line items each with a quantity of 1
 *       array(
 *         "name" => "Total Tax ({{TAX RATE}}%)",
 *         "quantity" => "1",
 *         "base_price_money" => array(
 *           "amount" => {{TOTAL TAXES IN BASE MONETARY UNIT}},
 *           "currency" => "{{CURRENCY USED}}"
 *         )
 *       ),
 *       array(
 *         "name" => "Shipping",
 *         "quantity" => "1",
 *         "base_price_money" => array(
 *           "amount" => {{TOTAL SHIPPING IN BASE MONETARY UNIT}},
 *           "currency" => "{{CURRENCY USED}}"
 *         )
 *       )
 *     )
 *   ),
 *   "ask_for_shipping_address" => {{TRUE or FALSE}},
 *   "merchant_support_email" => "{{SUPPORT EMAIL ADDRESS}}",
 *
 *   // Pre-populating customer data is optional, but recommended. If you don't
 *   // populate these fields, the customer will be forced to re-enter it on the
 *   // Square Checkout page
 *
 *   "pre_populate_buyer_email" => "{{CUSTOMER CONTACT INFORMATION: EMAIL}}",
 *   "pre_populate_shipping_address" => array(
 *     "address_line_1" => "{{SHIPPING ADDRESS - LINE 1}}",
 *     "address_line_2" => "{{SHIPPING ADDRESS - LINE 2}}",
 *     "locality" => "{{SHIPPING CITY/TOWNSHIP/ETC}}",
 *     "administrative_district_level_1" => "{{SHIPPING STATE/PROVINCE/ETC}}",
 *     "postal_code" => "{{SHIPPING POSTAL CODE}}",
 *     "first_name" => "{{CUSTOMER FIRST NAME}}",
 *     "last_name" => "{{CUSTOMER LAST NAME}}"
 *   )
 * )
 ******************************************************************************/
function getOrderAsArray($currOrder) {

  // add code to convert the cart object to a properly formatted array
  // that you can send to the Square Checkout endpoint.
  // If your cart software does not calculate the order total and taxes as
  // part of its workflow, you should also calculate and store that information
  // for verification later

  return $orderArray ;
}
?>
