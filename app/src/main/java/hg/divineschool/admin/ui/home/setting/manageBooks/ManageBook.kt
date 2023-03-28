package hg.divineschool.admin.ui.home.setting.manageBooks

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.insets.ui.Scaffold
import hg.divineschool.admin.R
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.ui.home.DPSBar
import hg.divineschool.admin.ui.home.dashboard.registerStudent.DropDown
import hg.divineschool.admin.ui.home.dashboard.registerStudent.dropDownModifier
import hg.divineschool.admin.ui.theme.*
import hg.divineschool.admin.ui.utils.*

@Composable
fun ManageBook(navController: NavController, viewModel: ManageBookViewModel) {
    val scrollState = rememberScrollState()
    var classExpanded by remember { mutableStateOf(false) }
    var className by remember { mutableStateOf(classNames[0]) }
    val showDialog = remember { mutableStateOf(false) }
    var bookList by remember { mutableStateOf(classNames[0].getClassBook()) }
    val bookState = viewModel.bookListFlow.collectAsState()
    var bookName by remember { mutableStateOf("") }
    var bookValue by remember { mutableStateOf("") }

    val context = LocalContext.current

    Scaffold(scaffoldState = rememberScaffoldState(), topBar = {
        DPSBar(onBackPressed = {
            navController.popBackStack()
        }, className = "Manage Books")
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
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
                        text = "Select Class", style = TextStyle(
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
                            modifier = dropDownModifier.weight(0.5f)
                        )
                        Spacer(modifier = Modifier.weight(0.2f))
                        Button(
                            onClick = {
                                viewModel.getBookList(className)
                            }, modifier = Modifier
                                .padding(8.dp)
                                .weight(0.3f)
                        ) {
                            Text(
                                text = "Go", textAlign = TextAlign.Center, style = TextStyle(
                                    fontFamily = regularFont,
                                    fontSize = 30.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
                    }

                    bookState.value.let {
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
                                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                                        userScrollEnabled = true,
                                        contentPadding = PaddingValues(
                                            top = 15.dp, start = 10.dp, end = 10.dp, bottom = 80.dp
                                        ),
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(
                                                color = MaterialTheme.colors.background.copy(
                                                    0.6f
                                                )
                                            ),
                                        columns = GridCells.Adaptive(280.dp)
                                    ) {
                                        items(it.result) { book ->
                                            BookCard(
                                                name = book.bookName,
                                                price = "$INR ${book.bookPrice}"
                                            ) { name, price ->
                                                showDialog.value = true
                                                bookName = name
                                                bookValue = price
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
        if (showDialog.value) {
            AlertDialog(onDismissRequest = {
                showDialog.value = false
            }, text = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    androidx.compose.material3.Text(
                        text = "Edit/Remove Book", style = TextStyle(
                            fontFamily = mediumFont,
                            fontSize = 36.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    Spacer(modifier = Modifier.requiredHeight(10.dp))
                    OutlinedTextField(
                        value = bookName,
                        onValueChange = { bookName = it },
                        label = {
                            androidx.compose.material3.Text(
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
                    )
                    OutlinedTextField(
                        value = bookValue,
                        onValueChange = { bookValue = it },
                        label = {
                            androidx.compose.material3.Text(
                                text = "Price", style = TextStyle(
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
                            keyboardType = KeyboardType.Number,
                            autoCorrect = false,
                            capitalization = KeyboardCapitalization.None,
                        ),
                        modifier = Modifier.fillMaxWidth(),
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

                        }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
                    ) {
                        androidx.compose.material3.Text(
                            "Delete", style = TextStyle(
                                fontFamily = mediumFont,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.SemiBold
                            ), color = Color.White
                        )
                    }
                    Button(
                        modifier = Modifier.fillMaxWidth(), onClick = {
                            showDialog.value = false
                            context.toast("Please Wait")
                            if (bookName.isNotEmpty() && bookValue.isNotEmpty()) {

                            }
                        }, colors = ButtonDefaults.buttonColors(backgroundColor = Purple500)
                    ) {
                        androidx.compose.material3.Text(
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