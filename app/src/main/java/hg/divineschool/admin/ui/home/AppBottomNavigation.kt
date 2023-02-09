package hg.divineschool.admin.ui.home

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import hg.divineschool.admin.BottomNavItem
import hg.divineschool.admin.R

@Composable
fun AppBottomNavigation(navController: NavController) {
    val navItems = listOf(
        BottomNavItem.Home,
        BottomNavItem.Invoice,
        BottomNavItem.Attendance,
        BottomNavItem.Notification
    )
    BottomAppBar(
        backgroundColor = colorResource(id = R.color.purple_500),
        elevation = 0.dp,
        cutoutShape = MaterialTheme.shapes.small.copy(CornerSize(percent = 100))
    ) {
        BottomNavigation(
            elevation = 0.dp,
            modifier = Modifier.padding(0.dp, 0.dp, 60.dp, 0.dp),
            backgroundColor = colorResource(id = R.color.purple_500),
            contentColor = Color.Black
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            navItems.forEach { item ->
                BottomNavigationItem(selected = currentRoute == item.route,
                    icon = {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = stringResource(id = item.title)
                        )
                    },
                    label = { Text(text = stringResource(id = item.title), fontSize = 9.sp) },
                    selectedContentColor = Color.Black,
                    unselectedContentColor = Color.Black.copy(0.4f),
                    alwaysShowLabel = true,
                    onClick = {
                        navController.navigate(item.route) {
                            navController.graph.startDestinationRoute?.let { screenRoute ->
                                popUpTo(screenRoute) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    })
            }

        }
    }
}