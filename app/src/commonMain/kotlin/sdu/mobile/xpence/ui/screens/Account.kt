package sdu.mobile.xpence.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import sdu.mobile.xpence.ui.components.Greeting
import sdu.mobile.xpence.ui.utils.AuthenticationProvider

@Composable
fun Account(
    onLogin: (String, String) -> Unit,
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(
                start = 16.dp,
                top = 30.dp,
                end = 16.dp,
                bottom = 30.dp
            )
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Greeting(if (AuthenticationProvider.isLoggedIn()) "Logged in" else "Logged out")

        if (!AuthenticationProvider.isLoggedIn()){
            user_name_textField()
            password_textField()


            Button(
                onClick = {
                    onLogin("admin", "admin")
                },
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        top = 30.dp,
                        end = 16.dp,
                        bottom = 30.dp
                    )
            ) {
                Text(text = "Login")
            }
        }

        if (AuthenticationProvider.isLoggedIn()) {
            Button(
                onClick = onLogout,
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        top = 30.dp,
                        end = 16.dp,
                        bottom = 30.dp
                    )
            ) {
                Text(text = "Logout")
            }
        }
    }
}

@Composable
fun user_name_textField() {
    var input by rememberSaveable { mutableStateOf("") }

    OutlinedTextField(
        value = input,
        onValueChange = { newText ->
            input = newText.trimStart { it == '0' }
        },
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

@Composable
fun password_textField() {
    var input by rememberSaveable { mutableStateOf("") }

    OutlinedTextField(
        value = input,
        onValueChange = { newText ->
            input = newText.trimStart { it == '0' }
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


