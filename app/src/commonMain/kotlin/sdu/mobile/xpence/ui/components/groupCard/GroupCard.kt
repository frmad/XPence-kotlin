package sdu.mobile.xpence.ui.components.groupCard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import sdu.mobile.xpence.ui.utils.Group


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupCard(group: Group, amount: Int, currencyCode: String, onClickStartSource: () -> Unit) {
    ElevatedCard(
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        onClick = { onClickStartSource() },
        modifier = Modifier
            .padding(
                start = 16.dp,
                top = 20.dp,
                end = 16.dp,
                bottom = 45.dp
            )
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(5.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            GroupName(group)
            GroupDescription(group)
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = amount.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(start = 10.dp, end = 8.dp)
                )
                Text(
                    text = currencyCode,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupCardWithoutExpenses(group: Group, onClickStartSource: () -> Unit) {
    ElevatedCard(
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        onClick = { onClickStartSource() },
        modifier = Modifier
            .padding(
                start = 16.dp,
                top = 20.dp,
                end = 16.dp,
                bottom = 45.dp
            )
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(5.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            GroupName(group)
            GroupDescription(group)
        }
    }
}



