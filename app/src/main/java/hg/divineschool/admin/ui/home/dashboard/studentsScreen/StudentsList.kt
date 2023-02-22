package hg.divineschool.admin.ui.home.dashboard.studentsScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import hg.divineschool.admin.AppScreen
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.ui.home.DPSBar
import hg.divineschool.admin.ui.theme.boldFont
import hg.divineschool.admin.ui.theme.cardColors
import hg.divineschool.admin.ui.theme.regularFont
import hg.divineschool.admin.ui.utils.toast
import kotlin.random.Random

@Composable
fun StudentsList(
    classID: String,
    className: String,
    navController: NavController,
    viewModel: StudentListViewModel
) {
    val studentListFlow = viewModel.studentListFlow.collectAsState()
    val context = LocalContext.current
/*    val keyID = remember { mutableStateOf(24) }
    navController.currentBackStackEntry
        ?.savedStateHandle?.getStateFlow<Boolean?>("studentAdded", false)?.collectAsState()?.value.let {
            if (it == true){
               keyID.value += Random(200).nextInt()
            }
        }*/

    LaunchedEffect(key1 = classID) {
        viewModel.getAllStudents(classID.toLong())
    }

    Scaffold(scaffoldState = rememberScaffoldState(), topBar = {
        DPSBar(onBackPressed = {
            navController.popBackStack()
        }, className = className)
    }, floatingActionButton = {
        ExtendedFloatingActionButton(onClick = {
            navController.navigate(AppScreen.StudentScreen.RegisterStudent.route + "/${classID}/$className") {
                launchSingleTop = true
            }

        },
            modifier = Modifier.padding(bottom = 70.dp, end = 10.dp),
            elevation = FloatingActionButtonDefaults.elevation(8.dp),
            backgroundColor = cardColors[classID.toInt()],
            shape = RoundedCornerShape(8.dp),
            icon = {
                Icon(
                    Icons.Filled.Add,
                    null,
                    tint = Color.White,
                    modifier = Modifier.requiredSize(30.dp)
                )
            },
            text = {
                Text(
                    text = "Add Student", style = TextStyle(
                        fontFamily = boldFont,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold
                    ), color = Color.White
                )
            })
    }, floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(color = MaterialTheme.colors.background.copy(0.8f))
        ) {
            studentListFlow.value.let {
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
                    is Resource.Success -> {
                        if (it.result.isNotEmpty()) {
                            LazyVerticalGrid(
                                verticalArrangement = Arrangement.spacedBy(18.dp),
                                userScrollEnabled = true,
                                contentPadding = PaddingValues(
                                    top = 15.dp, start = 8.dp, end = 8.dp, bottom = 80.dp
                                ),
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color = MaterialTheme.colors.background.copy(0.6f)),
                                columns = GridCells.Adaptive(280.dp)
                            ) {
                                items(it.result) { studentInfo ->
                                    StudentCard(student = studentInfo)
                                }
                            }
                        } else {
                            Text(
                                text = "No Student Present in $className",
                                style = TextStyle(fontFamily = regularFont, fontSize = 20.sp),
                                textAlign = TextAlign.Justify,
                                color = MaterialTheme.colors.onBackground
                            )
                        }
                    }
                    else -> {}
                }
            }
        }
    }

}