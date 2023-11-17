package sdu.mobile.xpence.ui.tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import sdu.mobile.xpence.ui.screens.GroupDetail

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

        Column {
            Text(text = "Groups")
            Button(
                onClick = { navigator.parent?.push(GroupDetail(1)) }
            ) {
                Text(text = "Detail")
            }
        }
    }
}