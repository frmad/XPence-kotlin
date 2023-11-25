package sdu.mobile.xpence.ui.components.createGroup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun selectGroupMembers(
    users: List<String>,
    selectedItems: List<String>,
    onSelectItems: (List<String>) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        users.forEach { member ->
            val isSelected = selectedItems.contains(member)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(if (isSelected) Color.Gray else Color.Transparent)
                    .padding(4.dp)
            ) {
                Checkbox(
                    checked = isSelected,
                    onCheckedChange = { isChecked ->
                        if (isChecked) {
                            onSelectItems(selectedItems + listOf(member))
                        } else {
                            onSelectItems(selectedItems.filter {
                                it != member
                            })
                        }
                    },
                    modifier = Modifier.padding(8.dp)
                )
                Text(
                    text = member,
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(1f)
                )
            }
        }
    }
}
