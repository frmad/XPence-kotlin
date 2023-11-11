package sdu.mobile.xpence

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp


var navItems: List<NavItemData> = listOf(
NavItemData(
title = "Home",
selectedIcon = Icons.Filled.Home,
unselectedIcon = Icons.Outlined.Home,
hasBadge = false
),
NavItemData(
title = "Groups",
selectedIcon = Icons.Filled.CheckCircle,
unselectedIcon = Icons.Outlined.CheckCircle,
hasBadge = true
),
NavItemData(

title = "Account",
selectedIcon = Icons.Filled.Face,
unselectedIcon = Icons.Outlined.Face,
hasBadge = false
)
)
fun updateNavItem(index: Int, newItem: NavItemData) {
    if (index in navItems.indices) {
        navItems = navItems.toMutableList().apply {
            this[index] = newItem
        }
    }
}



data class NavItemData(
    val title:String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasBadge: Boolean
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavBar(modifier: Modifier, containerColor: Color){
    var selectedItem by remember { mutableStateOf(0) }
    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = containerColor) {

                navItems.forEachIndexed {index, navItemData ->
                    NavigationBarItem(
                        icon = { Icon(if (selectedItem == index) navItemData.selectedIcon else navItemData.unselectedIcon, contentDescription = navItemData.title)
                               BadgedBox(badge = {
                                   if (navItemData.hasBadge) Badge(modifier = Modifier.size(5.dp), contentColor = Color.Blue){}

                               }) { }
                        },
                        label = { Text(navItemData.title) },
                        selected = selectedItem == index,
                        onClick = { selectedItem = index },
                        alwaysShowLabel = false,
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor =  Color.Transparent
                    )
                    )


                }

            }
        }
    ) {
            contentPadding ->
        Column {

            modifier.padding(contentPadding)
                .background(Color.Yellow)
        }
    }
}
