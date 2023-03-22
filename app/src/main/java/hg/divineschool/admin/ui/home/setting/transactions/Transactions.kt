package hg.divineschool.admin.ui.home.setting

import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.google.accompanist.insets.ui.Scaffold
import hg.divineschool.admin.ui.home.DPSBar

@Composable
fun Transactions(navController: NavController) {
    Scaffold(scaffoldState = rememberScaffoldState(),
        topBar = {
            DPSBar(onBackPressed = {
                navController.popBackStack()
            }, className = "Transactions")
        }
    ) {

    }
}