package hg.divineschool.admin.ui.home.setting.manageFees

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.insets.ui.Scaffold
import hg.divineschool.admin.ui.home.DPSBar
import hg.divineschool.admin.ui.theme.mediumFont
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ManageFee(navController: NavController) {
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
    var playGroup by remember { mutableStateOf(TextFieldValue("")) }
    var ln by remember { mutableStateOf(TextFieldValue("")) }
    var un by remember { mutableStateOf(TextFieldValue("")) }
    var classOne by remember { mutableStateOf(TextFieldValue("")) }
    var classTwo by remember { mutableStateOf(TextFieldValue("")) }
    var classThree by remember { mutableStateOf(TextFieldValue("")) }
    var classFour by remember { mutableStateOf(TextFieldValue("")) }
    var classFive by remember { mutableStateOf(TextFieldValue("")) }
    var classSix by remember { mutableStateOf(TextFieldValue("")) }
    var classSeven by remember { mutableStateOf(TextFieldValue("")) }
    var classEight by remember { mutableStateOf(TextFieldValue("")) }



    Scaffold(
        scaffoldState = rememberScaffoldState(), topBar = {
            DPSBar(onBackPressed = {
                navController.popBackStack()
            }, className = "Manage Fees")
        }, modifier = Modifier.fillMaxSize()
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(4.dp)
                .padding(padding)
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
            Card(elevation = 4.dp,modifier = Modifier
                .padding(16.dp)
                .background(color = Color.LightGray.copy(0.3f))) {
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
                                keyboardType = KeyboardType.Number,
                                onValueChanged = { classOne = it }
                            )
                            FeeUnit(
                                modifier = Modifier.weight(1f),
                                editTextModifier = editTextModifier.padding(
                                    top = 10.dp,
                                    bottom = 8.dp
                                ),
                                name = classTwo,
                                textLable = "Class Two",
                                keyboardType = KeyboardType.Number,
                                onValueChanged = { classTwo = it }
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
                                name = classThree,
                                textLable = "Class Three",
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
                                keyboardType = KeyboardType.Number,
                                onValueChanged = { classFive = it }
                            )
                            FeeUnit(
                                modifier = Modifier.weight(1f),
                                editTextModifier = editTextModifier.padding(
                                    top = 10.dp,
                                    bottom = 8.dp
                                ),
                                name = classSix,
                                textLable = "Class Six",
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
                                keyboardType = KeyboardType.Number,
                                onValueChanged = { classSeven = it }
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
                        Row(modifier = Modifier.wrapContentWidth()) {
                            FeeUnit(
                                modifier = Modifier.weight(0.2f),
                                editTextModifier = editTextModifier.padding(
                                    top = 10.dp,
                                    bottom = 8.dp
                                ),
                                name = classEight,
                                textLable = "Class Eight",
                                keyboardType = KeyboardType.Number,
                                onValueChanged = { classEight = it }
                            )
                        }
                    }
                }
            }
            Text(
                text = "Supplement Fee", style = TextStyle(
                    fontFamily = mediumFont,
                    fontSize = 30.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                ), modifier = Modifier.padding(start = 8.dp, top = 4.dp)
            )
        }
    }
}