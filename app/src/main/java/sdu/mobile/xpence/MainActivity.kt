package sdu.mobile.xpence

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sdu.mobile.xpence.ui.theme.XPenceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            XPenceTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val greetingText by remember { mutableStateOf("Cassidy") }
                    NavBar(modifier = Modifier,Color.Yellow )
                    Greeting(greetingText)
                }
            }
        }
    }
}




@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Goodbye $name!",
      //  modifier = modifier.background(Color.Gray)
    )

}




@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    XPenceTheme {
        Greeting("Android")
    }
}

@Preview(showBackground = true)
@Composable
fun NavBarPreview() {
    XPenceTheme {
        NavBar(Modifier, Color.Blue)
    }
}
