package sdu.mobile.xpence.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sdu.mobile.xpence.ui.utils.Group

@Composable
fun GroupName(group: Group){
    Text(
        text = group.name,
        fontSize = 22.sp,
        modifier = Modifier
            .padding(
                start = 10.dp,
                top = 10.dp,
                end = 10.dp,
                bottom = 5.dp
            )
    )
}