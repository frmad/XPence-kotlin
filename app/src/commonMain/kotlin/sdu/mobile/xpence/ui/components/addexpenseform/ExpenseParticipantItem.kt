package sdu.mobile.xpence.ui.components.addexpenseform

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import sdu.mobile.xpence.ui.screens.ExpenseParticipantData

@Composable
fun ExpenseParticipantItem(
    currency: String,
    item: ExpenseParticipantData,
    disableCurrencyInput: Boolean,
    setItem: (ExpenseParticipantData) -> Unit,
) {
    ListItem(
        headlineContent = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    Icons.Filled.Face,
                    contentDescription = "Profile picture",
                )
                Text(item.fullName)
            }
        },
        trailingContent = {
            SmallCurrencyInput(
                currency,
                disabled = disableCurrencyInput,
                if (item.expenseAmountCents == 0) "" else item.expenseAmountCents.toString()
            ) { value ->
                if (value.trim() == "") {
                    item.expenseAmountCents = 0
                } else {
                    item.expenseAmountCents = value.toInt()
                }
                setItem(item)
            }
        },
        leadingContent = {
            Checkbox(
                checked = item.enabled,
                onCheckedChange = {
                    item.enabled = it
                    setItem(item)
                }
            )
        }
    )
}