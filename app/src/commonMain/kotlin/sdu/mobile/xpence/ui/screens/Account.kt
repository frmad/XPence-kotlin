package sdu.mobile.xpence.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import sdu.mobile.xpence.ui.components.Greeting
import sdu.mobile.xpence.ui.utils.AuthenticationProvider
import sdu.mobile.xpence.ui.components.UserNameTextField
import sdu.mobile.xpence.ui.components.PasswordTextField


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

            var username by rememberSaveable { mutableStateOf("") }
            var password by rememberSaveable { mutableStateOf("") }

            UserNameTextField(username = username, onTextChange = {username = it})
            PasswordTextField(password = password, onTextChange = {password = it})

            Button(
                onClick = {
                    onLogin(username, password)
                },
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        top = 30.dp,
                        end = 16.dp,
                        bottom = 30.dp
                    )
            ) {
                Text(text = "Log in")
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
                Text(text = "Log out")
            }
        }
    }
}






