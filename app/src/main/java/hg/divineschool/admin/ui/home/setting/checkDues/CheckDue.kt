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
import hg.divineschool.admin.ui.theme.lightFont
import hg.divineschool.admin.ui.theme.regularFont

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CheckDue(navController: NavController) {
    val classPagerState = rememberPagerState()
    val monthPagerState = rememberPagerState()
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
    val classPosition = remember {
        mutableStateOf(0)
    }
    val monthPosition = remember {
        mutableStateOf(0)
    }
    LaunchedEffect(classPagerState) {
        snapshotFlow { classPagerState.currentPage }.collect { page ->
            classPosition.value = page
        }
    }
    LaunchedEffect(monthPagerState) {
        snapshotFlow { monthPagerState.currentPage }.collect { page ->
            monthPosition.value = page
        }
    }

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
                        text = "Slide Left To Select", style = TextStyle(
                            fontFamily = lightFont,
                            fontSize = 35.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        ), modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        HorizontalPager(
                            pageCount = classList.size,
                            state = classPagerState,
                            modifier = Modifier
                                .weight(0.4f)
                                .background(color = Color.LightGray.copy(0.6f))
                                .padding(8.dp)
                        ) { page ->
                            Text(
                                text = classList[page],
                                textAlign = TextAlign.Center,
                                style = TextStyle(
                                    fontFamily = regularFont,
                                    fontSize = 35.sp,
                                    color = Color.Black,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
                        HorizontalPager(
                            pageCount = monthList.size,
                            state = monthPagerState,
                            modifier = Modifier
                                .weight(0.4f)
                                .background(color = Color.LightGray.copy(0.6f))
                                .padding(8.dp)
                        ) { page ->
                            Text(
                                text = monthList[page],
                                textAlign = TextAlign.Center,
                                style = TextStyle(
                                    fontFamily = regularFont,
                                    fontSize = 35.sp,
                                    color = Color.Black,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
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