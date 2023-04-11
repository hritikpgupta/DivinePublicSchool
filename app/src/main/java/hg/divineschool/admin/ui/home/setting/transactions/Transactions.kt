package hg.divineschool.admin.ui.home.setting.transactions

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.insets.ui.Scaffold
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.models.Transaction
import hg.divineschool.admin.ui.home.DPSBar
import hg.divineschool.admin.ui.utils.CircularProgress
import hg.divineschool.admin.ui.utils.toast

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Transactions(navController: NavController, viewModel: TransactionsViewModel) {
    val dateRangeState = rememberDateRangePickerState()
    val transactionState = viewModel.transactionListFlow.collectAsState()
    val context = LocalContext.current

    var transactions by remember {
        mutableStateOf(emptyList<Transaction>())
    }


    Scaffold(scaffoldState = rememberScaffoldState(), topBar = {
        DPSBar(onBackPressed = {
            navController.popBackStack()
        }, className = "Transactions")
    }) {
        DateRangePicker(
            state = dateRangeState,
            modifier = Modifier
                .fillMaxHeight()
                .requiredWidth(500.dp)
                .padding(top = 60.dp, start = 0.dp, bottom = 0.dp)
        )


        transactionState.value.let {
            when (it) {
                is Resource.Loading -> {
                    CircularProgress()
                }
                is Resource.Failure -> {
                    it.exception.message?.let { it1 -> context.toast(it1) }
                }
                is Resource.Success -> {
                    if (it.result.isNotEmpty()){
                        transactions = it.result
                    }
                }
                else -> {}
            }
        }

    }
}