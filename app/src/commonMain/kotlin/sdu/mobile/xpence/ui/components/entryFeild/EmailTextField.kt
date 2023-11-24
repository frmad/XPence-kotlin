package sdu.mobile.xpence.ui.components.entryFeild

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EmailTextField(
    email: String,
    onTextChange: (String) -> Unit
) {
    var isvalid by remember { mutableStateOf(false) }

    OutlinedTextField(
        isError = !isvalid,
        supportingText = {
            if (!isvalid) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "This is not a valid Email",
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        value = email,
        onValueChange = onTextChange,
        label = { Text("Email") },
        placeholder = { Text("John@doe.com") },
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
private fun isValidText(text: String): Boolean {
    // Add your custom validation rules here
    return text.matches(Regex("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-]+)(\\.[a-zA-Z]{2,5}){1,2}\$"))
}