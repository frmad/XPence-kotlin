package sdu.mobile.xpence.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import sdu.mobile.xpence.ui.components.GroupList
import sdu.mobile.xpence.ui.components.Header

@Composable
fun Home() {
    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Header()
        GroupList()
    }
}