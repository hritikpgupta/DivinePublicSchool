package hg.divineschool.admin.ui.home.setting.transactions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.insets.ui.Scaffold
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.models.Transaction
import hg.divineschool.admin.ui.home.DPSBar
import hg.divineschool.admin.ui.theme.regularFont
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
    LaunchedEffect(Unit) {
        viewModel.getAllTransaction(dateRangeState)
    }


    Scaffold(scaffoldState = rememberScaffoldState(), topBar = {
        DPSBar(onBackPressed = {
            navController.popBackStack()
        }, className = "Transactions")
    }) {
        Row(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.LightGray.copy(0.15f))
                    .weight(0.4f)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    DateRangePicker(
                        state = dateRangeState,
                        title = {
                            Text(
                                "Filter Transaction", style = TextStyle(
                                    fontSize = 32.sp,
                                    textAlign = TextAlign.Center,
                                    fontFamily = regularFont
                                ), modifier = Modifier.padding(start = 2.dp)
                            )
                        },
                        headline = {
                            Text(
                                "Start Date - End Date", style = TextStyle(
                                    fontSize = 25.sp,
                                    fontFamily = regularFont,
                                ), modifier = Modifier.padding(bottom = 12.dp, start = 2.dp)
                            )
                        },
                        modifier = Modifier
                            .fillMaxHeight()
                            .requiredWidth(500.dp)
                            .padding(top = 60.dp, start = 0.dp, bottom = 0.dp)
                    )
                    Box(
                        contentAlignment = Alignment.BottomEnd,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 60.dp)
                    ) {
                        Button(
                            onClick = { viewModel.getAllTransaction(dateRangeState) },
                            modifier = Modifier
                                .requiredSize(70.dp)
                                .padding(end = 1.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Search,
                                contentDescription = "",
                                tint = Color.White,
                                modifier = Modifier.requiredSize(40.dp)
                            )
                        }
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.5f)
            ) {

            }
        }
        transactionState.value.let {
            when (it) {
                is Resource.Loading -> {
                    CircularProgress()
                }
                is Resource.Failure -> {
                    it.exception.message?.let { it1 -> context.toast(it1) }
                }
                is Resource.Success -> {
                    if (it.result.isNotEmpty()) {
                        transactions = it.result
                    }
                }
                else -> {}
            }
        }

    }
}