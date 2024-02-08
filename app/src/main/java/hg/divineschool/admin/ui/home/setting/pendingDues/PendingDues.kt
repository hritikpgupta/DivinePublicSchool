package hg.divineschool.admin.ui.home.setting.pendingDues

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Divider
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.insets.ui.Scaffold
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.ui.home.DPSBar
import hg.divineschool.admin.ui.theme.regularFont
import hg.divineschool.admin.ui.utils.CircularProgress
import hg.divineschool.admin.ui.utils.toast

@Composable
fun PendingDues(viewModel: PendingDuesViewModel, navController: NavController) {

    val pendingInvoiceYears = viewModel.pendingInvoiceListFlow.collectAsState()
    val context = LocalContext.current
    var sessionId by remember { mutableStateOf("") }



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
                    if (it.result.isNotEmpty()) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(padding)
                        ) {
                            Text(
                                text = "Select Session", style = TextStyle(
                                    fontFamily = regularFont,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Medium
                                ), color = Color.DarkGray, modifier = Modifier.padding(
                                    horizontal = 25.dp, vertical = 5.dp
                                )
                            )
                            SessionChipSection(
                                it = it.result,
                                modifier = Modifier.weight(0.1f)
                            ) { id ->
                                sessionId = id
                            }

                            Divider(
                                color = Color.LightGray, modifier = Modifier
                                    .fillMaxWidth()
                                    .requiredHeight(2.dp)
                                    .padding(
                                        horizontal = 25.dp, vertical = 5.dp
                                    )
                            )
                            PendingInvoiceDetailedSection(modifier = Modifier.weight(0.9f)) {
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