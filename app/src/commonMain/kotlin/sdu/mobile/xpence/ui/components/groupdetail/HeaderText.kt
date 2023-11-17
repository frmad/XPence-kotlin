package sdu.mobile.xpence.ui.components.groupdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun HeaderText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.onPrimary,
        modifier = Modifier.background(MaterialTheme.colorScheme.primary, CircleShape).padding(vertical = 8.dp)
            .fillMaxWidth().padding(horizontal = 16.dp),
        textAlign = TextAlign.Center
    )
}
