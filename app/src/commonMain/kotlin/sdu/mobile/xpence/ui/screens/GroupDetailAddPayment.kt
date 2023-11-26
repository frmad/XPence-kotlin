package sdu.mobile.xpence.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import sdu.mobile.xpence.formatDecimal
import sdu.mobile.xpence.isIphoneWeb
import sdu.mobile.xpence.ui.components.addpayment.SlideToUnlock
import sdu.mobile.xpence.ui.utils.*
import kotlin.math.abs

class GroupDetailAddPayment(
    val group: Group,
    val expenseAmountCents: Int,
    val transactionType: TransactionType
) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        var isLoading by remember { mutableStateOf(false) }
        val coroutineScope = rememberCoroutineScope()
        val navigator = LocalNavigator.currentOrThrow
        val bottomSheetNavigator = LocalBottomSheetNavigator.current

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        IconButton(
                            onClick = { navigator.pop() }, colors = IconButtonDefaults.iconButtonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.ArrowBack,
                                contentDescription = ""
                            )
                        }
                    },
                    title = {}
                )
            },
            bottomBar = {
                Row(
                    modifier = Modifier.padding(8.dp)
                ) {
                    if (isIphoneWeb()) {
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                bottomSheetNavigator.show(
                                    GroupDetailAddPaymentConfirmation(
                                        group,
                                        expenseAmountCents,
                                        transactionType
                                    ) {
                                        bottomSheetNavigator.hide()
                                        navigator.popUntilRoot()
                                        targetNavigationTab.value = it
                                    })
                            }
                        ) {
                            Text(if (transactionType == TransactionType.DEPOSIT) "Click here to make payment" else "Click here to make withdrawal")
                        }
                    } else {
                        SlideToUnlock(
                            text = if (transactionType == TransactionType.DEPOSIT) "Swipe to make payment" else "Swipe to make withdrawal",
                            isLoading = isLoading,
                            onUnlockRequested = {
                                coroutineScope.launch {
                                    isLoading = true
                                    delay(1000)
                                    val client = getHttpClient(authenticationState)
                                    client?.let {
                                        createTransaction(it, group.id, expenseAmountCents, transactionType)
                                        bottomSheetNavigator.show(
                                            GroupDetailAddPaymentConfirmation(
                                                group,
                                                expenseAmountCents,
                                                transactionType
                                            ) {
                                                bottomSheetNavigator.hide()
                                                navigator.popUntilRoot()
                                                targetNavigationTab.value = it
                                            })
                                    }
                                    isLoading = false
                                }
                            },
                        )
                    }

                }
            }
        ) {
            Column(
                modifier = Modifier.padding(it).fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(36.dp, Alignment.CenterVertically)
            ) {
                Text(
                    group.name,
                    style = MaterialTheme.typography.displayMedium,
                )
                Text(
                    "",
                    style = MaterialTheme.typography.headlineMedium,
                )
                Box(
                    modifier = Modifier.background(MaterialTheme.colorScheme.primary)
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                ) {
                    Text(
                        "You ${if (transactionType == TransactionType.DEPOSIT) "owe" else "are withdrawing"}: ${
                            abs(
                                expenseAmountCents / 100.0
                            ).formatDecimal(2)
                        } ${group.currency_code}",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

        }
    }
}