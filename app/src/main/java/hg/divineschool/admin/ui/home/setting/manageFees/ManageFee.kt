package hg.divineschool.admin.ui.home.setting.manageFees

import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.google.accompanist.insets.ui.Scaffold
import hg.divineschool.admin.ui.home.DPSBar

@Composable
fun ManageFee(navController: NavController) {
    Scaffold(scaffoldState = rememberScaffoldState(),
        topBar = {
            DPSBar(onBackPressed = {
                navController.popBackStack()
            }, className = "Manage Fees")
        }
    ) {

    }
}