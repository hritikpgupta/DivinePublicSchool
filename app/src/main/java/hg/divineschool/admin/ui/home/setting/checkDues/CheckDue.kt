package hg.divineschool.admin.ui.home.setting.checkDues

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.google.accompanist.insets.ui.Scaffold
import hg.divineschool.admin.R
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.ui.home.DPSBar
import hg.divineschool.admin.ui.home.dashboard.registerStudent.DropDown
import hg.divineschool.admin.ui.home.dashboard.registerStudent.dropDownModifier
import hg.divineschool.admin.ui.theme.lightFont
import hg.divineschool.admin.ui.theme.regularFont
import hg.divineschool.admin.ui.utils.CircularProgress
import hg.divineschool.admin.ui.utils.classNames
import hg.divineschool.admin.ui.utils.convertClassNameToPath
import hg.divineschool.admin.ui.utils.monthList
import hg.divineschool.admin.ui.utils.toast

@Composable
fun CheckDue(viewModel: CheckDueViewModel, navController: NavController) {


    val studentListFlow = viewModel.studentListFlow.collectAsStateWithLifecycle()
    var monthExpanded by remember { mutableStateOf(false) }
    var month by remember { mutableStateOf(monthList[0]) }
    var classExpanded by remember { mutableStateOf(false) }
    var className by remember { mutableStateOf(classNames[0]) }
    val context = LocalContext.current

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
                            values = classNames,
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
                            onClick = {
                                viewModel.getAllDueStudents(
                                    className.convertClassNameToPath(), month
                                )
                            }, modifier = Modifier
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
            studentListFlow.value.let {
                when (it) {
                    is Resource.Loading -> {
                        CircularProgress()
                    }
                    is Resource.Failure -> {
                        it.exception.message?.let { it1 -> context.toast(it1) }
                    }
                    is Resource.Success -> {
                        if (it.result.isNotEmpty()) {
                            LazyVerticalGrid(
                                verticalArrangement = Arrangement.spacedBy(18.dp),
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                userScrollEnabled = true,
                                contentPadding = PaddingValues(
                                    top = 15.dp, start = 10.dp, end = 10.dp, bottom = 80.dp
                                ),
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color = MaterialTheme.colors.background.copy(0.6f)),
                                columns = GridCells.Adaptive(280.dp)
                            ) {
                                items(it.result) { studentDue ->
                                    Card(
                                        backgroundColor = Color.LightGray.copy(0.4f),
                                        shape = RoundedCornerShape(4.dp),
                                        elevation = 2.dp,
                                        border = BorderStroke(4.dp, Color.Black.copy(0.4f)),
                                        modifier = Modifier
                                            .requiredWidth(280.dp)
                                            .requiredHeight(130.dp)
                                    ) {
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.SpaceEvenly,
                                            modifier = Modifier.padding(start = 4.dp)
                                        ) {
                                            Text(
                                                text = studentDue.name,
                                                fontSize = 28.sp,
                                                style = TextStyle(
                                                    fontFamily = regularFont,
                                                    fontWeight = FontWeight.Bold
                                                ),
                                                maxLines = 1,
                                                softWrap = true,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                            Text(
                                                text = "S/O ${studentDue.guardianName}",
                                                fontSize = 26.sp,
                                                style = TextStyle(
                                                    fontFamily = regularFont,
                                                    fontWeight = FontWeight.Bold
                                                ),
                                                maxLines = 1,
                                                softWrap = true,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                            Text(
                                                text = "Roll Number ${studentDue.rollNumber}",
                                                fontSize = 26.sp,
                                                style = TextStyle(
                                                    fontFamily = regularFont,
                                                    fontWeight = FontWeight.Bold
                                                ),
                                                maxLines = 1,
                                                softWrap = true,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        }
                                    }
                                }
                            }
                        } else {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                        .wrapContentHeight()
                                        .wrapContentWidth()
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.student_record),
                                        contentDescription = "Student_Record",
                                        modifier = Modifier.requiredSize(100.dp)
                                    )
                                    Spacer(modifier = Modifier.requiredHeight(5.dp))
                                    androidx.compose.material3.Text(
                                        text = "Empty Records", style = TextStyle(
                                            fontFamily = regularFont,
                                            fontSize = 34.sp,
                                            fontWeight = FontWeight.Medium
                                        ), color = Color.DarkGray
                                    )
                                }
                            }
                        }
                    }
                    else -> {}
                }
            }
        }
    }
}