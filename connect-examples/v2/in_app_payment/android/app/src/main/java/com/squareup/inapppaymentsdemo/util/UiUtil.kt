package com.squareup.inapppaymentsdemo.util

import androidx.compose.runtime.Composable
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

suspend fun <T> preventUiFlash(block: suspend () -> T): T = coroutineScope {
  val additionalDelay = async { delay(500) }
  val result = block()
  additionalDelay.await()
  result
}

@Composable
fun <T> T?.runComposable(block: @Composable T.() -> Unit): @Composable (() -> Unit)? {
  if (this != null) {
    return { this.block() }
  } else {
    return null
  }
}

@Composable
fun runComposableIf(
  predicate: Boolean,
  block: @Composable () -> Unit
): @Composable (() -> Unit)? {
  if (predicate) {
    return { block() }
  } else {
    return null
  }
}

