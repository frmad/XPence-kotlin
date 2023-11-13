package sdu.mobile.xpence

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.launch
import sdu.mobile.xpence.ui.screens.Account
import sdu.mobile.xpence.ui.screens.Groups
import sdu.mobile.xpence.ui.screens.Home
import sdu.mobile.xpence.ui.theme.XPenceTheme
import sdu.mobile.xpence.ui.utils.AuthenticationProvider
import sdu.mobile.xpence.ui.utils.getStoredAuthenticationData
import sdu.mobile.xpence.ui.utils.login
import sdu.mobile.xpence.ui.utils.logout

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun App() = XPenceTheme {
    val scope = rememberCoroutineScope()
    var currentScreen by remember { mutableStateOf(Screen.Home) }
    var authenticationData by remember { mutableStateOf(getStoredAuthenticationData()) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            NavBar(
                onItemSelected = { screen ->
                    currentScreen = screen
                },
                Color.Gray // This is for the color of the nav bar
            )
        },
    ) {
        Box(Modifier.safeDrawingPadding()) {
            AuthenticationProvider(authenticationData) {
                when (currentScreen) {
                    Screen.Home -> Home()
                    Screen.Groups -> Groups()
                    Screen.Account -> Account(
                        onLogin = { username, password ->
                            scope.launch {
                                authenticationData = login(username, password)
                            }
                        },
                        onLogout = {
                            authenticationData = logout()
                        }
                    )
                }
            }
        }
    }
}

