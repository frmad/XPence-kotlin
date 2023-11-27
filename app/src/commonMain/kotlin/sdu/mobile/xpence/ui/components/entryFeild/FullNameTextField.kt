package sdu.mobile.xpence.ui.components.entryFeild

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FullNameTextField(
    fullName: String,
    onTextChange: (String) -> Unit
) {
    OutlinedTextField(
        value = fullName,
        onValueChange = onTextChange,
        label = { Text("Full name") },
        placeholder = { Text("John Doe") },
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
    return text.matches(Regex("[a-zA-Z]+"))
}