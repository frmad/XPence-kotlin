package sdu.mobile.xpence.ui.tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import kotlinx.coroutines.launch
import sdu.mobile.xpence.ui.components.Greeting
import sdu.mobile.xpence.ui.components.entryFeild.PasswordTextField
import sdu.mobile.xpence.ui.components.entryFeild.UserNameTextField
import sdu.mobile.xpence.ui.screens.Signup
import sdu.mobile.xpence.ui.utils.authenticationState
import sdu.mobile.xpence.ui.utils.login
import sdu.mobile.xpence.ui.utils.logout

object ProfileTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Account"
            val icon = rememberVectorPainter(Icons.Default.Face)
            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val coroutineScope = rememberCoroutineScope()


        Column(
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    top = 30.dp,
                    end = 16.dp,
                    bottom = 30.dp
                )
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Greeting(if (authenticationState.isLoggedIn()) "Log in" else "Log out")

            if (!authenticationState.isLoggedIn()) {

                var username by rememberSaveable { mutableStateOf("") }
                var password by rememberSaveable { mutableStateOf("") }

                UserNameTextField(username = username, onTextChange = { username = it })
                PasswordTextField(password = password, onTextChange = { password = it })

                Button(
                    onClick = {
                        coroutineScope.launch {
                            authenticationState = login(username, password)
                        }
                    },
                    modifier = Modifier
                        .padding(
                            start = 16.dp,
                            top = 30.dp,
                            end = 16.dp,
                            bottom = 30.dp
                        )
                ) {
                    Text(text = "Log in")
                }

                Button(
                    onClick = {
                        navigator.parent?.push(Signup())
                    },
                    modifier = Modifier
                        .padding(
                            start = 16.dp,
                            top = 20.dp,
                            end = 16.dp,
                            bottom = 30.dp
                        )
                ) {
                    Text(text = "Sign up")
                }

            }

            if (authenticationState.isLoggedIn()) {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            authenticationState = logout()
                        }
                    },
                    modifier = Modifier
                        .padding(
                            start = 16.dp,
                            top = 30.dp,
                            end = 16.dp,
                            bottom = 30.dp
                        )
                ) {
                    Text(text = "Log out")
                }
            }
        }
    }
}