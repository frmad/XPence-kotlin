package sdu.mobile.xpence.ui.screens

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import sdu.mobile.xpence.ui.tabs.GroupTab
import sdu.mobile.xpence.ui.tabs.HomeTab
import sdu.mobile.xpence.ui.tabs.ProfileTab
import sdu.mobile.xpence.ui.utils.authenticationState

class AppContent : Screen {
    @Composable
    override fun Content() {
        TabNavigator(HomeTab) {
            Scaffold(
                content = {
                    key(authenticationState) {
                        CurrentTab()
                    }
                },
                bottomBar = {
                    NavigationBar {
                        TabNavigationItem(HomeTab)
                        TabNavigationItem(GroupTab)
                        TabNavigationItem(ProfileTab)
                    }
                }
            )
        }
    }

    @Composable
    private fun RowScope.TabNavigationItem(tab: Tab) {
        val tabNavigator = LocalTabNavigator.current

        NavigationBarItem(
            selected = tabNavigator.current == tab,
            onClick = { tabNavigator.current = tab },
            icon = { tab.options.icon?.let { Icon(painter = it, contentDescription = tab.options.title) } },
            label = { Text(text = tab.options.title) }
        )
    }
}