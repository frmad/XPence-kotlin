package sdu.mobile.xpence.ui.components.groupdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import sdu.mobile.xpence.formatDecimal
import sdu.mobile.xpence.ui.screens.GroupDetailAddPayment
import sdu.mobile.xpence.ui.utils.Group
import sdu.mobile.xpence.ui.utils.GroupMember
import sdu.mobile.xpence.ui.utils.User
import kotlin.math.abs

@Composable
fun BottomBar(
    user: User,
    group: Group,
    members: Array<GroupMember>,
    onOpenAddExpenseModal: () -> Unit
) {
    val navigator = LocalNavigator.currentOrThrow

    // Lookup current user in group members
    var foundGroupMember: GroupMember? = null
    for (groupMember in members) {
        if (groupMember.username == user.username) {
            foundGroupMember = groupMember
        }
    }

    val expenseText: String
    var expenseButtonEnabled = false
    var balanceAmountCents = 0
    foundGroupMember?.let { member ->
        balanceAmountCents = member.balanceAmountCents
    }

    if (balanceAmountCents == 0) {
        expenseText = "All expenses paid."
    } else if (balanceAmountCents > 0) {
        expenseText = "You are owed ${(balanceAmountCents / 100.0).formatDecimal(2)} ${group.currency_code}."
    } else {
        expenseButtonEnabled = true
        expenseText =
            "You owe ${abs((balanceAmountCents / 100.0)).formatDecimal(2)} ${group.currency_code}. Pay your share!"
    }

    Divider(color = Color.Gray, thickness = 0.5.dp)
    Column(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        BottomButton(expenseButtonEnabled, {
            navigator.push(GroupDetailAddPayment(group, balanceAmountCents))
        }) {
            Text(
                text = expenseText,
            )
        }
        BottomButton(true, onOpenAddExpenseModal) {
            Text(
                text = "Add expense",
            )
            Icon(Icons.Filled.Add, contentDescription = "Add")
        }
    }
}