package sdu.mobile.xpence.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.material3.ExperimentalMaterial3Api


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupCard(groupTitle: String, amount: Int, currencyCode: String, onClickStartSource: () -> Unit) {
    ElevatedCard(
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        onClick = {onClickStartSource()},
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
            fontSize = 20.dp,
            fontWeight = FontWeight.Bold,
            text = groupTitle,
            modifier = Modifier
                .padding(
                start = 25.dp,
                top = 25.dp,
                end = 5.dp,
                bottom = 5.dp
                ),
            textAlign = TextAlign.Center,
        )
        Row (
            horizontalArrangement = Arrangement.Center

        )
        {
            Text(
                text = amount.toString(),
                fontWeight = FontWeight.Bold,
                fontSize = 24.dp,
                modifier = Modifier
                    .padding(
                        start = 25.dp,
                        top = 5.dp
                        ),
                textAlign = TextAlign.Center,
            )
            Text(
                text = currencyCode,
                modifier = Modifier
                    .padding(
                        start = 25.dp,
                        top = 5.dp
                        ),
                textAlign = TextAlign.Center,
            )

        }
        
        Text(
            text = "You owe",
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier
                .padding(
                    start = 25.dp,
                    top = 10.dp
                    ),
            textAlign = TextAlign.Center,
        )

        Row() { 
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
                text = "3",
                modifier = Modifier
                    .padding(
                        start = 5.dp
                    ),
                textAlign = TextAlign.Center,
            )
         }

    }
}
