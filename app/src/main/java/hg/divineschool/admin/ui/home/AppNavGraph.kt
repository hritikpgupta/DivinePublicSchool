package hg.divineschool.admin.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import hg.divineschool.admin.AppScreen
import hg.divineschool.admin.BottomNavItem
import hg.divineschool.admin.ui.home.attendance.AttendanceScreen
import hg.divineschool.admin.ui.home.dashboard.DashboardScreen
import hg.divineschool.admin.ui.home.dashboard.invoiceStudent.StudentInvoice
import hg.divineschool.admin.ui.home.dashboard.registerStudent.RegisterStudent
import hg.divineschool.admin.ui.home.dashboard.studentsScreen.StudentsList
import hg.divineschool.admin.ui.home.dashboard.updateStudent.UpdateStudent
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
        studentListNav(navController)
    }
}

fun NavGraphBuilder.studentListNav(navController: NavHostController) {
    navigation(
        startDestination = AppScreen.StudentScreen.StudentList.route + "/{id}/{name}",
        route = AppScreen.StudentScreen.StudentList.route
    ) {

        composable(AppScreen.StudentScreen.StudentList.route + "/{id}/{name}") { it ->
            it.arguments.let {
                it?.getString("id")?.let { it1 ->
                    StudentsList(
                        classID = it1,
                        className = it.getString("name")!!,
                        navController,
                        hiltViewModel()
                    )
                }
            }
        }

        composable(AppScreen.StudentScreen.RegisterStudent.route + "/{id}/{name}") {
            it.arguments.let { bundle ->
                bundle?.getString("id")?.let { it1 ->
                    RegisterStudent(
                        classID = it1,
                        className = bundle.getString("name")!!,
                        navController,
                        hiltViewModel()
                    )
                }
            }
        }
        composable(AppScreen.StudentScreen.UpdateStudent.route + "/{id}/{name}") {
            it.arguments.let { bundle ->
                bundle?.getString("id")?.let { it1 ->
                    UpdateStudent(
                        classID = it1,
                        className = bundle.getString("name")!!,
                        navController,
                        hiltViewModel()
                    )
                }
            }
        }
        composable(AppScreen.StudentScreen.StudentInvoice.route + "/{id}/{name}/{scholarNumber}") {
            it.arguments.let { bundle ->
                bundle.let { it ->
                    StudentInvoice(
                        classID = it?.getString("id").toString(),
                        className = it?.getString("name").toString(),
                        scholarNumber = it?.getString("scholarNumber").toString(),
                        navController = navController,
                        hiltViewModel()
                    )
                }
            }

        }

    }
}

