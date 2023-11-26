package sdu.mobile.xpence

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import cafe.adriel.voyager.transitions.SlideTransition
import sdu.mobile.xpence.ui.screens.AppContent
import sdu.mobile.xpence.ui.theme.XPenceTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun App() = XPenceTheme {
    BottomSheetNavigator {
        Navigator(AppContent()) { navigator ->
            SlideTransition(navigator)
        }
    }
}

