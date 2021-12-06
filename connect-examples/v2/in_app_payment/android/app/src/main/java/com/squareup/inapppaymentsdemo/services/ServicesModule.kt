package com.squareup.inapppaymentsdemo.services

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * The [PORT] and [BASE_URL] are subject to change.
 */
private const val PORT = 3000
private const val BASE_URL = "http://192.168.50.34:$PORT/"

/**
 * Adds [InAppPaymentService] to the dagger graph.
 */
@Module
@InstallIn(SingletonComponent::class)
object ServicesModule {

  @Provides
  fun provideMoshi(): Moshi = Moshi.Builder()
    .build()

  @Provides
  fun provideRetrofit(
    moshi: Moshi,
  ): Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

  @Provides
  fun provideInAppPaymentService(
    retrofit: Retrofit
  ): InAppPaymentService = retrofit.create(InAppPaymentService::class.java)
}
