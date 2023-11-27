package sdu.mobile.xpence.ui.components.groupCard

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sdu.mobile.xpence.ui.utils.Group

@Composable
fun GroupDescription(group: Group){
    Text(
        text = group.description,
        fontSize = 15.sp,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier
            .padding(
                start = 10.dp,
                top = 5.dp,
                end = 10.dp,
                bottom = 5.dp
            )
    )
}
