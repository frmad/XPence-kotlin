package sdu.mobile.xpence.ui.tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import sdu.mobile.xpence.ui.components.GroupCard
import sdu.mobile.xpence.ui.screens.GroupDetail
import sdu.mobile.xpence.ui.utils.QueryState
import sdu.mobile.xpence.ui.utils.getGroups
import sdu.mobile.xpence.ui.utils.usingAPI

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


        var refresh by rememberSaveable { mutableStateOf(false) }
        key(refresh) {
            val result by usingAPI { client ->
                getGroups(client)
            }



            when (val res = result) {
                is QueryState.Success -> {
                    Column {
                        res.data.forEach { group ->
                            GroupCard(
                                group.name,
                                group.description,
                            ) { navigator.parent?.push(GroupDetail(group)) }
                        }
                    }
                }

                is QueryState.Error -> Text(text = res.message)
                is QueryState.Loading -> Text(text = "Loading")
                else -> {}
            }
        }
    }
}
