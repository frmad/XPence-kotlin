package sdu.mobile.xpence.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import kotlinx.coroutines.launch
import sdu.mobile.xpence.formatDecimal
import sdu.mobile.xpence.ui.components.addexpenseform.CurrencyInput
import sdu.mobile.xpence.ui.components.addexpenseform.ExpenseParticipantItem
import sdu.mobile.xpence.ui.utils.*
import kotlin.math.abs

@Stable
class ExpenseParticipantData(
    fullName: String,
    enabled: Boolean,
    expenseAmountCents: Int
) {
    var fullName by mutableStateOf(fullName)
    var enabled by mutableStateOf(enabled)
    var expenseAmountCents by mutableStateOf(expenseAmountCents)
}

@Composable
fun rememberExpenseParticipantsMap(
    expenseParticipantsMap: Map<String, ExpenseParticipantData>
): Map<String, ExpenseParticipantData> {
    return rememberSaveable {
        expenseParticipantsMap
    }
}

fun distributeEqually(number: Int, buckets: Int): List<Int> {
    require(number >= 0) { "Number must be non-negative" }
    require(buckets > 0) { "Number of buckets must be greater than 0" }

    val quotient = number / buckets
    val remainder = number % buckets

    // Create a list with each bucket receiving the quotient
    var result = MutableList(buckets) { quotient }

    // Distribute the remainder among the first 'remainder' buckets
    for (i in 0 until remainder) {
        result[i]++
    }

    return result
}

@Stable
fun getEvenlySplitExpenses(
    expenseAmount: Int,
    expenseParticipantMap: Map<String, ExpenseParticipantData>
): Map<String, ExpenseParticipantData> {
    var numActiveParticipants = 0

    for (participant in expenseParticipantMap.entries) {
        if (participant.value.enabled)
            numActiveParticipants++
    }

    val newMap = expenseParticipantMap.toMap()
    var distrubtionIndex = 0
    val distribution = distributeEqually(expenseAmount, numActiveParticipants)

    for (participant in expenseParticipantMap.entries) {
        if (participant.value.enabled) {
            newMap[participant.key]!!.expenseAmountCents = distribution[distrubtionIndex]
            distrubtionIndex++
        } else
            newMap[participant.key]!!.expenseAmountCents = 0
    }

    return newMap
}

@Stable
fun getExpenseBalance(
    expenseAmount: Int,
    expenseParticipantMap: Map<String, ExpenseParticipantData>
): Double {
    var expenseParticipantSum = 0
    for (participant in expenseParticipantMap.entries) {
        if (participant.value.enabled) {
            expenseParticipantSum += participant.value.expenseAmountCents
        }
    }

    return ((expenseAmount.toDouble()) - (expenseParticipantSum)) / 100.0
}

@Stable
fun getExpenseBalanceMessage(
    currency: String,
    expenseAmount: Int,
    expenseParticipantMap: Map<String, ExpenseParticipantData>
): String {
    val balance = getExpenseBalance(expenseAmount, expenseParticipantMap)
    val balanceString = abs(balance).formatDecimal(2)

    if (balance == 0.0) {
        return "All expenses have been assigned"
    }

    if (balance > 0) {
        return "Still need to assign $balanceString $currency"
    }

    if (balance < 0) {
        return "You have over assigned expenses by $balanceString $currency"
    }

    return ""
}

@Stable
fun isDataValid(
    expenseTitle: String,
    expenseDescription: String,
    expenseAmount: Int,
    splitExpensesEvenly: Boolean,
    expenseParticipants: Map<String, ExpenseParticipantData>
): Boolean {
    if (expenseTitle.isEmpty())
        return false

    if (expenseDescription.isEmpty())
        return false

    if (expenseAmount == 0)
        return false

    if (!splitExpensesEvenly && getExpenseBalance(expenseAmount, expenseParticipants) != 0.0)
        return false

    for (participant in expenseParticipants) {
        if (participant.value.enabled)
            return true
    }

    return false
}

class GroupDetailAddExpense(
    val currentUser: User,
    val group: Group,
    val members: Array<GroupMember>,
    val onSubmitExpense: () -> Unit
) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        var expenseTitle by rememberSaveable { mutableStateOf("") }
        var expenseDescription by rememberSaveable { mutableStateOf("") }
        var expenseAmount by rememberSaveable { mutableStateOf(0) }
        var splitExpensesEvenly by rememberSaveable { mutableStateOf(true) }
        val coroutineScope = rememberCoroutineScope()
        val currency = group.currency_code
        var expenseParticipants = rememberExpenseParticipantsMap(
            members.associate {
                it.username to ExpenseParticipantData(it.username, true, 0)
            }
        )

        val bottomSheetNavigator = LocalBottomSheetNavigator.current

        key(expenseParticipants) {
            Surface(
                modifier = Modifier.fillMaxHeight(0.8f)
            ) {
                Scaffold(
                    topBar = {
                        Column {
                            TopAppBar(title = { Text(text = "Add new expense") })
                            Divider(color = Color.Gray, thickness = 0.5.dp)
                        }
                    },
                    bottomBar = {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Divider(color = Color.Gray, thickness = 0.5.dp)
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Button(
                                    modifier = Modifier.fillMaxWidth(0.33f),
                                    onClick = { bottomSheetNavigator.hide() }
                                ) {
                                    Text(text = "Cancel")
                                }
                                Spacer(
                                    modifier = Modifier.fillMaxWidth(0.5f)
                                )
                                Button(
                                    modifier = Modifier.fillMaxWidth(),
                                    enabled = isDataValid(
                                        expenseTitle,
                                        expenseDescription,
                                        expenseAmount,
                                        splitExpensesEvenly,
                                        expenseParticipants
                                    ),
                                    onClick = {
                                        coroutineScope.launch {
                                            val client = getHttpClient(authenticationState)
                                            if (client != null) {
                                                createExpense(
                                                    client, group.id, PostExpense(
                                                        name = expenseTitle,
                                                        description = expenseDescription,
                                                        amountInCents = expenseAmount,
                                                        payerUsername = currentUser.username,
                                                        members = expenseParticipants.map { entry ->
                                                            PostExpenseMember(entry.key, entry.value.expenseAmountCents)

                                                        }
                                                    )
                                                )
                                                bottomSheetNavigator.hide()
                                                onSubmitExpense()
                                            }
                                        }
                                    }
                                ) {
                                    Text(text = "Confirm")
                                }
                            }
                        }
                    }
                ) {
                    Column(
                        modifier = Modifier
                            .padding(it)
                            .fillMaxSize()
                            .padding(16.dp, 16.dp)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Title",
                            style = MaterialTheme.typography.bodyLarge
                        )

                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = expenseTitle,
                            onValueChange = { expenseTitle = it },
                            maxLines = 1,
                            placeholder = {
                                Text(
                                    text = "e.g. Movie night",
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            },
                        )

                        Spacer(modifier = Modifier.padding(8.dp))

                        Text(
                            text = "Description",
                            style = MaterialTheme.typography.bodyLarge
                        )

                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = expenseDescription,
                            onValueChange = { expenseDescription = it },
                            maxLines = 3,
                            placeholder = {
                                Text(
                                    text = "e.g. Snacks and drinks for the movie night",
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            },
                        )

                        Spacer(modifier = Modifier.padding(8.dp))

                        Text(
                            text = "Amount",
                            style = MaterialTheme.typography.bodyLarge
                        )

                        CurrencyInput(
                            currency,
                            expenseAmount
                        ) { amount -> expenseAmount = amount }


                        Spacer(modifier = Modifier.padding(8.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)

                        ) {
                            Text(
                                text = "Split expenses evenly:",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Switch(
                                checked = splitExpensesEvenly,
                                onCheckedChange = {
                                    splitExpensesEvenly = it
                                }
                            )
                        }

                        Spacer(modifier = Modifier.padding(6.dp))

                        Text(
                            text = "Expense participants:",
                            style = MaterialTheme.typography.bodyLarge
                        )

                        if (!splitExpensesEvenly) {
                            Text(
                                text = "*${
                                    getExpenseBalanceMessage(
                                        currency,
                                        expenseAmount,
                                        expenseParticipants
                                    )
                                }",
                                style = MaterialTheme.typography.bodyMedium,
                                fontStyle = FontStyle.Italic
                            )
                        }

                        Column(
                            modifier = Modifier.fillMaxWidth().border(
                                0.5.dp, Color.Gray
                            ),
                        ) {
                            (if (splitExpensesEvenly) getEvenlySplitExpenses(
                                expenseAmount,
                                expenseParticipants
                            ) else expenseParticipants).map { entry ->
                                ExpenseParticipantItem(
                                    currency,
                                    entry.value,
                                    disableCurrencyInput = splitExpensesEvenly || !entry.value.enabled
                                ) { newItem ->
                                    expenseParticipants =
                                        expenseParticipants + (entry.key to newItem)
                                }
                                Divider(color = Color.Gray, thickness = 0.5.dp)
                            }
                        }
                    }
                }
            }
        }
    }
}


