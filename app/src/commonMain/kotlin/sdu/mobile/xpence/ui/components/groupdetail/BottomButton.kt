package sdu.mobile.xpence.ui.components.groupdetail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BottomButton(enabled: Boolean, content: @Composable RowScope.() -> Unit) {
    Button(
        onClick = {},
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20),
        border = BorderStroke(
            2.dp,
            if (enabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (enabled) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondary,
            contentColor = if (enabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
        ),
        enabled = enabled,
        content = content
    )
}
