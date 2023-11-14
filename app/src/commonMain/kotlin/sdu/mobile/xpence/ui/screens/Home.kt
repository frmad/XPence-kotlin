package sdu.mobile.xpence.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import sdu.mobile.xpence.ui.theme.Purple80
import sdu.mobile.xpence.ui.utils.QueryState
import sdu.mobile.xpence.ui.utils.getGroups
import sdu.mobile.xpence.ui.utils.usingAPI

@Composable
fun Home() {
    val result by usingAPI { client ->
        getGroups(client)
    }
    Column {
        Title()
    }
    when (val res = result) {
        is QueryState.Success -> {
            Column {
                res.data.forEach { group ->
                    Text(text = group.name)
                }
            }
        }

        is QueryState.Error -> Text(text = res.message)
        is QueryState.Loading -> Text(text = "Loading")
    }
}

@Composable
fun Title() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()  // Fills the width of the screen
                .height(80.dp)    // Adjust the height as needed
                .background(Purple80, shape = RectangleShape)
        )
        Text(
            text = "XPense",
            modifier = Modifier
                .padding(
                    start = 5.dp,
                    top = 30.dp,
                    end = 30.dp,
                    bottom = 5.dp
                )
                .align(Alignment.TopCenter),
            color = Color.Black,
        )
    }
}
