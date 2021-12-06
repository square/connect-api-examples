package com.squareup.inapppaymentsdemo.services

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * This service is responsible for handling in-app payments.
 */
interface InAppPaymentService {

  /**
   * Returns a list of available [Upgrade].
   */
  @GET("/get-upgrades")
  suspend fun getUpgrades(): GetUpgradesResponse

  /**
   * Generates a link for a given sku.
   */
  @POST("/generate-upgrade-link")
  suspend fun generateUpgradeLinks(
    @Body request: GenerateUpgradeLinksRequest
  ): UpgradeLink

  /**
   * Check the payment status for a given [id] (which is a sku).
   */
  @GET("/check-status/{id}")
  suspend fun checkStatus(@Path("id") id: String): CheckStatusResponse
}

// Below are helper data class for request and response JSON objects.

@JsonClass(generateAdapter = true)
data class GetUpgradesResponse(
  @Json(name = "upgrades") val upgrades: List<Upgrade>,
)

@JsonClass(generateAdapter = true)
data class CheckStatusResponse(
  @Json(name = "status") val status: PaymentStatus
)

@JsonClass(generateAdapter = true)
data class GenerateUpgradeLinksRequest(
  @Json(name = "sku") val sku: String
)
