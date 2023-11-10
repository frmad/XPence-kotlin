package sdu.mobile.xpence

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import sdu.mobile.xpence.ui.theme.XPenceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            XPenceTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Greeting("Cassidy")
                }
                NavBar(modifier = Modifier,Color.Yellow )
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Goodbye $name!",
        modifier = modifier.background(Color.Yellow)
    )

}

@Composable
fun NavBar(modifier: Modifier, containerColor: Color){
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter

    ){
        NavigationBar { Color.Blue }
    }
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
