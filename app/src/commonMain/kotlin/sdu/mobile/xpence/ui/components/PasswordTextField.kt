package sdu.mobile.xpence.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun PasswordTextField(
    password: String,
    onTextChange: (String) -> Unit
) {
    OutlinedTextField(
        value = password,
        onValueChange = onTextChange,
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