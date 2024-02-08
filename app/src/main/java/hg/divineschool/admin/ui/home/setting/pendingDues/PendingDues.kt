package hg.divineschool.admin.ui.home.setting.pendingDues

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.google.accompanist.insets.ui.Scaffold
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.ui.home.DPSBar
import hg.divineschool.admin.ui.utils.CircularProgress
import hg.divineschool.admin.ui.utils.toast

@Composable
fun PendingDues(viewModel: PendingDuesViewModel, navController: NavController) {

    val pendingInvoiceYears = viewModel.pendingInvoiceListFlow.collectAsState()
    val context = LocalContext.current


    Scaffold(
        scaffoldState = rememberScaffoldState(), topBar = {
            DPSBar(onBackPressed = {
                navController.popBackStack()
            }, className = "Pending Dues")
        }, modifier = Modifier.fillMaxSize()
    ) { padding ->

        pendingInvoiceYears.value.let {
            when (it) {
                is Resource.Loading -> {
                    CircularProgress()
                }
                is Resource.Failure -> {
                    it.exception.message?.let { it1 -> context.toast(it1) }
                }
                is Resource.FailureMessage -> {
                    it.message.let { it1 -> context.toast(it1) }
                }
                is Resource.Success -> {
                    if(it.result.isNotEmpty()){
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(padding)
                        ) {
                            Row(modifier = Modifier.weight(0.1f)) {

                            }
                            Row(modifier = Modifier.weight(0.9f)) {

                            }
                        }
                    }
                }
                else -> {}
            }
        }

    }
}