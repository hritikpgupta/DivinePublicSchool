package hg.divineschool.admin.ui.home.setting.manageBooks

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import hg.divineschool.admin.data.models.Book
import hg.divineschool.admin.ui.home.DPSBar
import hg.divineschool.admin.ui.home.dashboard.registerStudent.DropDown
import hg.divineschool.admin.ui.home.dashboard.registerStudent.dropDownModifier
import hg.divineschool.admin.ui.theme.*
import hg.divineschool.admin.ui.utils.CircularProgress
import hg.divineschool.admin.ui.utils.classNames
import hg.divineschool.admin.ui.utils.getClassColorFromName
import hg.divineschool.admin.ui.utils.toast

@Composable
fun ManageBook(navController: NavController, viewModel: ManageBookViewModel) {
    var classExpanded by remember { mutableStateOf(false) }
    var className by remember { mutableStateOf(classNames[0]) }
    val showDialog = remember { mutableStateOf(false) }
    val showAddDialog = remember { mutableStateOf(false) }
    val bookState = viewModel.bookListFlow.collectAsState()
    var bookName by remember { mutableStateOf("") }
    var addBookName by remember { mutableStateOf("") }
    var bookValue by remember { mutableStateOf("") }
    var addBookValue by remember { mutableStateOf("") }
    val context = LocalContext.current

    Scaffold(scaffoldState = rememberScaffoldState(), topBar = {
        DPSBar(onBackPressed = {
            navController.popBackStack()
        }, className = "Manage Books")
    }, floatingActionButton = {
        ExtendedFloatingActionButton(onClick = {
            showAddDialog.value = true
        },
            modifier = Modifier.padding(bottom = 70.dp, end = 10.dp),
            backgroundColor = Purple500,
            elevation = FloatingActionButtonDefaults.elevation(4.dp),
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
                androidx.compose.material3.Text(
                    text = "Add Book", style = TextStyle(
                        fontFamily = boldFont,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold
                    ), color = Color.White
                )
            })
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
                    Spacer(modifier = Modifier.requiredHeight(5.dp))
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
                                viewModel.getBookList(className)
                            },
                            onDismiss = { classExpanded = false },
                            values = classNames,
                            color = Color.Black,
                            selectedValue = className,
                            modifier = dropDownModifier.weight(0.5f)
                        )
                        Spacer(modifier = Modifier.weight(0.5f))
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
                                        horizontalArrangement = Arrangement.spacedBy(2.dp),
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
                                        columns = GridCells.Adaptive(300.dp)
                                    ) {
                                        items(it.result) { book ->
                                            BookCard(
                                                color = className.getClassColorFromName(),
                                                name = book.bookName,
                                                price = "${book.bookPrice}"
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
                            fontSize = 32.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    Spacer(modifier = Modifier.requiredHeight(10.dp))
                    androidx.compose.material3.Text(
                        text = bookName,
                        style = TextStyle(
                            fontFamily = mediumFont,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.SemiBold
                        ),
                        textAlign = TextAlign.Center,
                        color = Color.Black.copy(0.7f),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.requiredHeight(10.dp))
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
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.45f), onClick = {
                            showDialog.value = false
                            viewModel.deleteBook(className, Book(bookName, bookValue.toInt()))
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
                    Spacer(modifier = Modifier.weight(0.1f))
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.45f), onClick = {
                            showDialog.value = false
                            if (bookName.isNotEmpty() && bookValue.isNotEmpty()) {
                                viewModel.updateBook(className, Book(bookName, bookValue.toInt()))
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
        if (showAddDialog.value) {
            AlertDialog(onDismissRequest = {
                showAddDialog.value = false
            }, text = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    androidx.compose.material3.Text(
                        text = "Add New Book", style = TextStyle(
                            fontFamily = mediumFont,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    Spacer(modifier = Modifier.requiredHeight(10.dp))
                    OutlinedTextField(
                        value = addBookName,
                        onValueChange = { addBookName = it },
                        label = {
                            androidx.compose.material3.Text(
                                text = "Book Name", style = TextStyle(
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
                    Spacer(modifier = Modifier.requiredHeight(10.dp))
                    OutlinedTextField(
                        value = addBookValue,
                        onValueChange = { addBookValue = it },
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
                        modifier = Modifier
                            .fillMaxWidth(), onClick = {
                            showAddDialog.value = false
                            viewModel.addBook(className, Book(addBookName, addBookValue.toInt()))
                        }, colors = ButtonDefaults.buttonColors(backgroundColor = Purple500)
                    ) {
                        androidx.compose.material3.Text(
                            "Add Book", style = TextStyle(
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