package hg.divineschool.admin.ui.home.setting.checkDues

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
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
import hg.divineschool.admin.ui.theme.regularFont

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CheckDue(navController: NavController) {
    val pagerState1 = rememberPagerState()
    val pagerState2 = rememberPagerState()
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
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                HorizontalPager(
                    pageCount = classList.size,
                    state = pagerState1,
                    modifier = Modifier
                        .weight(0.4f)
                        .background(color = Color.LightGray.copy(0.7f))
                        .padding(8.dp)
                ) { page ->
                    Text(
                        text = classList[page], textAlign = TextAlign.Center, style = TextStyle(
                            fontFamily = regularFont,
                            fontSize = 35.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
                HorizontalPager(
                    pageCount = monthList.size,
                    state = pagerState2,
                    modifier = Modifier
                        .weight(0.4f)
                        .background(color = Color.LightGray.copy(0.7f))
                        .padding(8.dp)
                ) { page ->
                    Text(
                        text = monthList[page], textAlign = TextAlign.Center, style = TextStyle(
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
                    Icon(
                        Icons.Filled.Search,
                        null,
                        modifier = Modifier.requiredSize(30.dp),
                    )
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