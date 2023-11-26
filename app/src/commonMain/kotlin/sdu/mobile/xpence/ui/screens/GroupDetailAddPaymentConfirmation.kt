package sdu.mobile.xpence.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import sdu.mobile.xpence.formatDecimal
import sdu.mobile.xpence.ui.tabs.GroupTab
import sdu.mobile.xpence.ui.tabs.HomeTab
import sdu.mobile.xpence.ui.utils.Group
import sdu.mobile.xpence.ui.utils.TransactionType
import kotlin.math.abs

class GroupDetailAddPaymentConfirmation(
    val group: Group,
    val expenseAmountCents: Int,
    val transactionType: TransactionType,
    val onExit: (Tab) -> Unit
) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        Surface(
            modifier = Modifier.fillMaxHeight(0.3f).padding(vertical = 24.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(72.dp)
            ) {
                Text(
                    "${if (transactionType == TransactionType.DEPOSIT) "Payment" else "Withdrawal"} of ${
                        abs(
                            expenseAmountCents / 100.0
                        ).formatDecimal(2)
                    } ${group.currency_code} made!",
                    style = MaterialTheme.typography.headlineMedium
                )
                Row(
                    modifier = Modifier.padding(horizontal = 18.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                )
                {
                    Button(
                        modifier = Modifier.fillMaxWidth(0.4f), onClick = {
                            onExit(HomeTab)
                        }) {
                        Text("Home")
                    }
                    Button(
                        modifier = Modifier.fillMaxWidth(0.66f), onClick = {
                            onExit(GroupTab)
                        }) {
                        Text("My Groups")
                    }
                }
            }
        }
    }
}