package hg.divineschool.admin.ui.home.dashboard.invoiceStudent

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Text
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
import hg.divineschool.admin.ui.home.DPSBar
import hg.divineschool.admin.ui.home.dashboard.registerStudent.FormRow
import hg.divineschool.admin.ui.theme.cardColors
import hg.divineschool.admin.ui.theme.mediumFont
import hg.divineschool.admin.ui.utils.INR
import hg.divineschool.admin.ui.utils.Log_Tag
import hg.divineschool.admin.ui.utils.accessoryList
import hg.divineschool.admin.ui.utils.toast

@Composable
fun StudentInvoice(
    classID: String,
    className: String,
    scholarNumber: String,
    navController: NavController,
    viewModel: StudentInvoiceViewModel
) {
    val scrollState = rememberScrollState()
    val studentInfoState = viewModel.studentInformation.collectAsState()
    val context = LocalContext.current
    val classColor = cardColors[classID.toInt()]
    LaunchedEffect(Unit) {
        viewModel.getStudent(classID, scholarNumber)
    }
    val selectedAccessory = remember {
        mutableStateOf(emptyList<String>())
    }

    Scaffold(scaffoldState = rememberScaffoldState(), topBar = {
        DPSBar(onBackPressed = { navController.popBackStack() }, className = "Generate Bill")
    }) {
        studentInfoState.value.let {
            when (it) {
                is Resource.Loading -> {
                    Box(
                        contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is Resource.Failure -> {
                    it.exception.message?.let { it1 -> context.toast(it1) }
                }
                is Resource.FailureMessage -> {}
                is Resource.Success -> {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 65.dp, start = 5.dp, end = 5.dp, top = 65.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.Top,
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(0.75f)
                        ) {
                            FormRow(padding = 12) {
                                Card(
                                    elevation = 8.dp,
                                    modifier = Modifier.padding(horizontal = 10.dp)
                                ) {
                                    Row {
                                        if (it.result.student.newStudent){
                                            InvoiceCheckBoxes(
                                                text = "Admission Fee", classColor, modifier = Modifier
                                                    .weight(1f)
                                                    .padding(6.dp)
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                        }
                                        Row(modifier = Modifier.weight(2f)) {
                                            AccessoryDropdown(
                                                items = accessoryList,
                                                selectedItems = selectedAccessory,
                                                color = classColor,
                                                modifier = Modifier.padding(6.dp)
                                            )
                                        }
                                    }
                                }
                            }
                            FormRow(padding = 12) {
                                Card(
                                    elevation = 8.dp,
                                    modifier = Modifier.padding(horizontal = 10.dp)
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .requiredHeight(180.dp)
                                            .padding(8.dp)
                                    ) {
                                        Row(modifier = Modifier.padding(horizontal = 5.dp)) {
                                            Text(
                                                text = "Select Month",
                                                style = TextStyle(
                                                    fontFamily = mediumFont,
                                                    fontSize = 30.sp,
                                                    textAlign = TextAlign.Start
                                                ),
                                                modifier = Modifier
                                                    .weight(1f)
                                            )
                                            Text(
                                                text = "$INR 300",
                                                style = TextStyle(
                                                    fontFamily = mediumFont,
                                                    fontSize = 30.sp,
                                                    textAlign = TextAlign.End
                                                ),
                                                modifier = Modifier
                                                    .weight(1f)
                                            )
                                        }
                                        MonthSelectList(it.result.monthFeeList, classColor) {}
                                    }
                                }
                            }
                        }
                        Divider(color = Color.LightGray, modifier = Modifier
                                .fillMaxHeight()
                                .width(6.dp))
                        InvoiceSummarySection(modifier = Modifier.weight(0.25f))
                    }
                }
                else -> {}
            }
        }
    }
}









