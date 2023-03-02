package hg.divineschool.admin.ui.home.dashboard.invoiceStudent

import androidx.compose.foundation.layout.Column
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.google.accompanist.insets.ui.Scaffold
import hg.divineschool.admin.ui.home.DPSBar

@Composable
fun StudentInvoice(
    classID: String,
    className: String,
    scholarNumber: String,
    navController: NavController,
    viewModel: StudentInvoiceViewModel
) {
    Scaffold(
        scaffoldState = rememberScaffoldState(),
        topBar = {
            DPSBar(onBackPressed = { navController.popBackStack() }, className = "Generate Bill")
        }
    ) {
        Column() {

        }
    }

}