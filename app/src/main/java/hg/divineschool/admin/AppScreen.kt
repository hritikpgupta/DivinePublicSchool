package hg.divineschool.admin

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

sealed class AppScreen(@StringRes val title: Int?, @DrawableRes val icon: Int?, val route: String) {

    object AuthScreen : AppScreen(null, null, "nav_auth_screen")
    object StudentScreen : AppScreen(null, null, "nav_student_screen"){
        object StudentList : AppScreen(null, null,"student_list")
        object RegisterStudent : AppScreen(null, null,"register_student")
        object UpdateStudent : AppScreen(null, null,"update_student")
        object StudentInvoice : AppScreen(null, null,"invoice_student")
    }



}

sealed class BottomNavItem( @StringRes val title: Int, @DrawableRes val icon: Int, val route: String) {
    object Home : BottomNavItem(R.string.bottom_nav_home, R.drawable.baseline_home_24, "home")
    object Attendance : BottomNavItem(R.string.bottom_nav_attendance, R.drawable.attendance, "attendance")
    object Settings : BottomNavItem(R.string.bottom_nav_settings, R.drawable.baseline_settings_24, "settings")
    object AdminSettings : AppScreen(null, null,"admin_settings")

}