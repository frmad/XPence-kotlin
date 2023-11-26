package sdu.mobile.xpence.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import sdu.mobile.xpence.ui.components.groupdetail.BottomBar
import sdu.mobile.xpence.ui.components.groupdetail.MessageBubble
import sdu.mobile.xpence.ui.components.groupdetail.TopBar
import sdu.mobile.xpence.ui.utils.*

class GroupDetail(private val group: Group) : Screen {

    data class MessageData(
        val sender: String,
        val title: String,
        val description: String,
        val expenseAmount: Int
    )

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
    @Composable
    override fun Content() {
        val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
        var refresh by rememberSaveable { mutableStateOf(false) }
        key(refresh) {
            val bottomSheetNavigator = LocalBottomSheetNavigator.current
            val result by usingAPI { client ->
                val expenses = getExpenses(client, group.id)
                val members = getGroupMembers(client, group.id)
                val currentUser = getCurrentUser(client)
                Triple(currentUser, expenses, members)
            }
            val (currentUser, expenses, members) = (result as? QueryState.Success)?.data ?: Triple(
                User("loading..."),
                arrayOf(),
                arrayOf()
            )


            Scaffold(
                modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    TopBar(
                        group,
                        members
                    )
                },
                bottomBar = {
                    BottomBar(
                        currentUser,
                        group,
                        members,
                        onOpenShowStatus = {
                            bottomSheetNavigator.show(
                                GroupDetailShowStatus(currentUser, group, members)
                            )
                        },
                        onOpenAddExpenseModal = {
                            bottomSheetNavigator.show(
                                GroupDetailAddExpense(
                                    currentUser,
                                    group,
                                    members,
                                    onSubmitExpense = {
                                        refresh = !refresh
                                    }
                                )
                            )
                        }
                    )
                }
            ) { paddingValues ->
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                        .padding(paddingValues),
                    verticalArrangement = Arrangement.Top,
                    reverseLayout = true
                ) {
                    when (val res = result) {
                        is QueryState.Success -> items(expenses.reversed()) { item ->
                            Spacer(Modifier.height(20.dp))
                            MessageBubble(
                                title = item.name,
                                description = item.description,
                                amount = item.amountInCents,
                                sender = item.payerUsername,
                                isMyMessage = currentUser.username == item.payerUsername,
                                currency = group.currency_code
                            )
                        }

                        is QueryState.Error -> item { Text(text = res.message) }
                        is QueryState.Loading -> item { Text(text = "Loading") }
                        else -> {}
                    }
                }
            }
        }
    }
}