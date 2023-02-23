package hg.divineschool.admin.ui.home.dashboard.registerStudent

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.util.Log
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
import androidx.compose.material.icons.filled.DateRange
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
import hg.divineschool.admin.AppScreen
import hg.divineschool.admin.BottomNavItem
import hg.divineschool.admin.R
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.models.Student
import hg.divineschool.admin.ui.home.DPSBar
import hg.divineschool.admin.ui.theme.boldFont
import hg.divineschool.admin.ui.theme.cardColors
import hg.divineschool.admin.ui.theme.mediumFont
import hg.divineschool.admin.ui.theme.regularFont
import hg.divineschool.admin.ui.utils.Log_Tag
import hg.divineschool.admin.ui.utils.toast
import kotlinx.coroutines.flow.collect
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
    val genderOptions = listOf("Boy", "Girl")
    val religionOptions =
        listOf("Buddhism", "Christianity", "Hinduism", "Islam", "Jainism", "Sikhism")
    val classEntryOptions = listOf(
        "PLay Group",
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
    var pickedBirthDate by remember {
        mutableStateOf(LocalDate.now())
    }
    var pickedAdmissionDate by remember {
        mutableStateOf(LocalDate.now())
    }

    var rollNumber by remember { mutableStateOf(TextFieldValue("")) }
    var enrollmentNumber by remember { mutableStateOf(TextFieldValue("")) }

    var firstName by remember { mutableStateOf(TextFieldValue("Hritik")) }
    var lastName by remember { mutableStateOf(TextFieldValue("Gupta")) }
    val dateOfBirth by remember {
        derivedStateOf {
            DateTimeFormatter.ofPattern("MMM dd yyyy").format(pickedBirthDate)
        }
    }
    var gender by remember { mutableStateOf(genderOptions[0]) }

    var fathersName by remember { mutableStateOf(TextFieldValue("Rakesh Kumar Gupta")) }
    var mothersName by remember { mutableStateOf(TextFieldValue("Pinki Gupta")) }
    var guardianOccupation by remember { mutableStateOf(TextFieldValue("Farmer")) }
    var religion by remember { mutableStateOf(religionOptions[0]) }


    var address by remember { mutableStateOf(TextFieldValue("Ahraura")) }
    var contactNumber by remember { mutableStateOf(TextFieldValue("7668479477")) }
    var aadharNumber by remember { mutableStateOf(TextFieldValue("5678345687001234")) }


    val dateOfAdmission by remember {
        derivedStateOf {
            DateTimeFormatter.ofPattern("MMM dd yyyy").format(pickedAdmissionDate)
        }
    }
    var entryClass by remember { mutableStateOf(classEntryOptions[0]) }
    var schoolAttended by remember { mutableStateOf(TextFieldValue("None")) }
    var transportStudent by remember { mutableStateOf(false) }
    var newStudent by remember { mutableStateOf(false) }
    var isOrphan by remember { mutableStateOf(false) }


    val birthDateDialogState = rememberMaterialDialogState()
    val admissionDateDialogState = rememberMaterialDialogState()
    var genderExpanded by remember { mutableStateOf(false) }
    var religionExpanded by remember { mutableStateOf(false) }
    var entryClassExpanded by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    val bringIntoViewRequester = BringIntoViewRequester()
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
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
    Scaffold(scaffoldState = rememberScaffoldState(), topBar = {
        DPSBar(onBackPressed = {
            navController.popBackStack()
        }, className = "Registration Form For $className")
    }, floatingActionButton = {
        ExtendedFloatingActionButton(onClick = {
            if (uriString.value.isNotEmpty()) {
                try {
                    val stu = Student(
                        rollNumber = rollNumber.text.toLong(),
                        enrollmentNumber = enrollmentNumber.text.toLong(),
                        firstName = firstName.text,
                        lastName = lastName.text,
                        dateOfBirth = dateOfBirth,
                        gender = gender,
                        fathersName = fathersName.text,
                        mothersName = mothersName.text,
                        guardianOccupation = guardianOccupation.text,
                        religion = religion,
                        address = address.text,
                        contactNumber = contactNumber.text.toLong(),
                        aadharNumber = aadharNumber.text.toLong(),
                        dateOfAdmission = dateOfAdmission,
                        entryClass = entryClass,
                        schoolAttended = schoolAttended.text,
                        transportStudent = transportStudent,
                        newStudent = newStudent,
                        orphan = isOrphan,
                        image = ""
                    )
                    viewModel.registerStudent(stu, classID, className, uriString.value)
                } catch (_: NumberFormatException) {
                }
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
                        .padding(10.dp)
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
                        Image(painter = painterResource(id = R.drawable.add_image),
                            contentDescription = "",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .requiredSize(120.dp)
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
            ) {
                OutlinedTextField(value = dateOfBirth,
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
                            birthDateDialogState.show()
                        })

                ExposedDropdownMenuBox(expanded = genderExpanded,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 10.dp),
                    onExpandedChange = { genderExpanded = !genderExpanded }) {
                    TextField(
                        readOnly = true,
                        value = gender,
                        modifier = Modifier.fillMaxWidth(),
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
                                expanded = genderExpanded
                            )
                        },
                        colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = cardColors[classID.toInt()],
                            focusedBorderColor = cardColors[classID.toInt()],
                            backgroundColor = MaterialTheme.colors.onBackground.copy(.05f)

                        )
                    )
                    ExposedDropdownMenu(
                        expanded = genderExpanded,
                        onDismissRequest = { genderExpanded = false }) {
                        genderOptions.forEach { selectionOption ->
                            DropdownMenuItem(onClick = {
                                gender = selectionOption
                                genderExpanded = false
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
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 24.dp)
            ) {
                OutlinedTextField(value = guardianOccupation,
                    label = {
                        Text(
                            text = "Enter Occupation", style = TextStyle(
                                fontFamily = mediumFont,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    },
                    maxLines = 1,
                    onValueChange = { guardianOccupation = it },
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
                        .weight(1f)
                        .padding(horizontal = 10.dp)
                        .onFocusEvent {
                            if (it.isFocused) {
                                coroutineScope.launch { bringIntoViewRequester.bringIntoView() }
                            }
                        })
                ExposedDropdownMenuBox(expanded = religionExpanded,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 10.dp),
                    onExpandedChange = { religionExpanded = !religionExpanded }) {
                    TextField(
                        readOnly = true,
                        value = religion,
                        modifier = Modifier.fillMaxWidth(),
                        onValueChange = { },
                        label = {
                            Text(
                                "Select Religion", style = TextStyle(
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
                                expanded = religionExpanded
                            )
                        },
                        colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = cardColors[classID.toInt()],
                            focusedBorderColor = cardColors[classID.toInt()],
                            backgroundColor = MaterialTheme.colors.onBackground.copy(.05f)

                        )
                    )
                    ExposedDropdownMenu(
                        expanded = religionExpanded,
                        onDismissRequest = { religionExpanded = false }) {
                        religionOptions.forEach { selectionOption ->
                            DropdownMenuItem(onClick = {
                                religion = selectionOption
                                religionExpanded = false
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
                OutlinedTextField(value = contactNumber,
                    label = {
                        Text(
                            text = "Enter Contact Number", style = TextStyle(
                                fontFamily = mediumFont,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    },
                    maxLines = 1,
                    onValueChange = { contactNumber = it },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = cardColors[classID.toInt()],
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Phone,
                        autoCorrect = false,
                        capitalization = KeyboardCapitalization.None,
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 10.dp)
                        .onFocusEvent {
                            if (it.isFocused) {
                                coroutineScope.launch { bringIntoViewRequester.bringIntoView() }
                            }
                        })

                OutlinedTextField(value = aadharNumber,
                    label = {
                        Text(
                            text = "Enter Aadhar Number", style = TextStyle(
                                fontFamily = mediumFont,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    },
                    maxLines = 1,
                    onValueChange = { aadharNumber = it },
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
                        .weight(1f)
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
            ) {
                OutlinedTextField(value = address,
                    label = {
                        Text(
                            text = "Enter Address ", style = TextStyle(
                                fontFamily = mediumFont,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    },
                    maxLines = 1,
                    onValueChange = { address = it },
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
                        .weight(1f)
                        .padding(horizontal = 10.dp)
                        .onFocusEvent {
                            if (it.isFocused) {
                                coroutineScope.launch { bringIntoViewRequester.bringIntoView() }
                            }
                        })
                OutlinedTextField(value = schoolAttended,
                    label = {
                        Text(
                            text = "Enter Previous School  ", style = TextStyle(
                                fontFamily = mediumFont,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    },
                    maxLines = 1,
                    onValueChange = { schoolAttended = it },
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
                        .weight(1f)
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
            ) {
                OutlinedTextField(value = "Date of Admission $dateOfAdmission",
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
                            imageVector = Icons.Filled.DateRange,
                            contentDescription = "Date of Admission",
                            modifier = Modifier.requiredSize(34.dp)
                        )
                    },
                    onValueChange = { },
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 10.dp)
                        .clickable {
                            admissionDateDialogState.show()
                        })

                ExposedDropdownMenuBox(expanded = entryClassExpanded,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 10.dp),
                    onExpandedChange = { entryClassExpanded = !entryClassExpanded }) {
                    TextField(
                        readOnly = true,
                        value = entryClass,
                        modifier = Modifier.fillMaxWidth(),
                        onValueChange = { },
                        label = {
                            Text(
                                "Select Entry Class", style = TextStyle(
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
                                expanded = entryClassExpanded
                            )
                        },
                        colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = cardColors[classID.toInt()],
                            focusedBorderColor = cardColors[classID.toInt()],
                            backgroundColor = MaterialTheme.colors.onBackground.copy(.05f)
                        )
                    )
                    ExposedDropdownMenu(expanded = entryClassExpanded,
                        onDismissRequest = { entryClassExpanded = false }) {
                        classEntryOptions.forEach { selectionOption ->
                            DropdownMenuItem(onClick = {
                                entryClass = selectionOption
                                entryClassExpanded = false
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
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.bus),
                        contentDescription = "",
                        modifier = Modifier.requiredSize(40.dp)
                    )
                    Text(
                        text = "Transport Student",
                        style = TextStyle(fontFamily = regularFont, fontSize = 30.sp)
                    )
                    Checkbox(
                        checked = transportStudent,
                        modifier = Modifier.requiredSize(60.dp),
                        colors = CheckboxDefaults.colors(checkedColor = cardColors[classID.toInt()]),
                        onCheckedChange = { transportStudent = it },
                    )
                }
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.new_student),
                        contentDescription = "",
                        modifier = Modifier.requiredSize(40.dp)
                    )
                    Text(
                        text = "New Student",
                        style = TextStyle(fontFamily = mediumFont, fontSize = 30.sp)
                    )
                    Checkbox(
                        checked = newStudent,
                        modifier = Modifier.requiredSize(60.dp),
                        colors = CheckboxDefaults.colors(checkedColor = cardColors[classID.toInt()]),
                        onCheckedChange = { newStudent = it },
                    )
                }
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.orphan),
                        contentDescription = "",
                        modifier = Modifier.requiredSize(40.dp)
                    )
                    Text(
                        text = "Orphan",
                        style = TextStyle(fontFamily = mediumFont, fontSize = 30.sp)
                    )
                    Checkbox(
                        checked = isOrphan,
                        modifier = Modifier.requiredSize(60.dp),
                        colors = CheckboxDefaults.colors(checkedColor = cardColors[classID.toInt()]),
                        onCheckedChange = { isOrphan = it },
                    )
                }
            }

            Spacer(modifier = Modifier.height(70.dp))

        }
        registerState.value.let { value ->
            when (value) {
                is Resource.Failure -> {
                    value.exception.message?.let { it1 -> context.toast(it1) }
                }
                is Resource.Success -> {
/*                    navController.previousBackStackEntry?.savedStateHandle?.set("reload", true)
                    scope.launch {
                        navController.currentBackStackEntryFlow.collect{
                            Log.i(Log_Tag, it.destination.route!!)
                        }
                        Log.i(Log_Tag, navController.previousBackStackEntry?.destination?.route!!)

                    }*/
                    navController.popBackStack()
                    //navController.popBackStack(AppScreen.StudentScreen.StudentList.route + "/${classID.toInt()}/$className",inclusive = false)

                    //navController.navigate(AppScreen.StudentScreen.StudentList.route + "/${classID.toInt()}/$className")

/*                    navController.navigate(AppScreen.StudentScreen.StudentList.route + "/${classID.toInt()}/$className"){
                        popUpTo(AppScreen.StudentScreen.RegisterStudent.route+ "/${classID.toInt()}/$className"){inclusive = true}
                    }*/


/*                    navController.navigate(BottomNavItem.Home.route){
                        popUpTo(AppScreen.StudentScreen.StudentList.route){inclusive = true}
                    }*/

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

        MaterialDialog(dialogState = birthDateDialogState, buttons = {
            positiveButton(text = "Ok") {}
            negativeButton(text = "Cancel")
        })
        {
            datepicker(
                initialDate = LocalDate.now(),
                title = "Pick a date",
                yearRange = IntRange(1950, 2099),
            ) {
                pickedBirthDate = it
            }
        }
        MaterialDialog(dialogState = admissionDateDialogState, buttons = {
            positiveButton(text = "Ok") {}
            negativeButton(text = "Cancel")
        })
        {
            datepicker(
                initialDate = LocalDate.now(),
                title = "Pick a date",
                yearRange = IntRange(1950, 2099),
            ) {
                pickedAdmissionDate = it
            }
        }
    }

}


