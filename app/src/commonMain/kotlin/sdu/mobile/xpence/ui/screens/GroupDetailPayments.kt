package sdu.mobile.xpence.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow


class GroupDetailPayments : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        Scaffold {
            Column {
                Text("Group payments")
                Button(
                    onClick = { navigator.pop() }
                ) {
                    Text(text = "Go back")
                }
            }
        }

    }
}