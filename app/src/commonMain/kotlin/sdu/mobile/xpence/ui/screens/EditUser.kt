package sdu.mobile.xpence.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import sdu.mobile.xpence.ui.utils.QueryState
import sdu.mobile.xpence.ui.utils.editUser
import sdu.mobile.xpence.ui.utils.getCurrentUser
import sdu.mobile.xpence.ui.utils.usingAPI

class EditUser : Screen {

    @Composable
    override fun Content() {
        var failMessage by rememberSaveable { mutableStateOf("") }

        val result by usingAPI { client ->
            getCurrentUser(client)
        }
        var email by rememberSaveable{ mutableStateOf("") }
        var fullName by rememberSaveable{ mutableStateOf("") }
        when (val res = result) {
            is QueryState.Success -> {
                email= res.data.email
                fullName= res.data.fullName
                failMessage = ""
            }

            is QueryState.Error -> failMessage = res.message
            is QueryState.Loading -> failMessage = "Loading"
        }
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
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FullNameTextField(fullName = fullName, onTextChange = { fullName = it })
                EmailTextField(email = email, onTextChange = { email = it })

                Text(text = failMessage)

                Button(
                    onClick = {
                        coroutineScope.launch {
                            try {
                                editUser(email, fullName)
                                navigator.parent?.pop()
                            } catch (ex: Throwable) {
                                failMessage = "There was an error editing your account\n"
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
                    Text(text = "Save")
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
                    Text(text = "Back To Account")
                }
            }
        }
    }
}