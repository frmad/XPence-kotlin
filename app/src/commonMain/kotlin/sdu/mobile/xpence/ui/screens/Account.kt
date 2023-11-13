package sdu.mobile.xpence.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import sdu.mobile.xpence.ui.components.Greeting
import sdu.mobile.xpence.ui.utils.AuthenticationProvider

@Composable
fun Account(
    onLogin: (String, String) -> Unit,
    onLogout: () -> Unit
) {
    Column {
        Greeting(if (AuthenticationProvider.isLoggedIn()) "Logged in" else "Logged out")
        Button(
            onClick = {
                onLogin("admin", "admin")
            }
        ) {
            Text(text = "Login")
        }

        Button(
            onClick = onLogout
        ) {
            Text(text = "Logout")
        }
    }
}