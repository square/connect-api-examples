package com.squareup.inapppaymentsdemo.services

import com.squareup.inapppaymentsdemo.services.PaymentStatus.CANCELED
import com.squareup.inapppaymentsdemo.services.PaymentStatus.PAID
import com.squareup.inapppaymentsdemo.services.PaymentStatus.PENDING
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Models the price by wrapping [amount] and [currency].
 */
@JsonClass(generateAdapter = true)
data class Price(
  @Json(name = "amount") val amount: Long,
  @Json(name = "currency") val currency: String,
)

/**
 * Modules an upgrade, which consists of [sku], [name] and [price].
 */
@JsonClass(generateAdapter = true)
data class Upgrade(
  @Json(name = "sku") val sku: String,
  @Json(name = "name") val name: String,
  @Json(name = "price") val price: Price,
)

/**
 * There are three payment status states.
 *
 * * [PENDING] Payment has not yet been made.
 * * [PAID] Payment has been successfully made.
 * * [CANCELED] Payment has been canceled.
 */
enum class PaymentStatus {
  @Json(name = "PENDING")
  PENDING,

  @Json(name = "PAID")
  PAID,

  @Json(name = "CANCELED")
  CANCELED,
  ;
}

/**
 * Modules the payment link by wrapping the [url] and associated sku/[id].
 */
@JsonClass(generateAdapter = true)
data class UpgradeLink(
  @Json(name = "url") val url: String,
  @Json(name = "id") val id: String,
)
