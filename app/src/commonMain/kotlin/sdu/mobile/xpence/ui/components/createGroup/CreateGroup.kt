package sdu.mobile.xpence.ui.components.createGroup

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.launch
import sdu.mobile.xpence.ui.utils.*

@Composable
fun createGroup(){
    var isDialogVisible by remember { mutableStateOf(false) }
    OutlinedButton(
        onClick = { isDialogVisible = true},
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .width(200.dp)
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Create New Group")
        }
    }

    if (isDialogVisible) {
        createDialog(
            onDismiss = { isDialogVisible = false },
        )
    }


}


@Composable
fun createDialog(
    onDismiss: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(dismissOnClickOutside = false)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxHeight()
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            headerText()

            Spacer(modifier = Modifier.height(5.dp))

            var name by rememberSaveable { mutableStateOf("") }
            var description by rememberSaveable { mutableStateOf("") }

            groupNameTextField(name = name, onTextChange = { name = it })

            Spacer(modifier = Modifier.height(5.dp))

            groupDescriptionTextField(description = description, onTextChange = { description = it })

            Spacer(modifier = Modifier.height(10.dp))

            var selected by remember { mutableStateOf(listOf<User>()) }

            val currentUser by usingAPI { client ->
                getCurrentUser(client)
            }

            val allUsers by usingAPI { client ->
                getUsers(client)
            }

            val users = remember { mutableStateListOf<User>() }

            when (val all = allUsers) {
                is QueryState.Success -> {
                    users.clear()
                    all.data.forEach { user ->
                        when(val current = currentUser){
                            is QueryState.Success -> {
                                if(user != current.data){
                                    users.add(user)
                                }
                            }

                            is QueryState.Error -> Text(text = current.message)
                            is QueryState.Loading -> Text(text = "Loading")
                        }
                    }
                }
                is QueryState.Error -> Text(text = all.message)
                is QueryState.Loading -> Text(text = "Loading")
                else -> {}
            }

            selectGroupMembers(
                users = users,
                selectedItems = selected,
                onSelectItems = { selected = it },
            )

            Spacer(modifier = Modifier.height(10.dp))

            val dkk = remember { "DKK" }
            val owner = remember { false }

            Text(selected.toString())

            Button(
                onClick = {
                    coroutineScope.launch {
                        getHttpClient(authenticationState)?.let {client ->
                            val creationResult = createGroup(client, name, description, dkk.toString())

                            /*when(val current = currentUser){
                                is QueryState.Success -> {
                                    addGroupMember(client, creationResult.id, current.data.username, owner)
                                }
                                is QueryState.Error -> Text(text = current.message)
                                is QueryState.Loading -> Text(text = "Loading")
                            }*/

                            selected.forEach { member ->
                                addGroupMember(client, creationResult.id, member.username, owner)
                            }
                        }
                    }

                    onDismiss()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Done", modifier = Modifier.padding(8.dp))
            }
            Spacer(modifier = Modifier.height(20.dp))

        }
    }
}

@Composable
fun headerText(){
    Text(
        text = "Create a new group",
        color = MaterialTheme.colorScheme.onPrimary,
        fontSize = 20.sp,
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold, textAlign = TextAlign.Center
    )
}

@Composable
fun groupNameTextField(
    name: String,
    onTextChange: (String) -> Unit
){
    TextField(
        value = name,
        onValueChange = onTextChange,
        label = { Text("Group name") },
        placeholder = { Text(text = "Enter the group name") },
        singleLine = true,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        textStyle = TextStyle(color = Color.Black, fontSize = 15.sp)
    )
}

@Composable
fun groupDescriptionTextField(
    description: String,
    onTextChange: (String) -> Unit
){
    TextField(
        value = description,
        onValueChange = onTextChange,
        label = { Text("Group description") },
        placeholder = { Text(text = "Enter a description for the group") },
        singleLine = true,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        textStyle = TextStyle(color = Color.Black, fontSize = 15.sp)
    )
}

