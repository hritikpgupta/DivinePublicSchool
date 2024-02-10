package hg.divineschool.admin.ui.home.setting.manageFees

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.google.accompanist.insets.ui.Scaffold
import com.google.firebase.auth.FirebaseAuth
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.models.FeeStructure
import hg.divineschool.admin.ui.home.DPSBar
import hg.divineschool.admin.ui.theme.boldFont
import hg.divineschool.admin.ui.theme.mediumFont
import hg.divineschool.admin.ui.utils.CircularProgress
import hg.divineschool.admin.ui.utils.toast
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ManageFee(navController: NavController, viewModel: ManageFeeViewModel) {
    val feeState = viewModel.priceUpdateFlow.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val bringIntoViewRequester = BringIntoViewRequester()
    val editTextModifier = Modifier
        .padding(horizontal = 10.dp)
        .onFocusEvent {
            if (it.isFocused) {
                coroutineScope.launch { bringIntoViewRequester.bringIntoView() }
            }
        }
    val fee = FeeStructure.FEE_STRUCT

    var playGroup by remember { mutableStateOf(TextFieldValue("${fee.pgTuition}")) }
    var ln by remember { mutableStateOf(TextFieldValue("${fee.lnTuition}")) }
    var un by remember { mutableStateOf(TextFieldValue("${fee.unTuition}")) }
    var classOne by remember { mutableStateOf(TextFieldValue("${fee.classOneTuition}")) }
    var classTwo by remember { mutableStateOf(TextFieldValue("${fee.classTwoTuition}")) }
    var classThree by remember { mutableStateOf(TextFieldValue("${fee.classThreeTuition}")) }
    var classFour by remember { mutableStateOf(TextFieldValue("${fee.classFourTuition}")) }
    var classFive by remember { mutableStateOf(TextFieldValue("${fee.classFiveTuition}")) }
    var classSix by remember { mutableStateOf(TextFieldValue("${fee.classSixTuition}")) }
    var classSeven by remember { mutableStateOf(TextFieldValue("${fee.classSevenTuition}")) }
    var classEight by remember { mutableStateOf(TextFieldValue("${fee.classEightTuition}")) }
    var developmentCharge by remember { mutableStateOf(TextFieldValue("${fee.developmentCharge}")) }
    var annualCharge by remember { mutableStateOf(TextFieldValue("${fee.annualCharge}")) }
    var juniorComputerFee by remember { mutableStateOf(TextFieldValue("${fee.computerFeeJunior}")) }
    var seniorComputerFee by remember { mutableStateOf(TextFieldValue("${fee.computerFeeSenior}")) }
    var examFee by remember { mutableStateOf(TextFieldValue("${fee.examFee}")) }

    var beltPrice by remember { mutableStateOf(TextFieldValue("${fee.beltPrice}")) }
    var diaryPrice by remember { mutableStateOf(TextFieldValue("${fee.diaryFee}")) }
    var feeAndIdCardPrice by remember { mutableStateOf(TextFieldValue("${fee.idAndFeeCardPrice}")) }
    var juniorTiePrice by remember { mutableStateOf(TextFieldValue("${fee.tieFeeJunior}")) }
    var seniorTiePrice by remember { mutableStateOf(TextFieldValue("${fee.tieFeeSenior}")) }
    val context = LocalContext.current

    val isAdmin = FirebaseAuth.getInstance().currentUser?.email.equals("admin@dps.com")

    Scaffold(
        scaffoldState = rememberScaffoldState(), topBar = {
            DPSBar(onBackPressed = {
                navController.popBackStack()
            }, className = "Manage Fees")
        },
        floatingActionButton = {
            if (isAdmin) {
                ExtendedFloatingActionButton(onClick = {
                    viewModel.updatePrice(
                        FeeStructure(
                            pgTuition = playGroup.text.toInt(),
                            lnTuition = ln.text.toInt(),
                            unTuition = un.text.toInt(),
                            classOneTuition = classOne.text.toInt(),
                            classTwoTuition = classTwo.text.toInt(),
                            classThreeTuition = classThree.text.toInt(),
                            classFourTuition = classFour.text.toInt(),
                            classFiveTuition = classFive.text.toInt(),
                            classSixTuition = classSix.text.toInt(),
                            classSevenTuition = classSeven.text.toInt(),
                            classEightTuition = classEight.text.toInt(),
                            developmentCharge = developmentCharge.text.toInt(),
                            annualCharge = annualCharge.text.toInt(),
                            computerFeeJunior = juniorComputerFee.text.toInt(),
                            computerFeeSenior = seniorComputerFee.text.toInt(),
                            examFee = examFee.text.toInt(),
                            beltPrice = beltPrice.text.toInt(),
                            diaryFee = diaryPrice.text.toInt(),
                            idAndFeeCardPrice = feeAndIdCardPrice.text.toInt(),
                            tieFeeJunior = juniorTiePrice.text.toInt(),
                            tieFeeSenior = seniorTiePrice.text.toInt()
                        )
                    )
                },
                    modifier = Modifier.padding(bottom = 70.dp, end = 10.dp),
                    elevation = FloatingActionButtonDefaults.elevation(4.dp),
                    shape = RoundedCornerShape(8.dp),
                    icon = {
                        Icon(
                            Icons.Filled.Save,
                            null,
                            tint = Color.White,
                            modifier = Modifier.requiredSize(30.dp)
                        )
                    },
                    text = {
                        androidx.compose.material3.Text(
                            text = "Save Changes", style = TextStyle(
                                fontFamily = boldFont,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.SemiBold
                            ), color = Color.White
                        )
                    })
            }
        }, modifier = Modifier.fillMaxSize()
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(padding)
                .padding(bottom = 50.dp, start = 4.dp, top = 4.dp, end = 4.dp)
                .background(color = Color.LightGray.copy(0.2f))
        ) {
            Text(
                text = "Tuition Fee", style = TextStyle(
                    fontFamily = mediumFont,
                    fontSize = 30.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                ), modifier = Modifier.padding(start = 8.dp, top = 4.dp)
            )
            Card(
                elevation = 4.dp, modifier = Modifier
                    .padding(16.dp)
                    .background(color = Color.LightGray.copy(0.3f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.LightGray.copy(0.3f))
                ) {
                    Card(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier
                            .wrapContentHeight()
                            .padding(8.dp)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            FeeUnit(
                                modifier = Modifier.weight(1f),
                                editTextModifier = editTextModifier.padding(
                                    top = 10.dp,
                                    bottom = 8.dp
                                ),
                                name = playGroup,
                                textLable = "Play Group",
                                enabled = isAdmin,
                                keyboardType = KeyboardType.Number,
                                onValueChanged = { playGroup = it }
                            )
                            FeeUnit(
                                modifier = Modifier.weight(1f),
                                editTextModifier = editTextModifier.padding(
                                    top = 10.dp,
                                    bottom = 8.dp
                                ),
                                name = ln,
                                textLable = "Lower Nursery",
                                enabled = isAdmin,
                                keyboardType = KeyboardType.Number,
                                onValueChanged = { ln = it }
                            )
                            FeeUnit(
                                modifier = Modifier.weight(1f),
                                editTextModifier = editTextModifier.padding(
                                    top = 10.dp,
                                    bottom = 8.dp
                                ),
                                name = un,
                                textLable = "Upper Nursery",
                                enabled = isAdmin,
                                keyboardType = KeyboardType.Number,
                                onValueChanged = { un = it }
                            )
                            FeeUnit(
                                modifier = Modifier.weight(1f),
                                editTextModifier = editTextModifier.padding(
                                    top = 10.dp,
                                    bottom = 8.dp
                                ),
                                name = classOne,
                                textLable = "Class One",
                                enabled = isAdmin,
                                keyboardType = KeyboardType.Number,
                                onValueChanged = { classOne = it }
                            )

                        }
                    }
                    Card(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier
                            .wrapContentHeight()
                            .padding(8.dp)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            FeeUnit(
                                modifier = Modifier.weight(1f),
                                editTextModifier = editTextModifier.padding(
                                    top = 10.dp,
                                    bottom = 8.dp
                                ),
                                name = classTwo,
                                textLable = "Class Two",
                                enabled = isAdmin,
                                keyboardType = KeyboardType.Number,
                                onValueChanged = { classTwo = it }
                            )
                            FeeUnit(
                                modifier = Modifier.weight(1f),
                                editTextModifier = editTextModifier.padding(
                                    top = 10.dp,
                                    bottom = 8.dp
                                ),
                                name = classThree,
                                textLable = "Class Three",
                                enabled = isAdmin,
                                keyboardType = KeyboardType.Number,
                                onValueChanged = { classThree = it }
                            )
                            FeeUnit(
                                modifier = Modifier.weight(1f),
                                editTextModifier = editTextModifier.padding(
                                    top = 10.dp,
                                    bottom = 8.dp
                                ),
                                name = classFour,
                                textLable = "Class Four",
                                enabled = isAdmin,
                                keyboardType = KeyboardType.Number,
                                onValueChanged = { classFour = it }
                            )
                            FeeUnit(
                                modifier = Modifier.weight(1f),
                                editTextModifier = editTextModifier.padding(
                                    top = 10.dp,
                                    bottom = 8.dp
                                ),
                                name = classFive,
                                textLable = "Class Five",
                                enabled = isAdmin,
                                keyboardType = KeyboardType.Number,
                                onValueChanged = { classFive = it }
                            )

                        }
                    }
                    Card(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier
                            .wrapContentHeight()
                            .padding(8.dp)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            FeeUnit(
                                modifier = Modifier.weight(1f),
                                editTextModifier = editTextModifier.padding(
                                    top = 10.dp,
                                    bottom = 8.dp
                                ),
                                name = classSix,
                                textLable = "Class Six",
                                enabled = isAdmin,
                                keyboardType = KeyboardType.Number,
                                onValueChanged = { classSix = it }
                            )
                            FeeUnit(
                                modifier = Modifier.weight(1f),
                                editTextModifier = editTextModifier.padding(
                                    top = 10.dp,
                                    bottom = 8.dp
                                ),
                                name = classSeven,
                                textLable = "Class Seven",
                                enabled = isAdmin,
                                keyboardType = KeyboardType.Number,
                                onValueChanged = { classSeven = it }
                            )
                            FeeUnit(
                                modifier = Modifier.weight(1f),
                                editTextModifier = editTextModifier.padding(
                                    top = 10.dp,
                                    bottom = 8.dp
                                ),
                                name = classEight,
                                textLable = "Class Eight",
                                enabled = isAdmin,
                                keyboardType = KeyboardType.Number,
                                onValueChanged = { classEight = it }
                            )
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
            Text(
                text = "Other Fee", style = TextStyle(
                    fontFamily = mediumFont,
                    fontSize = 30.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                ), modifier = Modifier.padding(start = 8.dp, top = 4.dp)
            )
            Card(
                elevation = 4.dp, modifier = Modifier
                    .padding(16.dp)
                    .background(color = Color.LightGray.copy(0.3f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.LightGray.copy(0.3f))
                ) {
                    Card(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier
                            .wrapContentHeight()
                            .padding(8.dp)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            FeeUnit(
                                modifier = Modifier.weight(1f),
                                editTextModifier = editTextModifier.padding(
                                    top = 10.dp,
                                    bottom = 8.dp
                                ),
                                name = developmentCharge,
                                textLable = "Development",
                                enabled = isAdmin,
                                keyboardType = KeyboardType.Number,
                                onValueChanged = { developmentCharge = it }
                            )
                            FeeUnit(
                                modifier = Modifier.weight(1f),
                                editTextModifier = editTextModifier.padding(
                                    top = 10.dp,
                                    bottom = 8.dp
                                ),
                                name = annualCharge,
                                textLable = "Annual",
                                enabled = isAdmin,
                                keyboardType = KeyboardType.Number,
                                onValueChanged = { annualCharge = it }
                            )
                            FeeUnit(
                                modifier = Modifier.weight(1f),
                                editTextModifier = editTextModifier.padding(
                                    top = 10.dp,
                                    bottom = 8.dp
                                ),
                                name = examFee,
                                textLable = "Exam",
                                enabled = isAdmin,
                                keyboardType = KeyboardType.Number,
                                onValueChanged = { examFee = it }
                            )
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }

                    Card(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier
                            .wrapContentHeight()
                            .padding(8.dp)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            FeeUnit(
                                modifier = Modifier.weight(1f),
                                editTextModifier = editTextModifier.padding(
                                    top = 10.dp,
                                    bottom = 8.dp
                                ),
                                name = juniorComputerFee,
                                textLable = "Junior Computer",
                                enabled = isAdmin,
                                keyboardType = KeyboardType.Number,
                                onValueChanged = { juniorComputerFee = it }
                            )
                            FeeUnit(
                                modifier = Modifier.weight(1f),
                                editTextModifier = editTextModifier.padding(
                                    top = 10.dp,
                                    bottom = 8.dp
                                ),
                                name = seniorComputerFee,
                                textLable = "Senior Computer",
                                enabled = isAdmin,
                                keyboardType = KeyboardType.Number,
                                onValueChanged = { seniorComputerFee = it }
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
            Text(
                text = "Supplement Price", style = TextStyle(
                    fontFamily = mediumFont,
                    fontSize = 30.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                ), modifier = Modifier.padding(start = 8.dp, top = 4.dp)
            )
            Card(
                elevation = 4.dp, modifier = Modifier
                    .padding(16.dp)
                    .background(color = Color.LightGray.copy(0.3f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.LightGray.copy(0.3f))
                ) {
                    Card(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier
                            .wrapContentHeight()
                            .padding(8.dp)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            FeeUnit(
                                modifier = Modifier.weight(1f),
                                editTextModifier = editTextModifier.padding(
                                    top = 10.dp,
                                    bottom = 8.dp
                                ),
                                name = beltPrice,
                                textLable = "Belt",
                                enabled = isAdmin,
                                keyboardType = KeyboardType.Number,
                                onValueChanged = { beltPrice = it }
                            )
                            FeeUnit(
                                modifier = Modifier.weight(1f),
                                editTextModifier = editTextModifier.padding(
                                    top = 10.dp,
                                    bottom = 8.dp
                                ),
                                name = diaryPrice,
                                textLable = "Diary",
                                enabled = isAdmin,
                                keyboardType = KeyboardType.Number,
                                onValueChanged = { diaryPrice = it }
                            )
                            FeeUnit(
                                modifier = Modifier.weight(1f),
                                editTextModifier = editTextModifier.padding(
                                    top = 10.dp,
                                    bottom = 8.dp
                                ),
                                name = feeAndIdCardPrice,
                                textLable = "ID & Fee Card",
                                enabled = isAdmin,
                                keyboardType = KeyboardType.Number,
                                onValueChanged = { feeAndIdCardPrice = it }
                            )
                            Spacer(modifier = Modifier.weight(1f))

                        }
                    }

                    Card(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier
                            .wrapContentHeight()
                            .padding(8.dp)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            FeeUnit(
                                modifier = Modifier.weight(1f),
                                editTextModifier = editTextModifier.padding(
                                    top = 10.dp,
                                    bottom = 8.dp
                                ),
                                name = juniorTiePrice,
                                textLable = "Junior Tie",
                                enabled = isAdmin,
                                keyboardType = KeyboardType.Number,
                                onValueChanged = { juniorTiePrice = it }
                            )
                            FeeUnit(
                                modifier = Modifier.weight(1f),
                                editTextModifier = editTextModifier.padding(
                                    top = 10.dp,
                                    bottom = 8.dp
                                ),
                                name = seniorTiePrice,
                                textLable = "Senior Tie",
                                enabled = isAdmin,
                                keyboardType = KeyboardType.Number,
                                onValueChanged = { seniorTiePrice = it }
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }

        }
        feeState.value.let {
            when (it) {
                is Resource.Loading -> {
                    CircularProgress()
                }
                is Resource.Failure -> {
                    it.exception.message?.let { it1 -> context.toast(it1) }
                }
                is Resource.Success -> {
                    context.toast("Updated")
                    LaunchedEffect(Unit) {
                        navController.popBackStack()
                    }
                }
                else -> {}
            }
        }
    }
}