package com.squareup.inapppaymentsdemo.services

import com.squareup.inapppaymentsdemo.util.Result
import com.squareup.inapppaymentsdemo.util.Result.Failure
import com.squareup.inapppaymentsdemo.util.Result.Success
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

/**
 * Helper for making api calls which returns a [Result] instead of simply throwing on error.
 */
suspend fun <T> apiCall(apiCall: suspend () -> T): Result<T> {
  return try {
    withContext(IO) {
      Success(apiCall())
    }
  } catch (e: Throwable) {
    e.printStackTrace()
    Failure(e)
  }
}