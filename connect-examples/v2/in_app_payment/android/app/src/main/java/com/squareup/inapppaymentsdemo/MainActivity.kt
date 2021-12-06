package com.squareup.inapppaymentsdemo

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.webkit.SslErrorHandler
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.squareup.inapppaymentsdemo.UiState.CheckoutPageState
import com.squareup.inapppaymentsdemo.UiState.Companion.INITIAL_UI_STATE
import com.squareup.inapppaymentsdemo.UiState.CreateUpgradeLinkState
import com.squareup.inapppaymentsdemo.UiState.ErrorState
import com.squareup.inapppaymentsdemo.UiState.PaidState
import com.squareup.inapppaymentsdemo.UiState.PayState
import com.squareup.inapppaymentsdemo.UiState.PaymentCanceledState
import com.squareup.inapppaymentsdemo.UiState.UpgradesState
import com.squareup.inapppaymentsdemo.services.GenerateUpgradeLinksRequest
import com.squareup.inapppaymentsdemo.services.InAppPaymentService
import com.squareup.inapppaymentsdemo.services.PaymentStatus
import com.squareup.inapppaymentsdemo.services.PaymentStatus.CANCELED
import com.squareup.inapppaymentsdemo.services.PaymentStatus.PAID
import com.squareup.inapppaymentsdemo.services.PaymentStatus.PENDING
import com.squareup.inapppaymentsdemo.services.Price
import com.squareup.inapppaymentsdemo.services.Upgrade
import com.squareup.inapppaymentsdemo.services.UpgradeLink
import com.squareup.inapppaymentsdemo.services.apiCall
import com.squareup.inapppaymentsdemo.ui.theme.InAppPaymentsDemoTheme
import com.squareup.inapppaymentsdemo.util.Result
import com.squareup.inapppaymentsdemo.util.format
import com.squareup.inapppaymentsdemo.util.ok
import com.squareup.inapppaymentsdemo.util.runComposableIf
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Disclaimer: most of the code here is for demo purpose, and is not/may not be part of best practices.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      InAppPaymentsDemoTheme {
        Surface(color = MaterialTheme.colors.background) {
          App()
        }
      }
    }
  }
}

sealed class UiState {
  data class UpgradesState(
    val upgrades: Result<List<Upgrade>>?,
  ) : UiState()

  data class PayState(val upgrade: Upgrade) : UiState()

  data class CreateUpgradeLinkState(val upgrade: Upgrade) : UiState()

  data class CheckoutPageState(
    val upgrade: Upgrade,
    val upgradeLink: UpgradeLink,
  ) : UiState()

  data class PaidState(val upgrade: Upgrade) : UiState()

  data class PaymentCanceledState(val upgrade: Upgrade) : UiState()

  data class ErrorState(val errorMessage: String) : UiState()

  companion object {
    val INITIAL_UI_STATE = UpgradesState(upgrades = null)
  }
}

@Composable
fun App(mainViewModel: MainViewModel = viewModel()) {
  val uiState by mainViewModel.uiState.observeAsState(INITIAL_UI_STATE)
  val backStackState by mainViewModel.backStackState.observeAsState(emptyList())

  BackHandler(enabled = backStackState.isNotEmpty()) {
    mainViewModel.goToPrevState()
  }

  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          // This is to get the smart cast to work as expected.
          val state = uiState
          val title = when (state) {
            is CheckoutPageState -> "Checkout"
            is CreateUpgradeLinkState -> state.upgrade.name
            is ErrorState -> "Error"
            is PaidState -> "Paid"
            is PayState -> state.upgrade.name
            is PaymentCanceledState -> "Payment Canceled"
            is UpgradesState -> "SOC In-app Payment Demo"
          }
          Text(
            text = title,
          )
        },
        navigationIcon = runComposableIf(backStackState.isNotEmpty()) {
          BackButton {
            mainViewModel.goToPrevState()
          }
        }
      )
    },
  ) {
    Box(
      modifier = Modifier.padding(it)
    ) {
      Crossfade(targetState = uiState) { state ->
        when (state) {
          is PayState -> PayScreen(
            name = state.upgrade.name,
            price = state.upgrade.price,
            isFetchingUpgradeLink = false,
            onClickPay = {
              mainViewModel.goToState(CreateUpgradeLinkState(state.upgrade))
            },
          )
          is CreateUpgradeLinkState -> {
            val upgrade = state.upgrade
            LaunchedEffect(Unit) {
              when (val result = mainViewModel.generateUpgradeLink(upgrade)) {
                is Result.Success -> {
                  Log.d("MainActivity", "url: ${result.value.url}")
                  mainViewModel.goToState(
                    CheckoutPageState(
                      upgrade = upgrade,
                      upgradeLink = result.value,
                    ),
                    prevState = PayState(upgrade),
                  )
                }
                is Result.Failure -> {
                  result.error.printStackTrace()
                  error("create link api Error")
                }
              }
            }

            PayScreen(
              name = state.upgrade.name,
              price = state.upgrade.price,
              isFetchingUpgradeLink = true,
            )
          }
          is UpgradesState -> UpgradesListScreen(
            upgrades = state.upgrades,
            onClickUpgrade = { upgrade ->
              mainViewModel.goToState(PayState(upgrade))
            },
          )
          is CheckoutPageState -> CheckoutPageScreen(
            upgradeLink = state.upgradeLink,
            onPaymentStatusChanged = { paymentStatus ->
              when (paymentStatus) {
                PAID -> {
                  mainViewModel.goToState(
                    state = PaidState(state.upgrade),
                    prevState = INITIAL_UI_STATE
                  )
                }
                PENDING -> {
                  // Do nothing.
                }
                CANCELED -> {
                  mainViewModel.goToState(
                    state = PaymentCanceledState(state.upgrade),
                    prevState = PayState(state.upgrade),
                  )
                }
              }
            },
            onPaymentStatusFetchError = {
              mainViewModel.goToState(
                ErrorState("Unable to fetch payment status"),
                PayState(state.upgrade)
              )
            },
          )
          is PaymentCanceledState -> Text("Payment canceled")
          is PaidState -> PaidScreen(
            name = state.upgrade.name,
            price = state.upgrade.price,
            onClickOk = {
              mainViewModel.goToState(state = INITIAL_UI_STATE, prevState = null)
            },
          )
          is ErrorState -> Text("Error: ${state.errorMessage}")
        }
      }
    }
  }
}

@Composable
fun BackButton(
  onClick: () -> Unit,
) {
  IconButton(
    onClick = onClick,
  ) {
    Icon(Icons.Default.ArrowBack, contentDescription = "back button")
  }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UpgradesListScreen(
  upgrades: Result<List<Upgrade>>?,
  onClickUpgrade: (Upgrade) -> Unit,
  mainViewModel: MainViewModel = viewModel(),
) {
  LaunchedEffect(key1 = "fetch-upgrades") { mainViewModel.getUpgrades() }
  when (upgrades) {
    is Result.Success -> {
      LazyColumn {
        itemsIndexed(upgrades.value) { index, item ->
          ListItem(
            modifier = Modifier.clickable {
              onClickUpgrade(upgrades.value[index])
            },
            trailing = {
              Text(
                text = item.price.format(),
                fontWeight = FontWeight.Bold,
              )
            },
          ) {
            Text(item.name)
          }
        }
      }
    }
    is Result.Failure -> {
      Column(
        modifier = Modifier
          .padding(16.dp)
      ) {
        Text(
          modifier = Modifier,
          text = "Api error :("
        )
      }
    }
    null -> {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(16.dp)
      ) {
        CircularProgressIndicator(
          modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(16.dp)
        )
      }
    }
  }
}

@Composable
fun PayScreen(
  name: String,
  price: Price,
  isFetchingUpgradeLink: Boolean = false,
  onClickPay: () -> Unit = {},
) {
  Column(
    modifier = Modifier.padding(16.dp),
  ) {
    Row {
      Text(
        modifier = Modifier.weight(1.0f),
        text = name,
      )
      Spacer(modifier = Modifier.width(16.dp))
      Text(price.format())
    }

    Spacer(modifier = Modifier.height(16.dp))

    if (isFetchingUpgradeLink) {
      CircularProgressIndicator(
        modifier = Modifier.size(32.dp),
      )
    } else {
      Button(
        onClick = onClickPay,
      ) {
        Text("Pay")
      }
    }
  }
}

@Composable
fun PaidScreen(
  name: String,
  price: Price,
  onClickOk: () -> Unit = {},
) {
  Column(
    modifier = Modifier.padding(16.dp),
  ) {
    Row {
      Text(
        "Payment successful!"
      )
      Spacer(modifier = Modifier.width(8.dp))
      Icon(Icons.Default.Check, "payment success")
    }

    Spacer(modifier = Modifier.height(16.dp))

    Row {
      Text(
        modifier = Modifier.weight(1.0f),
        text = name,
      )
      Spacer(modifier = Modifier.width(16.dp))
      Text(price.format())
    }

    Spacer(modifier = Modifier.height(16.dp))

    Button(
      onClick = onClickOk,
    ) {
      Text("Ok")
    }
  }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun CheckoutPageScreen(
  upgradeLink: UpgradeLink,
  onPaymentStatusChanged: suspend (PaymentStatus) -> Unit,
  onPaymentStatusFetchError: (Throwable) -> Unit,
  mainViewModel: MainViewModel = viewModel(),
) {
  LaunchedEffect(upgradeLink.url) {
    mainViewModel.paymentStatus(upgradeLink.id).collect { result ->
      when (result) {
        is Result.Success -> {
          onPaymentStatusChanged(result.value)
        }
        is Result.Failure -> {
          onPaymentStatusFetchError(result.error)
        }
      }
    }
  }

  val pageLoading = remember { mutableStateOf(true) }

  Column {
    if (pageLoading.value) {
      LinearProgressIndicator(
        modifier = Modifier.fillMaxWidth(),
      )
    }

    AndroidView(
      modifier = Modifier.weight(1.0f),
      factory = {
        WebView.setWebContentsDebuggingEnabled(true)
        WebView(it).apply {
          layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
          )
          settings.javaScriptEnabled = true
          settings.loadWithOverviewMode = true
          settings.useWideViewPort = true
          settings.domStorageEnabled = true
          webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
              super.onPageStarted(view, url, favicon)
              pageLoading.value = true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
              super.onPageFinished(view, url)
              pageLoading.value = false
            }

            // Ignore SSL fun-stuff for POC.
            override fun onReceivedSslError(
              view: WebView?,
              handler: SslErrorHandler?,
              error: SslError?
            ) {
              handler?.proceed()
            }
          }
          loadUrl(upgradeLink.url)
        }
      },
    )
  }
}

@HiltViewModel
class MainViewModel @Inject constructor(
  private val service: InAppPaymentService,
) : ViewModel() {

  /**
   * Super simple backstack emulation.
   */
  private val _backStack = MutableLiveData<List<UiState>>(emptyList())
  val backStackState: LiveData<List<UiState>> = _backStack

  private val _state = MutableLiveData<UiState>(INITIAL_UI_STATE)
  var uiState: LiveData<UiState> = _state

  fun getUpgrades() {
    viewModelScope.launch {
      _state.value = UpgradesState(apiCall { service.getUpgrades().upgrades })
    }
  }

  suspend fun generateUpgradeLink(
    upgrade: Upgrade,
  ): Result<UpgradeLink> {
    return apiCall { service.generateUpgradeLinks(GenerateUpgradeLinksRequest(upgrade.sku)) }
  }

  fun goToPrevState() {
    val list = backStackState.value!!
    val prevState = list.last()
    _backStack.value = list.subList(0, list.size - 1)
    _state.value = prevState
  }

  fun goToState(state: UiState, prevState: UiState? = null) {
    val list = mutableListOf<UiState>()
    list += _backStack.value!!
    list += prevState ?: _state.value!!
    _backStack.value = list
    _state.value = state
  }

  /**
   * Using [Flow] to poll and emit [PaymentStatus].
   */
  fun paymentStatus(id: String): Flow<Result<PaymentStatus>> = flow {
    while (true) {
      val result = apiCall { service.checkStatus(id) }
      delay(5000)

      when (result) {
        is Result.Success -> {
          emit(result.value.status.ok())
          if (result.value.status != PENDING) {
            break
          }
        }
        is Result.Failure -> {
          result.error.printStackTrace()
          emit(result)
          break
        }
      }
    }
  }
}
