package hg.divineschool.admin.ui.home.setting.manageFees

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
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
import hg.divineschool.admin.ui.home.dashboard.registerStudent.FormEditText
import hg.divineschool.admin.ui.theme.mediumFont
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ManageFee(navController: NavController) {
    val coroutineScope = rememberCoroutineScope()
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
                .padding(4.dp)
                .padding(padding)
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
                elevation = 4.dp,
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(8.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Play Group", style = TextStyle(
                                fontFamily = mediumFont,
                                fontSize = 26.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold
                            ), modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                        )
                        FormEditText(textValue = playGroup,
                            text = "Enter Tuition Fee",
                            keyboardType = KeyboardType.Text,
                            color = Color.Black,
                            modifier = editTextModifier.padding(top = 8.dp),
                            isEnabled = true,
                            onValueChanged = { playGroup = it })
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Lower Nursery", style = TextStyle(
                                fontFamily = mediumFont,
                                fontSize = 26.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold
                            ), modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                        )
                        FormEditText(textValue = ln,
                            text = "Enter Tuition Fee",
                            keyboardType = KeyboardType.Text,
                            color = Color.Black,
                            modifier = editTextModifier.padding(top = 8.dp),
                            isEnabled = true,
                            onValueChanged = { ln = it })
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Upper Nursery", style = TextStyle(
                                fontFamily = mediumFont,
                                fontSize = 26.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold
                            ), modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                        )
                        FormEditText(textValue = un,
                            text = "Enter Tuition Fee",
                            keyboardType = KeyboardType.Text,
                            color = Color.Black,
                            modifier = editTextModifier.padding(top = 8.dp),
                            isEnabled = true,
                            onValueChanged = { un = it })
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Class One", style = TextStyle(
                                fontFamily = mediumFont,
                                fontSize = 26.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold
                            ), modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                        )
                        FormEditText(textValue = classOne,
                            text = "Enter Tuition Fee",
                            keyboardType = KeyboardType.Text,
                            color = Color.Black,
                            modifier = editTextModifier.padding(top = 8.dp),
                            isEnabled = true,
                            onValueChanged = { classOne = it })
                    }
                }
            }
        }
    }
}