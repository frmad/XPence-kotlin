package sdu.mobile.xpence

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import sdu.mobile.xpence.ui.theme.XPenceTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.layout.windowInsetsPadding

@Composable
internal fun App() = XPenceTheme {
    Surface(modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.safeDrawing), color = MaterialTheme.colorScheme.background) {
    var currentScreen by remember { mutableStateOf(Screen.Home) }
    NavBar(
        onItemSelected = { screen ->
            currentScreen = screen
        },
        modifier = Modifier,
        Color.Gray // This is for the color of the nav bar
    )
    //Note this will need all Screens in the Screen Enum or an else condition
    when (currentScreen) {
        Screen.Home -> Home()
        Screen.Groups -> Groups()
        Screen.Account -> Account()
    }
}
}

