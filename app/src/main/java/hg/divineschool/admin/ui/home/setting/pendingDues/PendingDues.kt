package hg.divineschool.admin.ui.home.setting.pendingDues

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Divider
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.google.accompanist.insets.ui.Scaffold
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.models.PendingInvoice
import hg.divineschool.admin.ui.home.DPSBar
import hg.divineschool.admin.ui.theme.regularFont
import hg.divineschool.admin.ui.utils.CircularProgress
import hg.divineschool.admin.ui.utils.toast
import kotlinx.coroutines.launch

@Composable
fun PendingDues(viewModel: PendingDuesViewModel, navController: NavController) {

    val pendingInvoiceYears = viewModel.pendingInvoiceListFlow.collectAsStateWithLifecycle()
    val pendingInvoice = viewModel.pendingInvoiceFlow.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var sessionId by remember { mutableStateOf("") }
    var pendingInvoiceList by remember { mutableStateOf<List<PendingInvoice>>(emptyList()) }

    Scaffold(
        scaffoldState = rememberScaffoldState(), topBar = {
            DPSBar(onBackPressed = {
                navController.popBackStack()
            }, className = "Pending Dues")
        }, modifier = Modifier.fillMaxSize()
    ) { padding ->

        pendingInvoice.value.let {
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
                    if (it.result.isNotEmpty()) {
                        it.result.let { it1 -> pendingInvoiceList = it1 }
                    }
                    else {
                        Box(
                            contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .wrapContentWidth()
                            ) {
                                Text(
                                    text = "No Invoices Found", style = TextStyle(
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
                    if (it.result.isNotEmpty()) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(padding)
                                .padding(horizontal = 5.dp)
                        ) {
                            Text(
                                text = "Select Session", style = TextStyle(
                                    fontFamily = regularFont,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.ExtraBold
                                ), color = Color.DarkGray, modifier = Modifier.padding(
                                    horizontal = 25.dp, vertical = 5.dp
                                )
                            )
                            SessionChipSection(
                                it = it.result, modifier = Modifier.weight(0.1f)
                            ) { id ->
                                pendingInvoiceList = emptyList()
                                sessionId = id
                                scope.launch {
                                    viewModel.getPendingInvoiceForYear(id)
                                }
                            }
                            Divider(
                                color = Color.LightGray,
                                thickness = 4.dp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        horizontal = 5.dp, vertical = 3.dp
                                    )
                            )

                            if (pendingInvoiceList.isNotEmpty()) {
                                PendingInvoiceDetailedSection(
                                    pendingInvoiceList, modifier = Modifier
                                        .weight(0.9f)
                                        .padding(
                                            horizontal = 8.dp, vertical = 4.dp
                                        ), { remark, invoiceId, remarkList ->
                                        scope.launch {
                                            pendingInvoiceList = emptyList()
                                            viewModel.addRemark(
                                                remark, invoiceId, sessionId, remarkList
                                            )
                                        }
                                    }
                                ) {
                                    scope.launch {
                                        viewModel.settleInvoice(it, sessionId)
                                    }
                                }
                            }
                        }
                    }
                    else {
                        Box(
                            contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .wrapContentWidth()
                            ) {
                                Text(
                                    text = "No Pending Dues Found", style = TextStyle(
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

    }
}