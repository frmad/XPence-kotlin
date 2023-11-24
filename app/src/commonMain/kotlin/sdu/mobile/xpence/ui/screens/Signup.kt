package sdu.mobile.xpence.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import sdu.mobile.xpence.ui.components.entryFeild.EmailTextField
import sdu.mobile.xpence.ui.components.entryFeild.FullNameTextField
import sdu.mobile.xpence.ui.components.entryFeild.PasswordTextField
import sdu.mobile.xpence.ui.components.entryFeild.UserNameTextField

class Signup : Screen {

    @Composable
    override fun Content() {
        var username by rememberSaveable { mutableStateOf("") }
        var email by rememberSaveable { mutableStateOf("") }
        var fullName by rememberSaveable { mutableStateOf("") }
        var password by rememberSaveable { mutableStateOf("") }
        var passwordAgain by rememberSaveable { mutableStateOf("") }
        Scaffold (
            modifier = Modifier.fillMaxSize()
        ) {
            Column {
                FullNameTextField(fullName = fullName, onTextChange = { fullName = it })
                EmailTextField(email = email, onTextChange = { email = it })
                UserNameTextField(username = username, onTextChange = { username = it })
                PasswordTextField(password = password, onTextChange = { password = it })
                PasswordTextField(password = passwordAgain, onTextChange = { passwordAgain = it })
            }
        }
    }
}