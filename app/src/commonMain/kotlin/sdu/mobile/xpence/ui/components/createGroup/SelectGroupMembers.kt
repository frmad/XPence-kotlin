package sdu.mobile.xpence.ui.components.createGroup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun selectGroupMembers(groupMembers: List<String>) {
    val selectedItems = remember { mutableStateListOf<String>() }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        groupMembers.forEach { member ->
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
                            selectedItems.add(member)
                        } else {
                            selectedItems.remove(member)
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
