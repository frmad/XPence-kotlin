package sdu.mobile.xpence.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.screen.Screen
import sdu.mobile.xpence.NavBar
import sdu.mobile.xpence.Screens
import sdu.mobile.xpence.ui.tabs.Account
import sdu.mobile.xpence.ui.tabs.Groups
import sdu.mobile.xpence.ui.tabs.Home
import sdu.mobile.xpence.ui.utils.authenticationState

var currentScreen by mutableStateOf(Screens.Home)

class AppContent : Screen {
    @Composable
    override fun Content() {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,
            bottomBar = {
                NavBar(
                    selectedItem = currentScreen,
                    onItemSelected = { screen ->
                        currentScreen = screen
                    },
                    Color.Gray // This is for the color of the nav bar
                )
            },
        ) {
            Box(Modifier.safeDrawingPadding()) {
                key(authenticationState) {
                    when (currentScreen) {
                        Screens.Home -> Home()
                        Screens.Groups -> Groups()
                        Screens.Account -> Account()
                    }
                }
            }
        }
    }
}