package sdu.mobile.xpence.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.launch
import sdu.mobile.xpence.ui.components.entryFeild.EmailTextField
import sdu.mobile.xpence.ui.components.entryFeild.FullNameTextField
import sdu.mobile.xpence.ui.components.entryFeild.PasswordTextField
import sdu.mobile.xpence.ui.components.entryFeild.UserNameTextField
import sdu.mobile.xpence.ui.tabs.HomeTab
import sdu.mobile.xpence.ui.utils.authenticationState
import sdu.mobile.xpence.ui.utils.createUser
import sdu.mobile.xpence.ui.utils.login

class Signup : Screen {

    @Composable
    override fun Content() {
        var username by rememberSaveable { mutableStateOf("") }
        var email by rememberSaveable { mutableStateOf("") }
        var fullName by rememberSaveable { mutableStateOf("") }
        var password by rememberSaveable { mutableStateOf("") }
        var passwordAgain by rememberSaveable { mutableStateOf("") }
        val coroutineScope = rememberCoroutineScope()
        val navigator = LocalNavigator.currentOrThrow

        Scaffold (
            modifier = Modifier.fillMaxSize()
        ) {
            Column {
                FullNameTextField(fullName = fullName, onTextChange = { fullName = it })
                EmailTextField(email = email, onTextChange = { email = it })
                UserNameTextField(username = username, onTextChange = { username = it })
                PasswordTextField(password = password, onTextChange = { password = it })
                PasswordTextField(password = passwordAgain, onTextChange = { passwordAgain = it })

                Button(
                    onClick = {
                        coroutineScope.launch {
                            authenticationState = createUser(email, fullName, username, password)
                            navigator.parent?.push(AppContent())

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
                    Text(text = "Sign up")
                }
            }
        }
    }
}