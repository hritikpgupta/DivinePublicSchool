package hg.divineschool.admin.ui.home.dashboard.registerStudent

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import hg.divineschool.admin.R
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.models.Student
import hg.divineschool.admin.ui.home.DPSBar
import hg.divineschool.admin.ui.theme.boldFont
import hg.divineschool.admin.ui.theme.cardColors
import hg.divineschool.admin.ui.theme.mediumFont
import hg.divineschool.admin.ui.theme.regularFont
import hg.divineschool.admin.ui.utils.toast
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@OptIn(
    ExperimentalGlideComposeApi::class,
    ExperimentalFoundationApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun RegisterStudent(
    classID: String,
    className: String,
    navController: NavController,
    viewModel: RegisterStudentViewModel
) {
    var rollNumber by remember { mutableStateOf(TextFieldValue("")) }
    var enrollmentNumber by remember { mutableStateOf(TextFieldValue("")) }
    var firstName by remember { mutableStateOf(TextFieldValue("")) }
    var lastName by remember { mutableStateOf(TextFieldValue("")) }
    var fathersName by remember { mutableStateOf(TextFieldValue("")) }
    var mothersName by remember { mutableStateOf(TextFieldValue("")) }
    var pickedDate by remember {
        mutableStateOf(LocalDate.now())
    }
    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter.ofPattern("MMM dd yyyy").format(pickedDate)
        }
    }
    val dateDialogState = rememberMaterialDialogState()
    val genderOptions = listOf("Boy", "Girl")
    var expanded by remember { mutableStateOf(false) }
    var genderOptionsText by remember { mutableStateOf(genderOptions[0]) }
    val scrollState = rememberScrollState()
    val bringIntoViewRequester = BringIntoViewRequester()
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val registerState = viewModel.registerStudentFlow.collectAsState()
    val uriString = remember { mutableStateOf("") }
    val showImage = remember { mutableStateOf(false) }
    val clickImage =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val intentDate = it.data
                uriString.value = intentDate?.getStringExtra("imageUri").toString()
                showImage.value = true
            }
        }
    Scaffold(
        scaffoldState = rememberScaffoldState(), topBar = {
            DPSBar(onBackPressed = {
                navController.popBackStack()
            }, className = className)
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = {
                if (uriString.value.isNotEmpty()) {
                    viewModel.registerStudent(
                        Student(), classID, className, uriString.value
                    )
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
                        text = "Save", style = TextStyle(
                            fontFamily = boldFont,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.SemiBold
                        ), color = Color.White
                    )
                })
        }, floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxSize()
                .padding(paddingValues)
                .padding(bottom = 70.dp)
                .background(color = MaterialTheme.colors.background.copy(0.8f))
        )
        {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    shape = CircleShape,
                    elevation = 4.dp,
                    border = BorderStroke(4.dp, cardColors[classID.toInt()]),
                    modifier = Modifier
                        .weight(1f)
                        .requiredSize(200.dp)
                        .padding(20.dp)
                ) {
                    if (showImage.value) {
                        if (uriString.value.isNotEmpty()) {
                            GlideImage(model = Uri.parse(uriString.value).path,
                                contentDescription = "Profile Image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .requiredSize(180.dp)
                                    .background(color = Color.White)
                                    .clickable {
                                        clickImage.launch(
                                            Intent(
                                                context, CameraActivity::class.java
                                            )
                                        )
                                    })
                        }

                    } else {
                        Image(painter = painterResource(id = R.drawable.ic_launcher_background),
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .requiredSize(180.dp)
                                .background(color = Color.White)
                                .clickable {
                                    clickImage.launch(Intent(context, CameraActivity::class.java))
                                })

                    }
                }

                OutlinedTextField(value = rollNumber,
                    label = {
                        Text(
                            text = "Enter Roll Number", style = TextStyle(
                                fontFamily = mediumFont,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    },
                    maxLines = 1,
                    onValueChange = { rollNumber = it },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = cardColors[classID.toInt()],
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number,
                        autoCorrect = false,
                        capitalization = KeyboardCapitalization.None,
                    ),
                    modifier = Modifier
                        .weight(2f)
                        .padding(horizontal = 25.dp)
                        .onFocusEvent {
                            if (it.isFocused) {
                                coroutineScope.launch { bringIntoViewRequester.bringIntoView() }
                            }
                        })
                OutlinedTextField(value = enrollmentNumber,
                    label = {
                        Text(
                            text = "Enter Enrollment Number", style = TextStyle(
                                fontFamily = mediumFont,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    },
                    maxLines = 1,
                    onValueChange = { enrollmentNumber = it },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = cardColors[classID.toInt()],
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number,
                        autoCorrect = false,
                        capitalization = KeyboardCapitalization.None,
                    ),
                    modifier = Modifier
                        .weight(2f)
                        .padding(horizontal = 25.dp)
                        .onFocusEvent {
                            if (it.isFocused) {
                                coroutineScope.launch { bringIntoViewRequester.bringIntoView() }
                            }
                        })

            }
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 14.dp)

            ) {
                OutlinedTextField(value = firstName,
                    label = {
                        Text(
                            text = "Enter First Name", style = TextStyle(
                                fontFamily = mediumFont,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    },
                    maxLines = 1,
                    onValueChange = { firstName = it },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = cardColors[classID.toInt()],
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text,
                        autoCorrect = false,
                        capitalization = KeyboardCapitalization.None,
                    ),
                    modifier = Modifier
                        .weight(2f)
                        .padding(horizontal = 10.dp)
                        .onFocusEvent {
                            if (it.isFocused) {
                                coroutineScope.launch { bringIntoViewRequester.bringIntoView() }
                            }
                        })
                OutlinedTextField(value = lastName,
                    label = {
                        Text(
                            text = "Enter Last Name", style = TextStyle(
                                fontFamily = mediumFont,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    },
                    maxLines = 1,
                    onValueChange = { lastName = it },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = cardColors[classID.toInt()],
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text,
                        autoCorrect = false,
                        capitalization = KeyboardCapitalization.None,
                    ),
                    modifier = Modifier
                        .weight(2f)
                        .padding(horizontal = 10.dp)
                        .onFocusEvent {
                            if (it.isFocused) {
                                coroutineScope.launch { bringIntoViewRequester.bringIntoView() }
                            }
                        })
            }
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 24.dp)
            )
            {
                OutlinedTextField(value = formattedDate,
                    textStyle = TextStyle(
                        textAlign = TextAlign.Center,
                        fontFamily = regularFont,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colors.onBackground
                    ),
                    maxLines = 1,
                    enabled = false,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = cardColors[classID.toInt()],
                        focusedLabelColor = cardColors[classID.toInt()],
                        unfocusedBorderColor = cardColors[classID.toInt()],
                        unfocusedLabelColor = cardColors[classID.toInt()],
                        leadingIconColor = cardColors[classID.toInt()]
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Cake,
                            contentDescription = "Birthday",
                            modifier = Modifier.requiredSize(34.dp)
                        )
                    },
                    onValueChange = { },
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 10.dp)
                        .clickable {
                            dateDialogState.show()
                        })

                ExposedDropdownMenuBox(expanded = expanded,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 10.dp),
                    onExpandedChange = { expanded = !expanded })
                {
                    TextField(
                        readOnly = true,
                        value = genderOptionsText,
                        onValueChange = { },
                        label = {
                            Text(
                                "Select Gender", style = TextStyle(
                                    textAlign = TextAlign.Center,
                                    fontFamily = regularFont,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colors.onBackground
                                )
                            )
                        },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = expanded
                            )
                        },
                        colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = cardColors[classID.toInt()],
                            focusedBorderColor = cardColors[classID.toInt()],
                            backgroundColor = MaterialTheme.colors.onBackground.copy(.05f)

                        )
                    )
                    ExposedDropdownMenu(expanded = expanded,
                        onDismissRequest = { expanded = false }) {
                        genderOptions.forEach { selectionOption ->
                            DropdownMenuItem(onClick = {
                                genderOptionsText = selectionOption
                                expanded = false
                            }) {
                                Text(
                                    text = selectionOption, style = TextStyle(
                                        textAlign = TextAlign.Center,
                                        fontFamily = regularFont,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = MaterialTheme.colors.onBackground
                                    )
                                )
                            }
                        }
                    }
                }


            }
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 24.dp)

            ) {
                OutlinedTextField(value = fathersName,
                    label = {
                        Text(
                            text = "Enter Father's Name", style = TextStyle(
                                fontFamily = mediumFont,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    },
                    maxLines = 1,
                    onValueChange = { fathersName = it },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = cardColors[classID.toInt()],
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text,
                        autoCorrect = false,
                        capitalization = KeyboardCapitalization.None,
                    ),
                    modifier = Modifier
                        .weight(2f)
                        .padding(horizontal = 10.dp)
                        .onFocusEvent {
                            if (it.isFocused) {
                                coroutineScope.launch { bringIntoViewRequester.bringIntoView() }
                            }
                        })
                OutlinedTextField(value = mothersName,
                    label = {
                        Text(
                            text = "Enter Mother's Name", style = TextStyle(
                                fontFamily = mediumFont,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    },
                    maxLines = 1,
                    onValueChange = { mothersName = it },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = cardColors[classID.toInt()],
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text,
                        autoCorrect = false,
                        capitalization = KeyboardCapitalization.None,
                    ),
                    modifier = Modifier
                        .weight(2f)
                        .padding(horizontal = 10.dp)
                        .onFocusEvent {
                            if (it.isFocused) {
                                coroutineScope.launch { bringIntoViewRequester.bringIntoView() }
                            }
                        })
            }

        }
        MaterialDialog(dialogState = dateDialogState, buttons = {
            positiveButton(text = "Ok") {}
            negativeButton(text = "Cancel")
        })
        {
            datepicker(
                initialDate = LocalDate.now(),
                title = "Pick a date",
                yearRange = IntRange(1950, 2099),
            ) {
                pickedDate = it
            }
        }
        registerState.value.let { value ->
            when (value) {
                is Resource.Failure -> {
                    value.exception.message?.let { it1 -> context.toast(it1) }
                }
                is Resource.Success -> {

                }
                is Resource.Loading -> {
                    Box(
                        contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator()
                    }
                }
                else -> {}
            }
        }
    }
}


