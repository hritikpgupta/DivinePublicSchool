package hg.divineschool.admin

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

sealed class AppScreen(@StringRes val title: Int?, @DrawableRes val icon: Int?, val route: String) {

    object AuthScreen : AppScreen(null, null, "nav_auth_screen")


}