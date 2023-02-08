package hg.divineschool.admin.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import hg.divineschool.admin.AppScreen
import hg.divineschool.admin.ui.auth.LoginScreen


@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = AppScreen.AuthScreen.route) {
        composable(AppScreen.AuthScreen.route) {
            LoginScreen(hiltViewModel())
        }
    }

}