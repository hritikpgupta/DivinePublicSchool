package hg.divineschool.admin.ui.home.dashboard.invoiceStudent

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import hg.divineschool.admin.data.models.FeeStructure
import hg.divineschool.admin.data.models.Student
import hg.divineschool.admin.data.utils.getBooks
import hg.divineschool.admin.data.utils.getPlaces
import hg.divineschool.admin.data.utils.getTuitionFee
import hg.divineschool.admin.ui.home.DPSBar
import hg.divineschool.admin.ui.home.dashboard.registerStudent.FormRow
import hg.divineschool.admin.ui.theme.cardColors
import hg.divineschool.admin.ui.theme.mediumFont
import hg.divineschool.admin.ui.utils.INR
import hg.divineschool.admin.ui.utils.accessoryList
import hg.divineschool.admin.ui.utils.customGetSerializable
import hg.divineschool.admin.ui.utils.toast

@Composable
fun StudentInvoice(
    classID: String,
    className: String,
    scholarNumber: String,
    navController: NavController,
    viewModel: StudentInvoiceViewModel
) {
    val currentStudent =
        navController.previousBackStackEntry?.arguments?.customGetSerializable<Student>("studentObj")
    val scrollState = rememberScrollState()
    val studentInfoState = viewModel.studentInformation.collectAsState()
    val chargeAdmissionFee = remember {
        mutableStateOf(false)
    }
    val tuitionFee = remember {
        mutableStateOf(0)
    }
    val transportFee = remember {
        mutableStateOf(0)
    }
    val bookFee = remember {
        mutableStateOf(0)
    }
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
                                .verticalScroll(scrollState)
                                .fillMaxSize()
                                .weight(0.75f)
                        ) {
                            FormRow(padding = 12) {
                                Card(
                                    elevation = 8.dp,
                                    modifier = Modifier.padding(horizontal = 10.dp)
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        if (currentStudent?.newStudent == true) {
                                            InvoiceCheckBoxes(
                                                text = "Admission Fee",
                                                classColor, chargeAdmissionFee.value, { bool ->
                                                    chargeAdmissionFee.value = bool
                                                },
                                                modifier = Modifier
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
                                                text = "Select Months",
                                                style = TextStyle(
                                                    fontFamily = mediumFont,
                                                    fontSize = 30.sp,
                                                    textAlign = TextAlign.Start
                                                ),
                                                modifier = Modifier
                                                    .weight(1f)
                                            )
                                            Text(
                                                text = "$INR ${tuitionFee.value}",
                                                style = TextStyle(
                                                    fontFamily = mediumFont,
                                                    fontSize = 30.sp,
                                                    textAlign = TextAlign.End
                                                ),
                                                modifier = Modifier
                                                    .weight(1f)
                                            )
                                        }
                                        MonthSelectList(
                                            it.result.monthFeeList,
                                            classColor
                                        ) { monthList ->
                                            if (monthList.isNotEmpty()) {
                                                tuitionFee.value =
                                                    monthList.size * FeeStructure.FEE_STRUCT.getTuitionFee(
                                                        classID
                                                    )
                                            } else {
                                                tuitionFee.value = 0
                                            }
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
                                            .heightIn(min = 180.dp, max = 270.dp)
                                            .padding(8.dp)
                                    ) {
                                        Row(modifier = Modifier.padding(horizontal = 5.dp)) {
                                            Text(
                                                text = "Select Books",
                                                style = TextStyle(
                                                    fontFamily = mediumFont,
                                                    fontSize = 30.sp,
                                                    textAlign = TextAlign.Start
                                                ),
                                                modifier = Modifier
                                                    .weight(1f)
                                            )
                                            Text(
                                                text = "$INR ${bookFee.value}",
                                                style = TextStyle(
                                                    fontFamily = mediumFont,
                                                    fontSize = 30.sp,
                                                    textAlign = TextAlign.End
                                                ),
                                                modifier = Modifier
                                                    .weight(1f)
                                            )
                                        }
                                        BookSelectList(
                                            FeeStructure.FEE_STRUCT.getBooks(classID),
                                            classColor
                                        ) { bookList ->
                                            if (bookList.isNotEmpty()) {
                                                var sum = 0
                                                bookList.forEach { book ->
                                                    sum += book.bookPrice
                                                }
                                                bookFee.value = sum
                                            } else {
                                                bookFee.value = 0
                                            }

                                        }
                                    }
                                }
                            }
                            if (currentStudent?.transportStudent == true) {
                                FormRow(padding = 12) {
                                    Card(
                                        elevation = 8.dp,
                                        modifier = Modifier.padding(horizontal = 10.dp)
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .heightIn(min = 180.dp, max = 270.dp)
                                                .padding(8.dp)
                                        ) {
                                            Row(modifier = Modifier.padding(horizontal = 5.dp)) {
                                                Text(
                                                    text = "Select Destination",
                                                    style = TextStyle(
                                                        fontFamily = mediumFont,
                                                        fontSize = 30.sp,
                                                        textAlign = TextAlign.Start
                                                    ),
                                                    modifier = Modifier
                                                        .weight(1f)
                                                )
                                                Text(
                                                    text = "$INR ${transportFee.value}",
                                                    style = TextStyle(
                                                        fontFamily = mediumFont,
                                                        fontSize = 30.sp,
                                                        textAlign = TextAlign.End
                                                    ),
                                                    modifier = Modifier
                                                        .weight(1f)
                                                )
                                            }
                                            DestinationSelectList(
                                                FeeStructure.FEE_STRUCT.getPlaces(),
                                                classColor
                                            ) { place ->
                                                if (place != null) {
                                                    transportFee.value = place.placePrice
                                                } else {
                                                    transportFee.value = 0
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        Divider(
                            color = Color.LightGray, modifier = Modifier
                                .fillMaxHeight()
                                .width(6.dp)
                        )
                        InvoiceSummarySection(modifier = Modifier.weight(0.25f))
                    }
                }
                else -> {}
            }
        }
    }
}









