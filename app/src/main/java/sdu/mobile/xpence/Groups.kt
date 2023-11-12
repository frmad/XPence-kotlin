package sdu.mobile.xpence

//import androidx.compose.ui.platform.LocalWindowOwner
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import sdu.mobile.xpence.api.DataModel
import sdu.mobile.xpence.api.RetrofitAPI


//import com.example.featherandroidtasks.ui.theme.FeatherAndroidTasksTheme

@OptIn(ExperimentalMaterial3Api::class)
//@Preview
/*@Composable
fun Groups() {
    var newGroupName by remember { mutableStateOf("") }
    var newGroupDescription by remember { mutableStateOf("") }
    var groups by remember { mutableStateOf(listOf(Group("My Group", "Description 1"), Group("Other Group", "Description 2"), Group("Other Group", "Description 3"))) }
    var isDialogVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(16.dp)
    ) {
        // List of groups as buttons
        LazyColumn {
            items(groups) { group ->
                GroupButton(group = group)
            }
        }

        // Button to create a new group
        OutlinedButton(
            onClick = { isDialogVisible = true },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Create New Group")
            }
        }

        // Dialog for creating a new group
        if (isDialogVisible) {
            CreateGroupDialog(
                onDismiss = { isDialogVisible = false },
                onGroupCreated = { newGroup, newDis ->
                    groups = groups + Group(newGroup, newDis)
                    newGroupName = newGroup
                    newGroupDescription = newDis
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateGroupDialog(
    onDismiss: () -> Unit,
    onGroupCreated: (String, String) -> Unit
) {
    var groupName by remember { mutableStateOf("") }
    var groupDescription by remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(dismissOnClickOutside = false)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Text(
                text = "Create New Group",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = groupName,
                onValueChange = { groupName = it },
                label = { Text("Group Name") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        onGroupCreated(groupName, groupDescription)
                        onDismiss()
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = groupDescription,
                onValueChange = { groupDescription = it },
                label = { Text("Group Description") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        onGroupCreated(groupName, groupDescription)
                        onDismiss()
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            Button(
                onClick = {
                    onGroupCreated(groupName, groupDescription)
                    postDataUsingRetrofit(groupName, groupDescription)
                    onDismiss()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Text(text = "Create")
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)*/

    //@Preview
    @Composable
    fun Groups() {
    /*var newGroupName by remember { mutableStateOf("") }
    var newGroupDescription by remember { mutableStateOf("") }
    var groups by remember { mutableStateOf(listOf(Group("My Group", "Description 1"), Group("Other Group", "Description 2"), Group("Other Group", "Description 3"))) }*/
    var isDialogVisible by remember { mutableStateOf(false) }

    /*Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(16.dp)
    ) {
        // List of groups as buttons
        LazyColumn {
            items(groups) { group ->
                GroupButton(group = group)
            }
        }*/

        // Button to create a new group
        OutlinedButton(
            onClick = { isDialogVisible = true },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .width(200.dp) // Adjust the width as needed
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Create New Group")
            }
        }

        // Dialog for creating a new group
        if (isDialogVisible) {
            postData(
                onDismiss = { isDialogVisible = false },
                /*onGroupCreated = { newGroup, newDis ->
                    groups = groups + Group(newGroup, newDis)
                    newGroupName = newGroup
                    newGroupDescription = newDis
                }*/
            )
        }
    }


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun postData(
    onDismiss: () -> Unit
) {
    val name = remember {
        mutableStateOf(TextFieldValue())
    }
    val description = remember {
        mutableStateOf(TextFieldValue())
    }
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(dismissOnClickOutside = false)
    ) {
        // on below line we are creating a column.
        Column(
            // on below line we are adding a modifier to it
            // and setting max size, max height and max width
            modifier = Modifier
                .fillMaxSize()
                .fillMaxHeight()
                .fillMaxWidth(),
            // on below line we are adding vertical
            // arrangement and horizontal alignment.
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // on below line we are creating a text
            Text(
                // on below line we are specifying text as
                // Session Management in Android.
                text = "All groups",
                // on below line we are specifying text color.
                color = Color.Blue,
                fontSize = 20.sp,
                // on below line we are specifying font family
                fontFamily = FontFamily.Default,
                // on below line we are adding font weight
                // and alignment for our text
                fontWeight = FontWeight.Bold, textAlign = TextAlign.Center
            )
            //on below line we are adding spacer
            Spacer(modifier = Modifier.height(5.dp))
            // on below line we are creating a text field for our email.
            TextField(
                // on below line we are specifying value for our email text field.
                value = name.value,
                // on below line we are adding on value change for text field.
                onValueChange = { name.value = it },
                // on below line we are adding place holder as text as "Enter your email"
                placeholder = { Text(text = "Enter the group name") },
                // on below line we are adding modifier to it
                // and adding padding to it and filling max width
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                // on below line we are adding text style
                // specifying color and font size to it.
                textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
                // on below line we are adding single line to it.
                singleLine = true,
            )
            // on below line we are adding spacer
            Spacer(modifier = Modifier.height(5.dp))
            // on below line we are creating a text field for our email.
            TextField(
                // on below line we are specifying value for our email text field.
                value = description.value,
                // on below line we are adding on value change for text field.
                onValueChange = { description.value = it },
                // on below line we are adding place holder as text as "Enter your email"
                placeholder = { Text(text = "Enter a description for the group") },
                // on below line we are adding modifier to it
                // and adding padding to it and filling max width
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                // on below line we are adding text style
                // specifying color and font size to it.
                textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
                // on below line we ar adding single line to it.
                singleLine = true,
            )
            // on below line we are adding spacer
            Spacer(modifier = Modifier.height(10.dp))
            // on below line we are creating a button
            Button(
                onClick = {
                    // on below line we are calling make payment method to update data.
                    postDataUsingRetrofit(
                        name, description
                    )
                    // Close the dialog after clicking the button
                    onDismiss()
                },
                // on below line we are adding modifier to our button.
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // on below line we are adding text for our button
                Text(text = "Create Group", modifier = Modifier.padding(8.dp))
            }
            // on below line we are adding a spacer.
            Spacer(modifier = Modifier.height(20.dp))
            // on below line we are creating a text for displaying a response.

        }
    }
}

fun postDataUsingRetrofit(
    name: MutableState<TextFieldValue>,
    description: MutableState<TextFieldValue>,
) {
    var url = "https://xpense-api.gredal.dev"
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val retrofitAPI = retrofit.create(RetrofitAPI::class.java)

    val dataModel = DataModel(name.value.text, description.value.text)

    val call: Call<DataModel?>? = retrofitAPI.postData(dataModel)
}
