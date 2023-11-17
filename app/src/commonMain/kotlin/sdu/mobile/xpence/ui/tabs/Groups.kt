package sdu.mobile.xpence.ui.tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import sdu.mobile.xpence.ui.screens.GroupDetail

@Composable
fun Groups() {
    val navigator = LocalNavigator.currentOrThrow

    Column {
        Text(text = "Groups")
        Button(
            onClick = { navigator.push(GroupDetail(1)) }
        ) {
            Text(text = "Detail")
        }
    }
}
