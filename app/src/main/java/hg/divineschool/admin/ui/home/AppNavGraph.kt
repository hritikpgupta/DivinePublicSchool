package hg.divineschool.admin.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import hg.divineschool.admin.BottomNavItem

@Composable
fun AppNavigationGraph(navController: NavHostController, modifier: Modifier) {
    NavHost(navController = navController, startDestination = BottomNavItem.Home.route) {
        composable(BottomNavItem.Home.route) {
            HomeScreen()
        }
        composable(BottomNavItem.Invoice.route) {
            NetworkScreen()
        }
        composable(BottomNavItem.Attendance.route) {
            AddPostScreen()
        }
        composable(BottomNavItem.Notification.route) {
            NotificationScreen()
        }

    }
}