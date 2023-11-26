package sdu.mobile.xpence.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupCard(groupTitle: String, description: String, onClickStartSource: () -> Unit) {
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
        Text(
            text = description,
            fontStyle = FontStyle.Italic,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(
                    start = 25.dp,
                    top = 5.dp,
                    bottom = 16.dp
                )
        )
    }
}
