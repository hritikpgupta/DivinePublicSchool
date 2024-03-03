package hg.divineschool.admin.ui.home.setting.transactions

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import hg.divineschool.admin.R
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.models.Transaction
import hg.divineschool.admin.ui.home.DPSBar
import hg.divineschool.admin.ui.home.dashboard.invoiceWebView.InvoiceOverview
import hg.divineschool.admin.ui.theme.regularFont
import hg.divineschool.admin.ui.utils.CircularProgress
import hg.divineschool.admin.ui.utils.toast

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Transactions(navController: NavController, viewModel: TransactionsViewModel) {
    val fromDateState = rememberDatePickerState(initialDisplayMode = DisplayMode.Input)
    val toDateState = rememberDatePickerState(initialDisplayMode = DisplayMode.Input)

    val transactionState = viewModel.transactionListFlow.collectAsStateWithLifecycle()
    val invoiceState = viewModel.invoiceFlow.collectAsStateWithLifecycle()
    val context = LocalContext.current

    var transactions by remember {
        mutableStateOf(emptyList<Transaction>())
    }
    LaunchedEffect(Unit) {
        viewModel.getAllTransaction(fromDateState, toDateState)
    }

    val startInvoiceOverviewScreen =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {}

    Scaffold(scaffoldState = rememberScaffoldState(), topBar = {
        DPSBar(onBackPressed = {
            navController.popBackStack()
        }, className = "Transactions")
    }) { padding ->
        Column(
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.LightGray.copy(0.15f))
            ) {
                Text(
                    text = "From",
                    style = TextStyle(fontFamily = regularFont, fontSize = 28.sp),
                    modifier = Modifier
                        .padding(start = 16.dp, bottom = 8.dp)
                )
                DatePicker(
                    state = fromDateState,
                    showModeToggle = false,
                    title = null,
                    headline = null,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .width((LocalConfiguration.current.screenWidthDp * 0.25).dp)
                )
                Text(
                    text = "To",
                    style = TextStyle(fontFamily = regularFont, fontSize = 28.sp),
                    modifier = Modifier
                        .padding(start = 4.dp, bottom = 8.dp)
                )
                DatePicker(
                    state = toDateState,
                    showModeToggle = false,
                    title = null,
                    headline = null,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .width((LocalConfiguration.current.screenWidthDp * 0.25).dp)
                )
                Button(
                    onClick = {
                        viewModel.getAllTransaction(fromDateState, toDateState)
                    },
                    modifier = Modifier
                        .padding(bottom = 8.dp, end = 8.dp)
                ) {
                    androidx.compose.material.Text(
                        text = "Search", textAlign = TextAlign.Center, style = TextStyle(
                            fontFamily = regularFont,
                            fontSize = 30.sp,
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
                Spacer(modifier = Modifier.weight(0.4f))
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
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
                                LazyVerticalGrid(
                                    verticalArrangement = Arrangement.spacedBy(18.dp),
                                    userScrollEnabled = true,
                                    contentPadding = PaddingValues(
                                        top = 4.dp, start = 4.dp, end = 4.dp, bottom = 80.dp
                                    ),
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(color = MaterialTheme.colors.background.copy(0.6f)),
                                    columns = GridCells.Adaptive(280.dp)
                                ) {
                                    items(transactions) {
                                        TransactionCard(transaction = it) { tran ->
                                            viewModel.getInvoice(tran)
                                        }
                                    }
                                }
                            } else {
                                Box(
                                    contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier
                                            .wrapContentHeight()
                                            .wrapContentWidth()
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.no_transaction),
                                            contentDescription = "Student_Record",
                                            modifier = Modifier.requiredSize(100.dp)
                                        )
                                        Spacer(modifier = Modifier.requiredHeight(5.dp))
                                        Text(
                                            text = "No Transaction", style = TextStyle(
                                                fontFamily = regularFont,
                                                fontSize = 34.sp,
                                                fontWeight = FontWeight.Medium
                                            ), color = Color.DarkGray
                                        )
                                    }
                                }
                            }
                        }

                        else -> {}
                    }
                }
                invoiceState.value.let {
                    when (it) {
                        is Resource.Loading -> {
                            CircularProgress()
                        }

                        is Resource.Failure -> {
                            it.exception.message?.let { it1 -> context.toast(it1) }
                        }

                        is Resource.Success -> {
                            LaunchedEffect(Unit) {
                                val intent = Intent(
                                    context, InvoiceOverview::class.java
                                )
                                intent.putExtra("invoiceObject", it.result)
                                startInvoiceOverviewScreen.launch(intent)
                            }
                        }

                        else -> {}
                    }
                }
            }
        }
    }
}