package com.squareup.inapppaymentsdemo.util

import com.squareup.inapppaymentsdemo.services.Price
import java.text.NumberFormat
import java.util.Currency

private val formatter = NumberFormat.getCurrencyInstance().apply {
  maximumFractionDigits = 2
  minimumFractionDigits = 2
}

fun Price.format(): String {
  formatter.currency = Currency.getInstance(currency);
  return formatter.format(amount)
}
