package hg.divineschool.admin.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import hg.divineschool.admin.AppScreen
import hg.divineschool.admin.BottomNavItem
import hg.divineschool.admin.ui.home.attendance.AttendanceScreen
import hg.divineschool.admin.ui.home.dashboard.DashboardScreen
import hg.divineschool.admin.ui.home.dashboard.studentsScreen.StudentsList
import hg.divineschool.admin.ui.home.invoice.InvoiceScreen
import hg.divineschool.admin.ui.home.notification.NotificationScreen

@Composable
fun AppNavigationGraph(navController: NavHostController, modifier: Modifier) {
    NavHost(navController = navController, startDestination = BottomNavItem.Home.route) {

        composable(BottomNavItem.Home.route) {
            DashboardScreen(hiltViewModel(), navController)
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
        composable(AppScreen.StudentListScreen.route + "/{id}/{name}") { it ->
            it.arguments.let {
                it?.getString("id")?.let { it1 ->
                    StudentsList(
                        classID = it1, className = it.getString("name")!!, navController
                    )
                }
            }
        }

    }
}