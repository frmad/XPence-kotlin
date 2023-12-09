package sdu.mobile.xpence.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import kotlinx.coroutines.launch
import sdu.mobile.xpence.formatDecimal
import sdu.mobile.xpence.ui.utils.*
import kotlin.math.abs
import kotlin.math.min

enum class ShowStatusState {
    CANNOT_WITHDRAW,
    COULD_WITHDRAW_BUT_NO_BALANCE,
    CAN_WITHDRAW
}

class GroupDetailShowStatus(
    val user: User,
    val group: Group,
    val members: Array<GroupMember>,
    val onPressWithdraw: (Int) -> Unit
) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val bottomSheetNavigator = LocalBottomSheetNavigator.current
        val coroutineScope = rememberCoroutineScope()
        val result by usingAPI { client ->
            getGroupBalance(client, group.id)
        }

        var myBalanceCents: Int = 0
        // Find user in members
        for (member in members) {
            if (member.username == user.username) {
                myBalanceCents = member.balanceAmountCents
            }
        }

        var status = ShowStatusState.CANNOT_WITHDRAW
        if (myBalanceCents > 0 && result is QueryState.Success) {
            val groupBalance = (result as QueryState.Success<Balance>).data.balanceAmountCents
            status = if (groupBalance > 0) {
                ShowStatusState.CAN_WITHDRAW
            } else {
                ShowStatusState.COULD_WITHDRAW_BUT_NO_BALANCE
            }
        }

        Scaffold(
            modifier = Modifier.fillMaxHeight(0.6f),
            topBar = {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text("Payment Status:", style = MaterialTheme.typography.headlineLarge)
                    }
                    Divider(color = Color.Gray, thickness = 0.5.dp)
                }
            },
            bottomBar = {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Divider(color = Color.Gray, thickness = 0.5.dp)
                    Row(
                        modifier = Modifier.padding(horizontal = 18.dp, vertical = 8.dp).fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    )
                    {
                        Button(
                            modifier = Modifier.fillMaxWidth(0.4f), onClick = {
                                bottomSheetNavigator.hide()
                            }) {
                            Text("Go back")
                        }
                        if (status != ShowStatusState.CANNOT_WITHDRAW) {
                            val groupBalance = (result as QueryState.Success<Balance>).data.balanceAmountCents
                            val amount = min(
                                groupBalance,
                                myBalanceCents
                            )
                            Button(
                                modifier = Modifier.fillMaxWidth(0.66f), onClick = {
                                    onPressWithdraw(amount)
                                },
                                enabled = status == ShowStatusState.CAN_WITHDRAW
                            ) {
                                Text(
                                    if (status == ShowStatusState.CAN_WITHDRAW) "Withdraw ${
                                        abs(amount / 100.0).formatDecimal(2)
                                    } ${group.currency_code}" else "Group balance is zero*",
                                    fontStyle = if (status == ShowStatusState.COULD_WITHDRAW_BUT_NO_BALANCE) FontStyle.Italic else FontStyle.Normal
                                )
                            }
                        }
                    }
                }
            }
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(it),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
            ) {
                items(members) { item ->
                    Row(
                        modifier = Modifier.fillMaxWidth(0.8f),
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(item.username)
                        Spacer(
                            Modifier
                                .height(2.dp)
                                .width(24.dp)
                                .background(MaterialTheme.colorScheme.onBackground, shape = DottedShape(step = 4.dp))
                                .weight(1.0f)
                        )
                        Text(
                            "${if (item.balanceAmountCents > 0) "+" else if (item.balanceAmountCents < 0) "-" else ""}${
                                abs(
                                    item.balanceAmountCents / 100.0
                                ).formatDecimal(2)
                            } ${group.currency_code}"
                        )
                        Spacer(
                            Modifier
                                .height(2.dp)
                                .width(4.dp)
                        )
                        if (item.balanceAmountCents < 0) {
                            OutlinedButton(
                                onClick = {
                                    coroutineScope.launch {
                                        sendMessage(item.username, "You owe", "A lot of money")
                                    }
                                },
                                modifier = Modifier.size(24.dp),
                                shape = CircleShape,
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                                contentPadding = PaddingValues(0.dp),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.primary)
                            ) {
                                Icon(Icons.Outlined.Notifications, contentDescription = "")
                            }
                        }
                    }
                }
                item {
                    Spacer(Modifier.height(36.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(0.8f),
                    ) {
                        when (val res = result) {
                            is QueryState.Success -> Text(
                                "Group Balance: ${(res.data.balanceAmountCents / 100.0).formatDecimal(2)} ${group.currency_code}",
                                fontWeight = FontWeight.Bold
                            )

                            is QueryState.Loading -> Text(
                                "Loading...",
                                fontWeight = FontWeight.Bold
                            )

                            is QueryState.Error -> Text(
                                "Error: ${res.message}",
                                fontWeight = FontWeight.Bold,
                                color = Color.Red
                            )

                            else -> {}
                        }

                        Spacer(Modifier.weight(1.0f))
                    }
                }

                if (status == ShowStatusState.COULD_WITHDRAW_BUT_NO_BALANCE) {
                    item {
                        Spacer(Modifier.height(36.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(0.8f),
                        ) {
                            Text(
                                "*Cannot make a withdrawal, since the group members have not paid their share.",
                                fontStyle = FontStyle.Italic
                            )
                        }
                    }
                }
            }
        }
    }
}