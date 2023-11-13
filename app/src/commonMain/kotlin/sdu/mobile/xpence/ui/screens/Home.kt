package sdu.mobile.xpence.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import sdu.mobile.xpence.ui.utils.QueryState
import sdu.mobile.xpence.ui.utils.getGroups
import sdu.mobile.xpence.ui.utils.usingAPI

@Composable
fun Home() {
    val result by usingAPI { client ->
        getGroups(client)
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
