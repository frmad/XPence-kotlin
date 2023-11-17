package sdu.mobile.xpence

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp


var navItems: List<NavItemData> = listOf(
    NavItemData(
        title = "Home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        hasBadge = false,
        screen = Screens.Home
    ),
    NavItemData(
        title = "Groups",
        selectedIcon = Icons.Filled.CheckCircle,
        unselectedIcon = Icons.Outlined.CheckCircle,
        hasBadge = true,
        screen = Screens.Groups
    ),
    NavItemData(

        title = "Account",
        selectedIcon = Icons.Filled.Face,
        unselectedIcon = Icons.Outlined.Face,
        hasBadge = false,
        screen = Screens.Account
    )
)

fun updateNavItem(index: Int, newItem: NavItemData) {
    if (index in navItems.indices) {
        navItems = navItems.toMutableList().apply {
            this[index] = newItem
        }
    }
}


// because structs sounded too complicated
data class NavItemData(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasBadge: Boolean,
    val screen: Screens
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavBar(
    selectedItem: Screens,
    onItemSelected: (Screens) -> Unit,
    containerColor: Color = Color.Yellow
) {

    NavigationBar(containerColor = containerColor) {
        navItems.forEachIndexed { index, navItemData ->
            NavigationBarItem(
                icon = {
                    Icon(
                        if (selectedItem == navItemData.screen) navItemData.selectedIcon else navItemData.unselectedIcon,
                        contentDescription = navItemData.title
                    )
                    // This adds the small bubble at the groups icon
                    BadgedBox(badge = {
                        if (navItemData.hasBadge) Badge(
                            modifier = Modifier.size(5.dp),
                            contentColor = Color.Blue
                        ) {}

                    }) { }
                },
                label = { Text(navItemData.title) },
                selected = selectedItem == navItemData.screen,
                onClick = {
                    if (navItemData.hasBadge) updateNavItem(
                        index, NavItemData(
                            title = navItemData.title,
                            selectedIcon = navItemData.selectedIcon,
                            unselectedIcon = navItemData.unselectedIcon,
                            hasBadge = false,
                            screen = navItemData.screen
                        )
                    )
                    onItemSelected(navItemData.screen)
                },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                )
            )


        }
    }
}