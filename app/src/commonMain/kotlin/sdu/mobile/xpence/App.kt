package sdu.mobile.xpence

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import sdu.mobile.xpence.ui.screens.AppContent
import sdu.mobile.xpence.ui.theme.XPenceTheme

@Composable
internal fun App() = XPenceTheme {
    Navigator(AppContent()) { navigator ->
        SlideTransition(navigator)
    }
}

