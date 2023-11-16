package sdu.mobile.xpence.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.unit.sp
import sdu.mobile.xpence.ui.theme.Purple80
import sdu.mobile.xpence.ui.utils.QueryState
import sdu.mobile.xpence.ui.utils.getGroups
import sdu.mobile.xpence.ui.utils.usingAPI

@Composable
fun Home() {

    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {
        Title()
        GroupList()
    }
}


@Composable
fun GroupList() {
    val result by usingAPI { client ->
        getGroups(client)
    }
    Box(){
        when (val res = result) {
            is QueryState.Success -> {
                Column(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                ) {
                    res.data.forEach { group ->
                        Text(
                            text = group.name,
                            fontSize = 22.sp,
                            modifier = Modifier
                                .padding(
                                    start = 5.dp,
                                    top = 20.dp,
                                    end = 5.dp,
                                    bottom = 20.dp
                                )
                        )
                    }
                }
            }
            is QueryState.Error -> Text(
                text = res.message,
                color = Color.Red
            )
            is QueryState.Loading -> Text(
                text = "Loading",
                color = Purple80
            )
        }
    }
}

@Composable
fun Title() {
    Box(
        modifier = Modifier
            .padding(bottom = 20.dp)
            .fillMaxWidth()
            .height(80.dp)
            .background(Purple80, shape = RectangleShape)
    ) {
        Text(
            text = "XPense",
            modifier = Modifier
                .padding(
                    start = 5.dp,
                    top = 5.dp,
                    end = 5.dp,
                    bottom = 5.dp
                )
                .align(Alignment.Center),
            color = Color.Black,
            fontSize = 30.sp
        )
    }
}
