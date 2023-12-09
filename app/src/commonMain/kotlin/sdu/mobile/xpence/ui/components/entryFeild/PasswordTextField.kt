package sdu.mobile.xpence.ui.components.entryFeild

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun PasswordTextField(
    password: String,
    onTextChange: (String) -> Unit
) {
    var isvalid by remember { mutableStateOf(false) }

    OutlinedTextField(
        isError = !isvalid,
        supportingText = {
            if (!isvalid) {
                Column {
                    Text(
                        text = "8 <= characters, 0 < uppercase letter,", //Minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character
                        color = MaterialTheme.colorScheme.error
                    )
                    Text(
                        text = "0 < lowercase letter, 0 < number and", //Minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character
                        color = MaterialTheme.colorScheme.error
                    )
                    Text(
                        text = "0 < special character", //Minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        },
        value = password,
        onValueChange = { value ->
            isvalid = isValidText(value)
            onTextChange(value)
        },
        label = { Text("Password") },
        placeholder = { Text("Password") },
        visualTransformation = PasswordVisualTransformation(),
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
    return text.matches(Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$"))
}