package sdu.mobile.xpence.ui.components.groupdetail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import sdu.mobile.xpence.formatDecimal


@Composable
fun MessageBubble(
    title: String,
    description: String,
    amount: Int,
    sender: String,
    isMyMessage: Boolean = true,
    currency: String
) {
    Row(
        Modifier.fillMaxWidth().padding(horizontal = 10.dp),
        horizontalArrangement = (if (isMyMessage) Arrangement.End else Arrangement.Start)
    ) {
        Column(horizontalAlignment = (if (isMyMessage) Alignment.End else Alignment.Start)) {
            Text(
                sender,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
            )
            Surface(
                modifier = Modifier.fillMaxWidth(0.7f),
                shape = RoundedCornerShape(20),
                color = if (isMyMessage) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.secondary,
                contentColor = if (isMyMessage) MaterialTheme.colorScheme.onTertiary else MaterialTheme.colorScheme.onSecondary,
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(
                            "${title}: ",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            "${(amount / 100.0).formatDecimal(2)} $currency",
                            style = MaterialTheme.typography.bodyLarge,
                            textDecoration = TextDecoration.Underline,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(
                        description,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}