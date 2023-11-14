package sdu.mobile.xpence.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.skia.Color


@Composable
fun Card(groupTitle: String, amount: Int, currency: String) {
    ElevatedCard(
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .padding(
                start = 16.dp,
                top = 30.dp,
                end = 16.dp,
                bottom = 16.dp
            )
            .fillMaxWidth()
    ) {
        Text(
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            text = "$groupTitle",
            modifier = Modifier
                .padding(
                start = 5.dp,
                top = 5.dp,
                end = 5.dp,
                bottom = 5.dp
                ),
            textAlign = TextAlign.Center,
        )
        Row {
            Text(
                text = "$amount",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(5.dp),
                textAlign = TextAlign.Center,
            )
            Text(
                text = "$currency.",
                modifier = Modifier
                    .padding(3.dp),
                textAlign = TextAlign.Center,
            )

        }

    }
}
