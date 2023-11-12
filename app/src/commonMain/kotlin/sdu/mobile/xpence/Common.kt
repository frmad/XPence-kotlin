package sdu.mobile.xpence

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "You are on the $name! page",
        //  modifier = modifier.background(Color.Gray)
    )

}

