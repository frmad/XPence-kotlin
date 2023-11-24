package sdu.mobile.xpence.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupCard(groupTitle: String, amount: Int, currencyCode: String, onClickStartSource: () -> Unit) {
    ElevatedCard(
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        onClick = { onClickStartSource() },
        modifier = Modifier
            .padding(
                start = 16.dp,
                top = 30.dp,
                end = 16.dp,
                bottom = 45.dp
            )
            .fillMaxWidth()
    ) {
        Text(
            style = MaterialTheme.typography.titleMedium,
            text = groupTitle,
            modifier = Modifier
                .padding(
                    start = 25.dp,
                    top = 25.dp,
                    end = 5.dp,
                    bottom = 5.dp
                )
        )
        Row(
            horizontalArrangement = Arrangement.Center
        )
        {
            Text(
                text = amount.toString(),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(
                        start = 25.dp,
                        top = 5.dp
                    )
            )
            Text(
                text = currencyCode,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(
                        start = 25.dp,
                        top = 5.dp
                    )
            )
        }
        Row {
            Text(
                text = "1",
                modifier = Modifier
                    .padding(
                        start = 25.dp
                    ),
                textAlign = TextAlign.Center,
            )
            Text(
                text = "2",
                modifier = Modifier
                    .padding(
                        start = 5.dp
                    ),
                textAlign = TextAlign.Center,
            )
            Text(
                text = "7",
                modifier = Modifier
                    .padding(
                        start = 5.dp
                    ),
                textAlign = TextAlign.Center,
            )
        }
    }
}
