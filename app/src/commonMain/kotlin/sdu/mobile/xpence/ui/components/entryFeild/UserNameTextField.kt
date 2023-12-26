package sdu.mobile.xpence.ui.components.entryFeild

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun UserNameTextField(
    username: String,
    onTextChange: (String) -> Unit
) {
    OutlinedTextField(
        value = username,
        onValueChange = onTextChange,
        label = { Text("User name") },
        placeholder = { Text("User name") },
        singleLine = true,
        modifier = Modifier
            .padding(
                start = 16.dp,
                top = 30.dp,
                end = 16.dp,
                bottom = 20.dp
            )
    )
}