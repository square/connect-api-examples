package com.squareup.inapppaymentsdemo.util

import com.squareup.inapppaymentsdemo.util.Result.Success

sealed class Result<out T> {

  data class Success<out T>(val value: T) : Result<T>()

  data class Failure(val error: Throwable) : Result<Nothing>()
}

fun <T> Result<T>.isSuccessful() = this is Success<T>

val <T> Result<T>.valueOrNull: T?
  get() = (this as? Success<T>)?.value

fun <T> T.ok() = Success(this)
