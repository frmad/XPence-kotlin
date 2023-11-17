package sdu.mobile.xpence.ui.tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import sdu.mobile.xpence.ui.utils.QueryState
import sdu.mobile.xpence.ui.utils.getGroups
import sdu.mobile.xpence.ui.utils.usingAPI
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import sdu.mobile.xpence.ui.screens.GroupDetail
import androidx.compose.material3.Button
import sdu.mobile.xpence.ui.components.GroupCard

object GroupTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Groups"
            val icon = rememberVectorPainter(Icons.Default.CheckCircle)
            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon,
                )
            }
        }

@Composable
override fun Content() {
    val navigator = LocalNavigator.currentOrThrow

    val result by usingAPI { client ->
        getGroups(client)
    }

    when (val res = result) {
        is QueryState.Success -> {
            Column {
                res.data.forEach { group ->
                    GroupCard(group.name, 195, group.currencyCode, { navigator.parent?.push(GroupDetail(group.id)) })
                }
            }
        }

        is QueryState.Error -> Text(text = res.message)
        is QueryState.Loading -> Text(text = "Loading")
        else -> {}
    }
    }
}
