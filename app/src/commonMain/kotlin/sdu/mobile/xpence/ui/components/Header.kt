package sdu.mobile.xpence.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sdu.mobile.xpence.ui.theme.Purple80

@Composable
fun Header() {
    Box(
        modifier = Modifier
            .padding(bottom = 20.dp)
            .fillMaxWidth()
            .height(80.dp)
            .background(Purple80, shape = RoundedCornerShape(topEnd = 0.dp, bottomEnd = 20.dp)),

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
            fontSize = 30.sp,
        )
    }
}