package sdu.mobile.xpence.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
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
import sdu.mobile.xpence.ui.utils.authenticationState
import sdu.mobile.xpence.ui.utils.createUser

class Signup : Screen {

    @Composable
    override fun Content() {
        var username by rememberSaveable { mutableStateOf("") }
        var email by rememberSaveable { mutableStateOf("") }
        var fullName by rememberSaveable { mutableStateOf("") }
        var password by rememberSaveable { mutableStateOf("") }
        var passwordAgain by rememberSaveable { mutableStateOf("") }
        var failMessage by rememberSaveable { mutableStateOf("") }
        val coroutineScope = rememberCoroutineScope()
        val navigator = LocalNavigator.currentOrThrow

        Scaffold(
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    top = 30.dp,
                    end = 16.dp,
                    bottom = 30.dp
                )
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FullNameTextField(fullName = fullName, onTextChange = { fullName = it })
                EmailTextField(email = email, onTextChange = { email = it })
                UserNameTextField(username = username, onTextChange = { username = it })
                PasswordTextField(password = password, onTextChange = { password = it })
                PasswordTextField(password = passwordAgain, onTextChange = { passwordAgain = it })

                Text(text = failMessage)

                Button(
                    onClick = {
                        coroutineScope.launch {
                            try {
                                authenticationState = createUser(email, fullName, username, password)
                                navigator.parent?.pop()
                            } catch (ex: Throwable) {
                                failMessage = "There was an error creating your account\n"
                                println(ex.toString())
                            }
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
                Button(
                    onClick = {
                        navigator.pop()
                    },
                    modifier = Modifier
                        .padding(
                            start = 16.dp,
                            top = 30.dp,
                            end = 16.dp,
                            bottom = 30.dp
                        )
                ) {
                    Text(text = "Go Back To Login")
                }
            }
        }
    }
}