package sdu.mobile.xpence.ui.components.groupdetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import sdu.mobile.xpence.ui.screens.GroupDetailPayments

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupMemberDropdown() {
    var expanded by remember { mutableStateOf(false) }
    val items = listOf(
        "Alice Johnson",
        "John Doe",
        "Eva Davis",
        "Chris Miller",
        "Sarah White",
    )
    Box(modifier = Modifier.fillMaxWidth(0.7f)) {
        FilterChip(
            modifier = Modifier.fillMaxWidth(),
            selected = false,
            label = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Group members",
                    textAlign = TextAlign.Center
                )
            },
            onClick = { expanded = true },
            shape = CircleShape
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            items.forEachIndexed { index, s ->
                DropdownMenuItem(
                    text = {
                        Text(text = s)
                    },
                    onClick = {},
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "drawable icons",
                        )
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    val navigator = LocalNavigator.currentOrThrow

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(
            navigationIcon = {
                IconButton(
                    onClick = { navigator.pop() }, colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = ""
                    )
                }
            },
            title = { HeaderText("My group: 123kr") },
        )
        Row {
            Column(
                modifier = Modifier.fillMaxWidth(0.5f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FilterChip(
                    modifier = Modifier.fillMaxWidth(0.7f),
                    selected = false,
                    label = {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "See payment status",
                            textAlign = TextAlign.Center
                        )
                    },
                    onClick = { navigator.push(GroupDetailPayments()) },
                    shape = CircleShape
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                GroupMemberDropdown()
            }
        }
        Divider(color = Color.Gray, thickness = 0.5.dp)
    }
}