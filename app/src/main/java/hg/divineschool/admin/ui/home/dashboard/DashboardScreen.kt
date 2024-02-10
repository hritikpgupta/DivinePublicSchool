package hg.divineschool.admin.ui.home.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import hg.divineschool.admin.AppScreen
import hg.divineschool.admin.BottomNavItem
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.ui.home.DPSAppBar
import hg.divineschool.admin.ui.theme.boldFont
import hg.divineschool.admin.ui.theme.cardColors
import hg.divineschool.admin.ui.theme.mediumFont
import hg.divineschool.admin.ui.theme.regularFont
import hg.divineschool.admin.ui.utils.CircularProgress
import hg.divineschool.admin.ui.utils.toast

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(viewModel: DashboardViewModel, navController: NavController) {

    val classListFlow = viewModel.classListFlow.collectAsStateWithLifecycle()
    val nameUpdateFlow = viewModel.nameUpdateFlow.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val showDialog = remember { mutableStateOf(false) }
    var classTeacherText by remember { mutableStateOf("") }
    var dialogColor by remember { mutableStateOf(Color.White) }
    var selectedClassID by remember { mutableLongStateOf(0L) }

    Scaffold(topBar = { DPSAppBar() },
        floatingActionButton = {
        if (FirebaseAuth.getInstance().currentUser?.email.equals("hgupta@dps.com")) {
            ExtendedFloatingActionButton(onClick = {
                navController.navigate(BottomNavItem.AdminSettings.route)
            },
                modifier = Modifier.padding(bottom = 70.dp, end = 10.dp),
                elevation = FloatingActionButtonDefaults.elevation(4.dp),
                shape = RoundedCornerShape(8.dp),
                icon = {
                    Icon(
                        Icons.Filled.Settings,
                        null,
                        tint = Color.White,
                        modifier = Modifier.requiredSize(30.dp)
                    )
                },
                text = {
                    Text(
                        text = "Admin", style = TextStyle(
                            fontFamily = boldFont,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.SemiBold
                        ), color = Color.White
                    )
                })
        }
    }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(color = MaterialTheme.colors.background.copy(0.8f))
                .wrapContentSize(Alignment.Center)
        ) {
            classListFlow.value.let {
                when (it) {
                    is Resource.Loading -> {
                        CircularProgress()
                    }
                    is Resource.Failure -> {
                        it.exception.message?.let { it1 -> context.toast(it1) }
                    }
                    is Resource.Success -> {
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
                            items(it.result) { classInfo ->
                                ClassCard(classInformation = classInfo,
                                    cardColor = cardColors[classInfo.id.toInt()],
                                    onLongPress = { id ->
                                        showDialog.value = true
                                        dialogColor = cardColors[classInfo.id.toInt()]
                                        selectedClassID = id
                                        classTeacherText = classInfo.classTeacherName
                                    },
                                    onCardClick = { id, name ->
                                        navController.navigate(AppScreen.StudentScreen.StudentList.route + "/${id.toInt()}/$name")
                                    })
                            }
                        }
                    }
                    else -> {}
                }
            }
            nameUpdateFlow.value.let {
                when (it) {
                    is Resource.Loading -> {
                        CircularProgress()
                    }
                    is Resource.Failure -> {
                        it.exception.message?.let { it1 -> context.toast(it1) }
                    }
                    is Resource.Success -> {
                        classTeacherText = ""
                        LaunchedEffect(Unit) {
                            viewModel.getAllClasses()
                        }
                    }
                    else -> {}
                }
            }
        }
        if (showDialog.value) {
            AlertDialog(onDismissRequest = {
                showDialog.value = false
            }, text = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Assign Class Teacher", style = TextStyle(
                            fontFamily = mediumFont,
                            fontSize = 36.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    Spacer(modifier = Modifier.requiredHeight(10.dp))
                    OutlinedTextField(
                        value = classTeacherText,
                        onValueChange = { classTeacherText = it },
                        label = {
                            Text(
                                text = "Name", style = TextStyle(
                                    fontFamily = mediumFont,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        },
                        maxLines = 1,
                        textStyle = TextStyle(
                            fontFamily = regularFont,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Thin
                        ),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done,
                            keyboardType = KeyboardType.Text,
                            autoCorrect = false,
                            capitalization = KeyboardCapitalization.None,
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = dialogColor,
                            cursorColor = dialogColor,
                            disabledBorderColor = dialogColor,
                            unfocusedBorderColor = dialogColor
                        )
                    )
                }
            }, buttons = {
                Row(
                    modifier = Modifier.padding(all = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier.fillMaxWidth(), onClick = {
                            showDialog.value = false
                            context.toast("Please Wait")
                            if (classTeacherText.isNotEmpty()) {
                                viewModel.updateClassTeacherName(
                                    selectedClassID, classTeacherText
                                )
                            }
                        }, colors = ButtonDefaults.buttonColors(backgroundColor = dialogColor)
                    ) {
                        Text(
                            "Update", style = TextStyle(
                                fontFamily = mediumFont,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.SemiBold
                            ), color = Color.White
                        )
                    }
                }
            })
        }
    }
}