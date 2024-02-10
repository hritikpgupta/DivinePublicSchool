package hg.divineschool.admin.ui.home.dashboard.invoiceStudent

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.google.accompanist.insets.ui.Scaffold
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.models.Book
import hg.divineschool.admin.data.models.FeeStructure
import hg.divineschool.admin.data.models.MonthFee
import hg.divineschool.admin.data.models.Supplement
import hg.divineschool.admin.data.utils.getBooks
import hg.divineschool.admin.data.utils.getPlaces
import hg.divineschool.admin.data.utils.getSupplement
import hg.divineschool.admin.data.utils.getTuitionFee
import hg.divineschool.admin.ui.home.DPSBarWithAction
import hg.divineschool.admin.ui.home.dashboard.invoiceWebView.InvoiceOverview
import hg.divineschool.admin.ui.home.dashboard.registerStudent.FormRow
import hg.divineschool.admin.ui.theme.boldFont
import hg.divineschool.admin.ui.theme.cardColors
import hg.divineschool.admin.ui.theme.mediumFont
import hg.divineschool.admin.ui.theme.regularFont
import hg.divineschool.admin.ui.utils.CircularProgress
import hg.divineschool.admin.ui.utils.INR
import hg.divineschool.admin.ui.utils.getAnnualFee
import hg.divineschool.admin.ui.utils.getComputerFee
import hg.divineschool.admin.ui.utils.getExamFee
import hg.divineschool.admin.ui.utils.getFormattedBookString
import hg.divineschool.admin.ui.utils.getFormattedMonthString
import hg.divineschool.admin.ui.utils.getFormattedSupplementString
import hg.divineschool.admin.ui.utils.toast
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun StudentInvoice(
    classID: String,
    className: String,
    scholarNumber: String,
    navController: NavController,
    viewModel: StudentInvoiceViewModel
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val scaffoldState = rememberScaffoldState()
    val studentInfoState = viewModel.studentInformation.collectAsStateWithLifecycle()
    val invoiceState = viewModel.saveInvoice.collectAsStateWithLifecycle()
    val studentInvoices = viewModel.studentInvoices.collectAsStateWithLifecycle()

    val selectedSupplement = remember {
        mutableStateOf(emptyList<Supplement>())
    }
    val selectedBooks = remember {
        mutableStateOf(emptyList<Book>())
    }
    val selectedMonthFee = remember {
        mutableStateOf(emptyList<MonthFee>())
    }

    val chargeDevelopmentFee = remember {
        mutableStateOf(false)
    }
    val tuitionFee = remember {
        mutableStateOf(0)
    }
    val developmentFee = remember {
        mutableStateOf(0)
    }
    val transportFee = remember {
        mutableStateOf(0)
    }
    val transportPlace = remember {
        mutableStateOf("")
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
    val defaultNumberOfMonthsForDestination = remember {
        mutableStateOf(1)
    }

    val classColor = cardColors[classID.toInt()]
    LaunchedEffect(Unit) {
        viewModel.getStudent(classID, scholarNumber)
        viewModel.getAllInvoices(classID, scholarNumber)
    }
    val startInvoiceScreen =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            scope.launch {
                scaffoldState.drawerState.close()
            }
        }

    Scaffold(
        scaffoldState = scaffoldState,
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        topBar = {
            DPSBarWithAction(onBackPressed = { navController.popBackStack() }, onActionPressed = {
                scope.launch {
                    scaffoldState.drawerState.open()
                }
            }, className = "Generate Bill"
            )
        },
        drawerShape = customShape(),
        drawerContent = {
            Column(
                modifier = Modifier
                    .padding(bottom = 50.dp, start = 0.dp, top = 12.dp, end = 0.dp)
                    .fillMaxSize()
            ) {
                Text(
                    text = "Previous Invoices",
                    style = TextStyle(
                        fontSize = 30.sp, fontFamily = boldFont, fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 4.dp)
                )
                Divider(thickness = 4.dp, color = Color.LightGray)
                studentInvoices.value.let {
                    when (it) {
                        is Resource.Loading -> {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                        is Resource.Failure -> {
                            it.exception.message?.let { it1 -> context.toast(it1) }
                        }
                        is Resource.FailureMessage -> {}
                        is Resource.Success -> {
                            if (it.result.isNotEmpty()) {
                                LazyColumn(modifier = Modifier.fillMaxSize()) {
                                    items(it.result) { item ->
                                        Card(
                                            elevation = 1.dp,
                                            backgroundColor = Color.LightGray.copy(0.2f),
                                            onClick = {
                                                val intent = Intent(
                                                    context, InvoiceOverview::class.java
                                                )
                                                intent.putExtra("invoiceObject", item)
                                                startInvoiceScreen.launch(intent)
                                            },
                                            modifier = Modifier
                                                .padding(12.dp)
                                                .requiredHeight(75.dp)
                                                .requiredWidth(320.dp)
                                        ) {
                                            Column(
                                                verticalArrangement = Arrangement.Center,
                                                horizontalAlignment = Alignment.CenterHorizontally
                                            ) {
                                                Text(
                                                    text = "$INR ${item.total}",
                                                    style = TextStyle(
                                                        fontSize = 26.sp, fontFamily = regularFont
                                                    ),
                                                    maxLines = 1,
                                                    softWrap = true,
                                                    overflow = TextOverflow.Ellipsis,
                                                    textAlign = TextAlign.Center,
                                                    color = Color.Black
                                                )
                                                Text(
                                                    text = "${item.date} at ${item.time}",
                                                    style = TextStyle(
                                                        fontSize = 26.sp, fontFamily = regularFont
                                                    ),
                                                    maxLines = 1,
                                                    softWrap = true,
                                                    overflow = TextOverflow.Ellipsis,
                                                    textAlign = TextAlign.Center,
                                                    color = Color.Black
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                            else {
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .requiredWidth(320.dp)
                                        .padding(
                                            start = 2.dp, end = 0.dp, top = 0.dp, bottom = 0.dp
                                        )
                                ) {
                                    Text(
                                        text = "No Invoice Available",
                                        style = TextStyle(
                                            fontSize = 30.sp,
                                            fontFamily = regularFont,
                                        ),
                                        textAlign = TextAlign.Center,
                                        color = Color.Black.copy(0.7f)
                                    )
                                }
                            }
                        }
                        else -> {}
                    }
                }
            }
        },
    ) {
        studentInfoState.value.let {
            when (it) {
                is Resource.Loading -> {
                    CircularProgress(color = classColor)
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
                                    modifier = Modifier.padding(horizontal = 8.dp)
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        if (it.result.student.newStudent) {
                                            InvoiceCheckBoxes(
                                                text = "Development Fee",
                                                classColor,
                                                chargeDevelopmentFee.value,
                                                { bool ->
                                                    chargeDevelopmentFee.value = bool
                                                    if (chargeDevelopmentFee.value) {
                                                        developmentFee.value =
                                                            FeeStructure.FEE_STRUCT.developmentCharge
                                                    } else {
                                                        developmentFee.value = 0
                                                    }
                                                },
                                                modifier = Modifier
                                                    .weight(0.40f)
                                                    .padding(8.dp)
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                        }
                                        Row(modifier = Modifier.weight(0.60f)) {
                                            AccessoryDropdown(
                                                items = FeeStructure.FEE_STRUCT.getSupplement(),
                                                selectedItems = selectedSupplement,
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
                                                defaultNumberOfMonthsForDestination.value = monthList.size
                                                selectedMonthFee.value = monthList
                                                tuitionFee.value =monthList.size * FeeStructure.FEE_STRUCT.getTuitionFee(classID)
                                                examinationFee.value = monthList.getExamFee()
                                                annualFee.value = monthList.getAnnualFee(it.result.student.newStudent)
                                                computerFee.value =
                                                    monthList.getComputerFee(classID)
                                            } else {
                                                tuitionFee.value = 0
                                                examinationFee.value = 0
                                                annualFee.value = 0
                                                computerFee.value = 0
                                                selectedMonthFee.value = emptyList()
                                                defaultNumberOfMonthsForDestination.value = 1
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
                                                selectedBooks.value = bookList
                                                var sum = 0
                                                bookList.forEach { book ->
                                                    sum += book.bookPrice
                                                }
                                                bookFee.value = sum
                                            } else {
                                                bookFee.value = 0
                                                selectedBooks.value = emptyList()
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
                                                    transportPlace.value = place.placeName
                                                } else {
                                                    transportFee.value = 0
                                                    transportPlace.value = ""
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
                            tuitionFee = tuitionFee.value.toLong(),
                            defaultNumberOfMonthsForDestination = defaultNumberOfMonthsForDestination.value,
                            developmentFee = developmentFee.value.toLong(),
                            transportFee = transportFee.value.toLong(),
                            bookFee = bookFee.value.toLong(),
                            supplementFee = supplementFee.value.toLong(),
                            examinationFee = examinationFee.value.toLong(),
                            annualFee = annualFee.value.toLong(),
                            computerFee = computerFee.value.toLong()
                        ) { invoice ->
                            invoice.placeName = transportPlace.value
                            if (selectedSupplement.value.isNotEmpty()) {
                                invoice.supplementsList =
                                    selectedSupplement.value.getFormattedSupplementString()
                            }
                            if (selectedBooks.value.isNotEmpty()) {
                                invoice.bookList = selectedBooks.value.getFormattedBookString()
                            }
                            if (selectedMonthFee.value.isNotEmpty()) {
                                invoice.tuitionFeeMonthList =
                                    selectedMonthFee.value.getFormattedMonthString()
                            }
                            viewModel.saveInvoice(classID, scholarNumber, invoice)
                        }
                    }
                }
                else -> {}
            }
        }
        invoiceState.value.let {
            when (it) {
                is Resource.Loading -> {
                    CircularProgress(color = classColor)
                }
                is Resource.Failure -> {
                    it.exception.message?.let { it1 -> context.toast(it1) }
                }
                is Resource.FailureMessage -> {}
                is Resource.Success -> {
                    LaunchedEffect(Unit) {
                        val intent = Intent(
                            context, InvoiceOverview::class.java
                        )
                        intent.putExtra("invoiceObject", it.result)
                        startInvoiceScreen.launch(intent)
                        tuitionFee.value = 0
                        developmentFee.value = 0
                        transportFee.value = 0
                        bookFee.value = 0
                        supplementFee.value = 0
                        examinationFee.value = 0
                        annualFee.value = 0
                        computerFee.value = 0
                        selectedSupplement.value = emptyList()
                        selectedBooks.value = emptyList()
                        selectedMonthFee.value = emptyList()
                        transportPlace.value = ""
                        transportFee.value = 0
                        viewModel.getStudent(classID, scholarNumber)
                        viewModel.getAllInvoices(classID, scholarNumber)
                    }

                }
                else -> {}
            }
        }
    }
}

fun customShape() = object : Shape {
    override fun createOutline(
        size: Size, layoutDirection: LayoutDirection, density: Density
    ): Outline {
        return Outline.Rectangle(Rect(0f, 0f, 700f , size.height ))
    }
}










