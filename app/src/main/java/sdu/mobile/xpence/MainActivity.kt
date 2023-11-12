package sdu.mobile.xpence

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import sdu.mobile.xpence.ui.theme.XPenceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            XPenceTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
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
        }
    }
}

