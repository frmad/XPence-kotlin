package sdu.mobile.xpence.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import sdu.mobile.xpence.ui.theme.Purple80
import sdu.mobile.xpence.ui.utils.QueryState
import sdu.mobile.xpence.ui.utils.getGroups
import sdu.mobile.xpence.ui.utils.usingAPI

@Composable
fun GroupList() {
    val result by usingAPI { client ->
        getGroups(client)
    }
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ){
        when (val res = result) {
            is QueryState.Success -> {
                Column(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(10.dp)
                ) {
                    res.data.forEach { group ->
                        Box(
                            modifier = Modifier
                                .border(BorderStroke(2.dp, Color.LightGray), shape = RoundedCornerShape(20.dp))
                                .padding(5.dp)
                                .width(500.dp)
                        ){
                            GroupName(group)

                            GroupDescription(group)
                        }
                        Spacer(modifier = Modifier.height(30.dp))
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


