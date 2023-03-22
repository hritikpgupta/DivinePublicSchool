package hg.divineschool.admin.ui.home.setting.checkDues

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.insets.ui.Scaffold
import hg.divineschool.admin.ui.home.DPSBar
import hg.divineschool.admin.ui.home.dashboard.registerStudent.DropDown
import hg.divineschool.admin.ui.home.dashboard.registerStudent.dropDownModifier
import hg.divineschool.admin.ui.home.dashboard.registerStudent.genderOptions
import hg.divineschool.admin.ui.theme.lightFont
import hg.divineschool.admin.ui.theme.regularFont

@Composable
fun CheckDue(viewModel: CheckDueViewModel,navController: NavController) {

    val classList = listOf(
        "Play Group",
        "Lower Nursery",
        "Upper Nursery",
        "Class One",
        "Class Two",
        "Class Three",
        "Class Four",
        "Class Five",
        "Class Six",
        "Class Seven",
        "Class Eight"
    )
    val monthList = listOf(
        "January",
        "February",
        "March",
        "April",
        "May",
        "June",
        "July",
        "August",
        "September",
        "October",
        "November",
        "December"
    )

    var monthExpanded by remember { mutableStateOf(false) }
    var month by remember { mutableStateOf(monthList[0]) }
    var classExpanded by remember { mutableStateOf(false) }
    var className by remember { mutableStateOf(classList[0]) }

    Scaffold(
        scaffoldState = rememberScaffoldState(), topBar = {
            DPSBar(onBackPressed = {
                navController.popBackStack()
            }, className = "Check Students Dues")
        }, modifier = Modifier.fillMaxSize()
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Card(
                elevation = 2.dp,
                backgroundColor = Color.LightGray.copy(0.1f),
                shape = RoundedCornerShape(2.dp),
                border = BorderStroke(2.dp, Color.LightGray.copy(0.1f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth()
                    .padding(8.dp)
            ) {
                Column(modifier = Modifier.background(color = Color.LightGray.copy(0.1f))) {
                    Text(
                        text = "Select Class & Month", style = TextStyle(
                            fontFamily = lightFont,
                            fontSize = 30.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        ), modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(2.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    ) {
                        DropDown(
                            lableText = "Select Class",
                            expanded = classExpanded,
                            onExpand = { classExpanded = !classExpanded },
                            onItemClick = {
                                className = it
                                classExpanded = false
                            },
                            onDismiss = { classExpanded = false },
                            values = classList,
                            color = Color.Black,
                            selectedValue = className,
                            modifier = dropDownModifier.weight(0.4f)
                        )
                        DropDown(
                            lableText = "Select Month",
                            expanded = monthExpanded,
                            onExpand = { monthExpanded = !monthExpanded },
                            onItemClick = {
                                month = it
                                monthExpanded = false
                            },
                            onDismiss = { monthExpanded = false },
                            values = monthList,
                            color = Color.Black,
                            selectedValue = month,
                            modifier = dropDownModifier.weight(0.4f)
                        )
                        Button(
                            onClick = { }, modifier = Modifier
                                .padding(8.dp)
                                .weight(0.2f)
                        ) {
                            Text(
                                text = "Search", textAlign = TextAlign.Center, style = TextStyle(
                                    fontFamily = regularFont,
                                    fontSize = 30.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}