package hg.divineschool.admin

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

sealed class AppScreen(@StringRes val title: Int?, @DrawableRes val icon: Int?, val route: String) {

    object AuthScreen : AppScreen(null, null, "nav_auth_screen")
    object StudentScreen : AppScreen(null, null, "nav_student_screen"){
        object StudentList : AppScreen(null, null,"student_list")
        object RegisterStudent : AppScreen(null, null,"register_student")
        object CameraScreen : AppScreen(null, null,"camera_screen")
    }



}

sealed class BottomNavItem( @StringRes val title: Int, @DrawableRes val icon: Int, val route: String) {
    object Home : BottomNavItem(R.string.bottom_nav_home, R.drawable.baseline_home_24, "home")
    object Attendance : BottomNavItem(R.string.bottom_nav_attendance, R.drawable.attendance, "attendance")
    object Invoice : BottomNavItem(R.string.bottom_nav_invoice, R.drawable.invoice, "invoice")
    object Notification : BottomNavItem(R.string.bottom_nav_notification, R.drawable.baseline_notifications_active_24, "notification")
}