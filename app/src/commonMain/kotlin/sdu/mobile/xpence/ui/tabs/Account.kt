package sdu.mobile.xpence.ui.tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import sdu.mobile.xpence.ui.utils.*

@Composable
fun Account() {
    val scope = rememberCoroutineScope()

    Column {
        Text(text = "Account")
        Button(
            onClick = {
                scope.launch {
                    authenticationState = login("admin", "admin")
                }
            }
        ) {
            Text(text = "Login")
        }

        Button(
            onClick = {
                scope.launch {
                    authenticationState = logout()
                }
            }
        ) {
            Text(text = "Logout")
        }

        val result by usingAPI { client ->
            getGroups(client)
        }

        when (val res = result) {
            is QueryState.Success -> {
                Column {
                    res.data.forEach { group ->
                        Text(text = group.name)
                    }
                }
            }

            is QueryState.Error -> Text(text = res.message)
            is QueryState.Loading -> Text(text = "Loading")
            else -> {}
        }
    }
}