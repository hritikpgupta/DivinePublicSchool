package hg.divineschool.admin.ui.home.setting.manageBooks

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
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
import com.google.accompanist.insets.ui.Scaffold
import hg.divineschool.admin.data.models.FeeStructure
import hg.divineschool.admin.data.utils.convertToBookList
import hg.divineschool.admin.ui.home.DPSBar
import hg.divineschool.admin.ui.home.dashboard.registerStudent.DropDown
import hg.divineschool.admin.ui.home.dashboard.registerStudent.dropDownModifier
import hg.divineschool.admin.ui.theme.lightFont
import hg.divineschool.admin.ui.theme.regularFont
import hg.divineschool.admin.ui.utils.INR
import hg.divineschool.admin.ui.utils.classNames
import hg.divineschool.admin.ui.utils.getClassBook

@Composable
fun ManageBook(navController: NavController, viewModel: ManageBookViewModel) {
    val scrollState = rememberScrollState()
    var classExpanded by remember { mutableStateOf(false) }
    var className by remember { mutableStateOf(classNames[0]) }
    var bookList by remember { mutableStateOf(classNames[0].getClassBook()) }


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
                                bookList = className.getClassBook()
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

                    LazyVerticalGrid(
                        verticalArrangement = Arrangement.spacedBy(18.dp),
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        userScrollEnabled = true,
                        contentPadding = PaddingValues(
                            top = 15.dp, start = 10.dp, end = 10.dp, bottom = 80.dp
                        ),
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = MaterialTheme.colors.background.copy(0.6f)),
                        columns = GridCells.Adaptive(280.dp)
                    ) {
                        items(bookList) {
                            BookCard(name = it.bookName, price = "$INR ${it.bookPrice}")
                        }
                    }
                }
            }
        }
    }
}