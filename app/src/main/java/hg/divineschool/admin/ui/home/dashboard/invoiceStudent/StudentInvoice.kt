package hg.divineschool.admin.ui.home.dashboard.invoiceStudent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
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
import hg.divineschool.admin.data.models.Supplement
import hg.divineschool.admin.data.utils.getBooks
import hg.divineschool.admin.data.utils.getPlaces
import hg.divineschool.admin.data.utils.getSupplement
import hg.divineschool.admin.data.utils.getTuitionFee
import hg.divineschool.admin.ui.home.DPSBar
import hg.divineschool.admin.ui.home.dashboard.registerStudent.FormRow
import hg.divineschool.admin.ui.theme.cardColors
import hg.divineschool.admin.ui.theme.mediumFont
import hg.divineschool.admin.ui.utils.*

@Composable
fun StudentInvoice(
    classID: String,
    className: String,
    scholarNumber: String,
    navController: NavController,
    viewModel: StudentInvoiceViewModel
) {
/*    var currentStudent = navController.previousBackStackEntry?.arguments?.customGetSerializable<Student>("studentObj")
    if (currentStudent == null)
        currentStudent = Student()*/
    val scrollState = rememberScrollState()
    val studentInfoState = viewModel.studentInformation.collectAsState()
    val selectedAccessory = remember {
        mutableStateOf(emptyList<Supplement>())
    }

    val chargeAdmissionFee = remember {
        mutableStateOf(false)
    }
    val tuitionFee = remember {
        mutableStateOf(0)
    }
    val admissionFee = remember {
        mutableStateOf(0)
    }
    val transportFee = remember {
        mutableStateOf(0)
    }
    val supplementFee = remember {
        mutableStateOf(0)
    }
    val bookFee = remember {
        mutableStateOf(0)
    }

    val examinationFee = remember {
        mutableStateOf(0)
    }
    val computerFee = remember {
        mutableStateOf(0)
    }
    val annualFee = remember {
        mutableStateOf(0)
    }


    val context = LocalContext.current
    val classColor = cardColors[classID.toInt()]
    LaunchedEffect(Unit) {
        viewModel.getStudent(classID, scholarNumber)
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
                            .background(color = Color.LightGray.copy(0.2f))
                            .padding(bottom = 65.dp, start = 5.dp, end = 5.dp, top = 55.dp)
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
                                        if (it.result.student.newStudent) {
                                            InvoiceCheckBoxes(
                                                text = "Admission Fee",
                                                classColor,
                                                chargeAdmissionFee.value,
                                                { bool ->
                                                    chargeAdmissionFee.value = bool
                                                    if (chargeAdmissionFee.value) {
                                                        admissionFee.value =
                                                            FeeStructure.FEE_STRUCT.admissionCharge
                                                    } else {
                                                        admissionFee.value = 0
                                                    }
                                                },
                                                modifier = Modifier
                                                    .weight(0.35f)
                                                    .padding(8.dp)
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                        }
                                        Row(modifier = Modifier.weight(0.65f)) {
                                            AccessoryDropdown(
                                                items = FeeStructure.FEE_STRUCT.getSupplement(),
                                                selectedItems = selectedAccessory,
                                                onItemsSelected = { suppList ->
                                                    if (suppList.isNotEmpty()) {
                                                        var sum = 0
                                                        suppList.forEach { sup ->
                                                            sum += sup.price
                                                        }
                                                        supplementFee.value = sum
                                                    } else {
                                                        supplementFee.value = 0
                                                    }
                                                },
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
                                                text = "Select Months", style = TextStyle(
                                                    fontFamily = mediumFont,
                                                    fontSize = 30.sp,
                                                    textAlign = TextAlign.Start
                                                ), modifier = Modifier.weight(1f)
                                            )
                                            Text(
                                                text = "$INR ${tuitionFee.value}",
                                                style = TextStyle(
                                                    fontFamily = mediumFont,
                                                    fontSize = 30.sp,
                                                    textAlign = TextAlign.End
                                                ),
                                                modifier = Modifier.weight(1f)
                                            )
                                        }
                                        MonthSelectList(
                                            it.result.monthFeeList, classColor
                                        ) { monthList ->
                                            if (monthList.isNotEmpty()) {
                                                tuitionFee.value =
                                                    monthList.size * FeeStructure.FEE_STRUCT.getTuitionFee(
                                                        classID
                                                    )
                                                examinationFee.value = monthList.getExamFee()
                                                annualFee.value = monthList.getAnnualFee()
                                                computerFee.value =
                                                    monthList.getComputerFee(classID)

                                            } else {
                                                tuitionFee.value = 0
                                                examinationFee.value = 0
                                                annualFee.value = 0
                                                computerFee.value = 0
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
                                                text = "Select Books", style = TextStyle(
                                                    fontFamily = mediumFont,
                                                    fontSize = 30.sp,
                                                    textAlign = TextAlign.Start
                                                ), modifier = Modifier.weight(1f)
                                            )
                                            Text(
                                                text = "$INR ${bookFee.value}", style = TextStyle(
                                                    fontFamily = mediumFont,
                                                    fontSize = 30.sp,
                                                    textAlign = TextAlign.End
                                                ), modifier = Modifier.weight(1f)
                                            )
                                        }
                                        BookSelectList(
                                            FeeStructure.FEE_STRUCT.getBooks(classID), classColor
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
                            if (it.result.student.transportStudent) {
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
                                                    text = "Select Destination", style = TextStyle(
                                                        fontFamily = mediumFont,
                                                        fontSize = 30.sp,
                                                        textAlign = TextAlign.Start
                                                    ), modifier = Modifier.weight(1f)
                                                )
                                                Text(
                                                    text = "$INR ${transportFee.value}",
                                                    style = TextStyle(
                                                        fontFamily = mediumFont,
                                                        fontSize = 30.sp,
                                                        textAlign = TextAlign.End
                                                    ),
                                                    modifier = Modifier.weight(1f)
                                                )
                                            }
                                            DestinationSelectList(
                                                FeeStructure.FEE_STRUCT.getPlaces(), classColor
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
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                        Divider(
                            color = Color.LightGray, modifier = Modifier
                                .fillMaxHeight()
                                .width(6.dp)
                        )
                        InvoiceSummarySection(
                            classID = classID,
                            student = it.result.student,
                            modifier = Modifier.weight(0.25f),
                            color = classColor,
                            tuitionFee = tuitionFee.value,
                            admissionFee = admissionFee.value,
                            transportFee = transportFee.value,
                            bookFee = bookFee.value,
                            supplementFee = supplementFee.value,
                            examinationFee = examinationFee.value,
                            annualFee = annualFee.value,
                            computerFee = computerFee.value
                        )
                    }
                }
                else -> {}
            }
        }
    }
}









