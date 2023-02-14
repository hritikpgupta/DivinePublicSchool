package hg.divineschool.admin.ui.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import hg.divineschool.admin.BottomNavItem
import hg.divineschool.admin.ui.theme.lightFont

@Composable
fun AppBottomNavigation(navController: NavController) {
    val navItems = listOf(
        BottomNavItem.Home,
        BottomNavItem.Invoice,
        BottomNavItem.Attendance,
        BottomNavItem.Notification
    )
    BottomAppBar(
        backgroundColor = MaterialTheme.colors.background,
        elevation = 10.dp,
        contentPadding = PaddingValues(0.dp)
    ) {
        BottomNavigation(
            modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 0.dp),
            backgroundColor = MaterialTheme.colors.background,
            ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            navItems.forEach { item ->
                BottomNavigationItem(selected = currentRoute == item.route,
                    icon = {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = stringResource(id = item.title),
                            modifier = Modifier.requiredSize(width = 24.dp, height = 24.dp)
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(id = item.title),
                            fontFamily = lightFont,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    },
                    selectedContentColor = MaterialTheme.colors.onBackground,
                    unselectedContentColor = MaterialTheme.colors.onBackground.copy(0.4f),
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
