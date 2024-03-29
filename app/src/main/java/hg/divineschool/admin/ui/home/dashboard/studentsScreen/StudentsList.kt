package hg.divineschool.admin.ui.home.dashboard.studentsScreen

import android.util.Log
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import hg.divineschool.admin.AppScreen
import hg.divineschool.admin.R
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.ui.theme.boldFont
import hg.divineschool.admin.ui.theme.cardColors
import hg.divineschool.admin.ui.theme.mediumFont
import hg.divineschool.admin.ui.theme.regularFont
import hg.divineschool.admin.ui.utils.CircularProgress
import hg.divineschool.admin.ui.utils.Log_Tag
import hg.divineschool.admin.ui.utils.toast

@Composable
fun StudentsList(
    classID: String,
    className: String,
    navController: NavController,
    viewModel: StudentListViewModel
) {
    val studentListFlow = viewModel.studentListFlow.collectAsStateWithLifecycle()
    val searchText = viewModel.searchText.collectAsStateWithLifecycle()
    val students = viewModel.students.collectAsStateWithLifecycle()
    val isSearching = viewModel.isSearching.collectAsStateWithLifecycle()
    val classColor = cardColors[classID.toInt()]
    val context = LocalContext.current
    val searchWidth = remember { mutableFloatStateOf(0.0f) }

    LaunchedEffect(Unit) {
        viewModel.getAllStudents(classID.toLong())
    }

    Scaffold(scaffoldState = rememberScaffoldState(), topBar = {
        TopAppBar(elevation = 4.dp, title = {
            Text(
                text = className, style = TextStyle(
                    fontFamily = boldFont, fontSize = 26.sp, fontWeight = FontWeight.SemiBold
                )
            )
        }, backgroundColor = MaterialTheme.colors.background, navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    Icons.Filled.ArrowBack, null, modifier = Modifier.requiredSize(28.dp)
                )
            }
        }, actions = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = {
                    if (searchWidth.floatValue == 0.4f) {
                        searchWidth.floatValue = 0.0f
                    } else {
                        searchWidth.floatValue = 0.4f
                    }

                }) {
                    Icon(
                        Icons.Filled.Search,
                        null,
                        modifier = Modifier.requiredSize(30.dp),
                        tint = cardColors[classID.toInt()],
                    )
                }
                TextField(value = searchText.value,
                    shape = RoundedCornerShape(2.dp),
                    textStyle = TextStyle(
                        fontFamily = mediumFont, fontSize = 22.sp
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Text,
                        autoCorrect = false,
                        capitalization = KeyboardCapitalization.None,
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = MaterialTheme.colors.background.copy(
                            0.8f
                        ),
                        placeholderColor = MaterialTheme.colors.background,
                        cursorColor = cardColors[classID.toInt()],
                        focusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        disabledPlaceholderColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    onValueChange = viewModel::onSearchTextChange,
                    modifier = Modifier.fillMaxWidth(searchWidth.floatValue),
                    placeholder = {
                        Text(
                            text = "Search Student", style = TextStyle(
                                fontFamily = mediumFont, fontSize = 22.sp
                            )
                        )
                    })
            }
        })
    }, floatingActionButton = {
        ExtendedFloatingActionButton(onClick = {
            searchWidth.floatValue = 0.0f
            if (searchText.value.isNotEmpty()) {
                viewModel.onClearSearchText()
            }
            navController.navigate(AppScreen.StudentScreen.RegisterStudent.route + "/${classID}/$className")
        },
            modifier = Modifier.padding(bottom = 70.dp, end = 10.dp),
            elevation = FloatingActionButtonDefaults.elevation(4.dp),
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
                        CircularProgress(color = classColor)
                    }
                    is Resource.Failure -> {
                        it.exception.message?.let { it1 -> context.toast(it1) }
                    }
                    is Resource.Success -> {
                        /*if (it.result.isNotEmpty()) {
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
                                items(it.result) { studentInfo ->
                                    StudentCard(student = studentInfo)
                                }
                            }
                        }
                        else {
                            Text(
                                text = "No Student Present in $className",
                                style = TextStyle(fontFamily = regularFont, fontSize = 20.sp),
                                textAlign = TextAlign.Justify,
                                color = MaterialTheme.colors.onBackground
                            )
                        }*/
                    }
                    is Resource.FailureMessage -> {}

                    else -> {}
                }
            }
            students.value.let {
                if (it.isNotEmpty()) {
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
                        columns = GridCells.Adaptive(200.dp)
                    ) {
                        items(it) { studentInfo ->
                            StudentCard(student = studentInfo,
                                color = cardColors[classID.toInt()],
                                onViewClick = {
                                    navController.currentBackStackEntry?.arguments?.apply {
                                        putSerializable("studentObj", studentInfo)
                                    }
                                    navController.navigate(AppScreen.StudentScreen.UpdateStudent.route + "/${classID}/$className")
                                    searchWidth.floatValue = 0.0f
                                    if (searchText.value.isNotEmpty()) {
                                        viewModel.onClearSearchText()
                                    }
                                },
                                onInvoiceClick = {
                                    navController.currentBackStackEntry?.arguments?.apply {
                                        putSerializable("studentObj", studentInfo)
                                    }
                                    navController.navigate(AppScreen.StudentScreen.StudentInvoice.route + "/${classID}/$className/${studentInfo.scholarNumber}")
                                    searchWidth.floatValue = 0.0f
                                    if (searchText.value.isNotEmpty()) {
                                        viewModel.onClearSearchText()
                                    }
                                })
                        }
                    }
                }
                else {
                    Box(
                        contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                            ,modifier = Modifier
                                .wrapContentHeight()
                                .wrapContentWidth()
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.student_record),
                                contentDescription = "Student_Record",
                                modifier = Modifier.requiredSize(100.dp)
                            )
                            Spacer(modifier = Modifier.requiredHeight(5.dp))
                            Text(
                                text = "Empty Records",
                                style = TextStyle(fontFamily = regularFont,
                                    fontSize = 34.sp, fontWeight = FontWeight.Medium),
                                color = Color.DarkGray
                            )
                        }
                    }
                }
            }

        }

    }

}