package hg.divineschool.admin.ui.home.dashboard.invoiveOverview

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import hg.divineschool.admin.data.utils.DataConstants
import hg.divineschool.admin.ui.home.DPSBar
import hg.divineschool.admin.ui.utils.Log_Tag

@Composable
fun InvoiceOverview(navController: NavController) {

    Scaffold(scaffoldState = rememberScaffoldState(),
        topBar = {
            DPSBar(onBackPressed = {
                navController.popBackStack()
            }, className = DataConstants.currentInvoice!!.invoiceNumber)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.background)
                .padding(it)
        ) {
            Log.i(Log_Tag, DataConstants.currentInvoice.toString())
        }
    }

}