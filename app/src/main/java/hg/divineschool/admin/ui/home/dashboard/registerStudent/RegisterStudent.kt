package hg.divineschool.admin.ui.home.dashboard.registerStudent

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.FileUtils
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
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
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.models.Student
import hg.divineschool.admin.data.utils.validateStudentObjectBeforeUpload
import hg.divineschool.admin.ui.home.DPSBar
import hg.divineschool.admin.ui.theme.NoImageBackground
import hg.divineschool.admin.ui.theme.boldFont
import hg.divineschool.admin.ui.theme.cardColors
import hg.divineschool.admin.ui.theme.regularFont
import hg.divineschool.admin.ui.utils.CircularProgress
import hg.divineschool.admin.ui.utils.toast
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.quality
import id.zelory.compressor.constraint.resolution
import id.zelory.compressor.constraint.size
import kotlinx.coroutines.launch
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@OptIn(
    ExperimentalGlideComposeApi::class,
    ExperimentalFoundationApi::class,
)
@Composable
fun RegisterStudent(
    classID: String,
    className: String,
    navController: NavController,
    viewModel: RegisterStudentViewModel
) {

    val coroutineScope = rememberCoroutineScope()
    val bringIntoViewRequester = BringIntoViewRequester()
    val editTextModifier = Modifier
        .padding(horizontal = 10.dp)
        .onFocusEvent {
            if (it.isFocused) {
                coroutineScope.launch { bringIntoViewRequester.bringIntoView() }
            }
        }
    var pickedBirthDate by remember {
        mutableStateOf(LocalDate.now())
    }
    var pickedAdmissionDate by remember {
        mutableStateOf(LocalDate.now())
    }
    val classColor = cardColors[classID.toInt()]
    var rollNumber by remember { mutableStateOf(TextFieldValue("")) }
    var scholarNumber by remember { mutableStateOf(TextFieldValue("")) }
    var firstName by remember { mutableStateOf(TextFieldValue("")) }
    var lastName by remember { mutableStateOf(TextFieldValue("")) }
    val dateOfBirth by remember {
        derivedStateOf {
            DateTimeFormatter.ofPattern("MMM dd yyyy").format(pickedBirthDate)
        }
    }
    var gender by remember { mutableStateOf(genderOptions[0]) }
    var fathersName by remember { mutableStateOf(TextFieldValue("")) }
    var mothersName by remember { mutableStateOf(TextFieldValue("")) }
    var guardianOccupation by remember { mutableStateOf(TextFieldValue("")) }
    var religion by remember { mutableStateOf(religionOptions[0]) }
    var address by remember { mutableStateOf(TextFieldValue("")) }
    var contactNumber by remember { mutableStateOf(TextFieldValue("")) }
    var aadharNumber by remember { mutableStateOf(TextFieldValue("")) }
    val dateOfAdmission by remember {
        derivedStateOf {
            DateTimeFormatter.ofPattern("MMM dd yyyy").format(pickedAdmissionDate)
        }
    }
    var entryClass by remember { mutableStateOf(classEntryOptions[0]) }
    var schoolAttended by remember { mutableStateOf(TextFieldValue("")) }
    var transportStudent by remember { mutableStateOf(false) }
    var newStudent by remember { mutableStateOf(false) }
    var isOrphan by remember { mutableStateOf(false) }
    var isRte by remember { mutableStateOf(false) }
    val birthDateDialogState = rememberMaterialDialogState()
    val admissionDateDialogState = rememberMaterialDialogState()
    var genderExpanded by remember { mutableStateOf(false) }
    var religionExpanded by remember { mutableStateOf(false) }
    var entryClassExpanded by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val registerState = viewModel.registerStudentFlow.collectAsState()
    val uriString = remember {
        mutableStateOf("")
    }
    val showImage = remember {
        mutableStateOf(false)
    }
    val clickImage =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                uriString.value = it.data?.getStringExtra("imageUri").toString()
                showImage.value = true
                coroutineScope.launch {
                    val compressedFileImage =
                        Compressor.compress(context, File(Uri.parse(uriString.value).path!!)){
                            resolution(400,400)
                            quality(80)
                            size(30_000)
                        }
                    FileUtils.copy(
                        compressedFileImage.inputStream(),
                        File(Uri.parse(uriString.value).path!!).outputStream()
                    )
                    compressedFileImage.delete()
                }
            }
        }

    Scaffold(scaffoldState = rememberScaffoldState(), topBar = {
        DPSBar(onBackPressed = {
            navController.popBackStack()
        }, className = "Registration Form For $className")
    }, floatingActionButton = {
        ExtendedFloatingActionButton(onClick = {
            if (rollNumber.text.isNotEmpty() && scholarNumber.text.isNotEmpty()) {
                try {
                    val student = Student(
                        rollNumber = rollNumber.text.toLong(),
                        scholarNumber = scholarNumber.text.toLong(),
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
                        rte = isRte,
                        image = ""
                    )
                    val formValidation = validateStudentObjectBeforeUpload(student)
                    if (formValidation == null) {
                        viewModel.registerStudent(
                            student, classID, className, uriString.value
                        )
                    } else {
                        context.toast(formValidation)
                    }

                } catch (_: NumberFormatException) {
                    context.toast("Roll or Enrollment can't be null")
                }
            }
        },
            modifier = Modifier.padding(bottom = 60.dp, end = 10.dp),
            elevation = FloatingActionButtonDefaults.elevation(4.dp),
            backgroundColor = classColor,
            shape = RoundedCornerShape(8.dp),
            icon = {
                Icon(
                    Icons.Filled.CloudUpload,
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
                .padding(bottom = 65.dp, start = 5.dp, end = 5.dp)
                .background(color = MaterialTheme.colors.background.copy(0.8f))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Card(
                        shape = CircleShape,
                        elevation = 4.dp,
                        border = BorderStroke(4.dp, classColor),
                        backgroundColor = NoImageBackground,
                        modifier = Modifier
                            .requiredSize(220.dp)
                            .padding(12.dp)
                    ) {
                        if (showImage.value) {
                            if (uriString.value.isNotEmpty()) {
                                GlideImage(
                                    model = Uri.parse(uriString.value).path,
                                    contentDescription = "Profile Image",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .requiredSize(220.dp)
                                        .background(color = Color.White)
                                )
                            }
                        } else {
                            Image(
                                painter = painterResource(id = hg.divineschool.admin.R.drawable.image_missing),
                                contentDescription = "",
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier
                                    .requiredSize(160.dp)
                                    .background(color = NoImageBackground)
                            )
                        }
                    }
                    Icon(
                        Icons.Filled.AddAPhoto,
                        null,
                        tint = classColor,
                        modifier = Modifier
                            .padding(bottom = 4.dp, end = 2.dp)
                            .requiredSize(32.dp)
                            .clickable {
                                clickImage.launch(
                                    Intent(
                                        context,
                                        CameraActivity::class.java
                                    )
                                )
                            }
                    )
                }
            }
            FormRow(padding = 24) {
                FormEditText(textValue = rollNumber,
                    text = "Roll Number",
                    keyboardType = KeyboardType.Number,
                    color = classColor,
                    modifier = editTextModifier.weight(1f),
                    isEnabled = true,
                    onValueChanged = { rollNumber = it })
                FormEditText(textValue = scholarNumber,
                    text = "Scholar Number",
                    keyboardType = KeyboardType.Number,
                    color = classColor,
                    modifier = editTextModifier.weight(1f),
                    isEnabled = true,
                    onValueChanged = { scholarNumber = it })
            }
            FormRow(padding = 24) {
                FormEditText(textValue = firstName,
                    text = "Enter First Name",
                    keyboardType = KeyboardType.Text,
                    color = classColor,
                    modifier = editTextModifier.weight(2f),
                    isEnabled = true,
                    onValueChanged = { firstName = it })
                FormEditText(textValue = lastName,
                    text = "Enter Last Name",
                    keyboardType = KeyboardType.Text,
                    color = classColor,
                    modifier = editTextModifier.weight(2f),
                    isEnabled = true,
                    onValueChanged = { lastName = it })
            }
            FormRow(padding = 24) {
                OutlinedTextField(value = dateOfBirth,
                    textStyle = TextStyle(
                        textAlign = TextAlign.Center,
                        fontFamily = regularFont,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colors.onBackground
                    ),
                    maxLines = 1,
                    enabled = false,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = classColor,
                        focusedLabelColor = classColor,
                        unfocusedBorderColor = classColor,
                        unfocusedLabelColor = classColor,
                        leadingIconColor = classColor,
                        cursorColor = classColor,
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
                DropDown(
                    lableText = "Select Gender",
                    expanded = genderExpanded,
                    onExpand = { genderExpanded = !genderExpanded },
                    onItemClick = {
                        gender = it
                        genderExpanded = false
                    },
                    onDismiss = { genderExpanded = false },
                    values = genderOptions,
                    color = classColor,
                    selectedValue = gender,
                    modifier = dropDownModifier.weight(1f)
                )
            }
            FormRow(padding = 24) {
                FormEditText(textValue = fathersName,
                    text = "Enter Father's Name",
                    keyboardType = KeyboardType.Text,
                    color = classColor,
                    modifier = editTextModifier.weight(2f),
                    isEnabled = true,
                    onValueChanged = { fathersName = it })
                FormEditText(textValue = mothersName,
                    text = "Enter Mother's Name",
                    keyboardType = KeyboardType.Text,
                    color = classColor,
                    modifier = editTextModifier.weight(2f),
                    isEnabled = true,
                    onValueChanged = { mothersName = it })
            }
            FormRow(padding = 24) {
                FormEditText(textValue = guardianOccupation,
                    text = "Enter Occupation",
                    keyboardType = KeyboardType.Text,
                    color = classColor,
                    modifier = editTextModifier.weight(1f),
                    isEnabled = true,
                    onValueChanged = { guardianOccupation = it })
                DropDown(
                    lableText = "Select Religion",
                    expanded = religionExpanded,
                    onExpand = { religionExpanded = !religionExpanded },
                    onItemClick = {
                        religion = it
                        religionExpanded = false
                    },
                    onDismiss = { religionExpanded = false },
                    values = religionOptions,
                    color = classColor,
                    selectedValue = religion,
                    modifier = dropDownModifier.weight(1f)
                )
            }
            FormRow(padding = 24) {
                FormEditText(textValue = contactNumber,
                    text = "Enter Contact Number",
                    keyboardType = KeyboardType.Phone,
                    color = classColor,
                    modifier = editTextModifier.weight(2f),
                    isEnabled = true,
                    onValueChanged = { contactNumber = it })
                FormEditText(textValue = aadharNumber,
                    text = "Enter Aadhar Number",
                    keyboardType = KeyboardType.Number,
                    color = classColor,
                    modifier = editTextModifier.weight(2f),
                    isEnabled = true,
                    onValueChanged = { aadharNumber = it })
            }
            FormRow(padding = 24) {
                FormEditText(textValue = address,
                    text = "Enter Address",
                    keyboardType = KeyboardType.Text,
                    color = classColor,
                    modifier = editTextModifier.weight(2f),
                    isEnabled = true,
                    onValueChanged = { address = it })
                FormEditText(textValue = schoolAttended,
                    text = "Enter Previous School",
                    keyboardType = KeyboardType.Text,
                    color = classColor,
                    modifier = editTextModifier.weight(2f),
                    isEnabled = true,
                    onValueChanged = { schoolAttended = it })
            }
            FormRow(padding = 24) {
                OutlinedTextField(value = "Date of Admission $dateOfAdmission",
                    textStyle = TextStyle(
                        textAlign = TextAlign.Center,
                        fontFamily = regularFont,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colors.onBackground
                    ),
                    maxLines = 1,
                    enabled = false,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = classColor,
                        focusedLabelColor = classColor,
                        unfocusedBorderColor = classColor,
                        unfocusedLabelColor = classColor,
                        leadingIconColor = classColor,
                        cursorColor = classColor,
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

                DropDown(
                    lableText = "Select Entry Class",
                    expanded = entryClassExpanded,
                    onExpand = { entryClassExpanded = !entryClassExpanded },
                    onItemClick = {
                        entryClass = it
                        entryClassExpanded = false
                    },
                    onDismiss = { entryClassExpanded = false },
                    values = classEntryOptions,
                    color = classColor,
                    selectedValue = entryClass,
                    modifier = dropDownModifier.weight(1f)
                )

            }
            FormCheckboxes(color = classColor,
                transportStudent = transportStudent,
                newStudent = newStudent,
                isOrphan = isOrphan,
                isRte = isRte,
                onTransportChange = { transportStudent = it },
                onNewStudentChange = { newStudent = it },
                onIsOrphanChange = { isOrphan = it },
                onRteChange = { isRte = it }
            )
            Spacer(modifier = Modifier.height(70.dp))
        }
        registerState.value.let { value ->
            when (value) {
                is Resource.Failure -> {
                    value.exception.message?.let { it1 -> context.toast(it1) }
                }
                is Resource.Success -> {
                    LaunchedEffect(Unit) {
                        navController.popBackStack()
                    }
                }
                is Resource.Loading -> {
                    CircularProgress(color = classColor)
                }
                is Resource.FailureMessage -> {
                    Toast.makeText(context, value.message, Toast.LENGTH_LONG).show()
                }
                else -> {}
            }
        }


        MaterialDialog(dialogState = birthDateDialogState, buttons = {
            positiveButton(text = "Ok") {}
            negativeButton(text = "Cancel")
        }) {
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
        }) {
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





