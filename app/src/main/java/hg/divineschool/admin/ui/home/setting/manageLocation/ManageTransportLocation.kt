package hg.divineschool.admin.ui.home.setting.manageLocation

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
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.google.accompanist.insets.ui.Scaffold
import hg.divineschool.admin.R
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.models.Place
import hg.divineschool.admin.ui.home.DPSBar
import hg.divineschool.admin.ui.home.setting.manageBooks.BookCard
import hg.divineschool.admin.ui.theme.Purple500
import hg.divineschool.admin.ui.theme.boldFont
import hg.divineschool.admin.ui.theme.mediumFont
import hg.divineschool.admin.ui.theme.regularFont
import hg.divineschool.admin.ui.utils.CircularProgress
import hg.divineschool.admin.ui.utils.toast

@Composable
fun ManageTransportLocation(
    navController: NavController,
    viewModel: ManageTransportLocationViewModel
) {

    val placeState = viewModel.placeListFlow.collectAsState()
    val context = LocalContext.current
    val showDialog = remember { mutableStateOf(false) }
    val showAddDialog = remember { mutableStateOf(false) }
    var placeName by remember { mutableStateOf("") }
    var addPlaceName by remember { mutableStateOf("") }
    var placePrice by remember { mutableStateOf("") }
    var addPlacePrice by remember { mutableStateOf("") }


    Scaffold(scaffoldState = rememberScaffoldState(),
        topBar = {
            DPSBar(onBackPressed = {
                navController.popBackStack()
            }, className = "Manage Transport Location")
        }, floatingActionButton = {
            ExtendedFloatingActionButton(onClick = {
                addPlaceName = ""
                addPlacePrice = ""
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
                    Text(
                        text = "Add Location", style = TextStyle(
                            fontFamily = boldFont,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.SemiBold
                        ), color = Color.White
                    )
                })
        }
    ) {
        placeState.value.let {
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
                                top = 80.dp, start = 10.dp, end = 10.dp, bottom = 80.dp
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
                            items(it.result) { place ->
                                BookCard(
                                    color = Color.LightGray,
                                    name = place.placeName,
                                    price = "${place.placePrice}"
                                ) { name, price ->
                                    showDialog.value = true
                                    placeName = name
                                    placePrice = price
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
                                Text(
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
        if (showDialog.value) {
            AlertDialog(onDismissRequest = {
                showDialog.value = false
            }, properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false),
                text = {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Edit/Remove Location", style = TextStyle(
                                fontFamily = mediumFont,
                                fontSize = 32.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                        Spacer(modifier = Modifier.requiredHeight(10.dp))
                        Text(
                            text = placeName,
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
                            value = placePrice,
                            onValueChange = { placePrice = it },
                            label = {
                                Text(
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
                },
                buttons = {
                    Column {
                        Row(
                            modifier = Modifier.padding(all = 8.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .padding(horizontal = 2.dp), onClick = {
                                    showDialog.value = false
                                    viewModel.deletePlace(Place(placeName, placePrice.toInt()))
                                }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
                            ) {
                                Text(
                                    "Delete", style = TextStyle(
                                        fontFamily = mediumFont,
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.SemiBold
                                    ), color = Color.White
                                )
                            }
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .padding(horizontal = 2.dp), onClick = {
                                    showDialog.value = false
                                    if (placePrice.isNotEmpty()) {
                                        viewModel.updatePlace(Place(placeName, placePrice.toInt()))
                                    }
                                }, colors = ButtonDefaults.buttonColors(backgroundColor = Purple500)
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
                        Row(
                            modifier = Modifier.padding(all = 8.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .padding(horizontal = 2.dp),
                                onClick = {
                                    showDialog.value = false
                                },
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
                            ) {
                                Text(
                                    "Close", style = TextStyle(
                                        fontFamily = mediumFont,
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.SemiBold
                                    ), color = Color.White
                                )
                            }

                        }

                    }


                })
        }

        if (showAddDialog.value) {
            AlertDialog(
                onDismissRequest = {
                    showAddDialog.value = false
                },
                properties = DialogProperties(
                    dismissOnBackPress = false,
                    dismissOnClickOutside = false
                ),
                text = {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Add New Book", style = TextStyle(
                                fontFamily = mediumFont,
                                fontSize = 32.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                        Spacer(modifier = Modifier.requiredHeight(10.dp))
                        OutlinedTextField(
                            value = addPlaceName,
                            onValueChange = { addPlaceName = it },
                            label = {
                                Text(
                                    text = "Place Name", style = TextStyle(
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
                            value = addPlacePrice,
                            onValueChange = { addPlacePrice = it },
                            label = {
                                Text(
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
                },
                buttons = {
                    Row(
                        modifier = Modifier.padding(all = 8.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(0.5f)
                                .padding(horizontal = 2.dp),
                            onClick = {
                                showAddDialog.value = false
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
                        ) {
                            Text(
                                "Close", style = TextStyle(
                                    fontFamily = mediumFont,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.SemiBold
                                ), color = Color.White
                            )
                        }
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(0.5f)
                                .padding(horizontal = 2.dp), onClick = {
                                showAddDialog.value = false
                                viewModel.addPlace(Place(addPlaceName, addPlacePrice.toInt()))
                            }, colors = ButtonDefaults.buttonColors(backgroundColor = Purple500)
                        ) {
                            Text(
                                "Add Location", style = TextStyle(
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