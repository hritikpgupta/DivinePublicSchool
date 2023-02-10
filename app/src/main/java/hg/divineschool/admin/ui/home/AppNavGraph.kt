package hg.divineschool.admin.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import hg.divineschool.admin.BottomNavItem
import hg.divineschool.admin.ui.home.attendance.AttendanceScreen
import hg.divineschool.admin.ui.home.dashboard.HomeScreen
import hg.divineschool.admin.ui.home.invoice.InvoiceScreen
import hg.divineschool.admin.ui.home.notification.NotificationScreen

@Composable
fun AppNavigationGraph(navController: NavHostController, modifier: Modifier) {
    NavHost(navController = navController, startDestination = BottomNavItem.Home.route) {
        composable(BottomNavItem.Home.route) {
            HomeScreen()
        }
        composable(BottomNavItem.Invoice.route) {
            InvoiceScreen()
        }
        composable(BottomNavItem.Attendance.route) {
            AttendanceScreen()
        }
        composable(BottomNavItem.Notification.route) {
            NotificationScreen()
        }

    }
}