package hg.divineschool.admin.ui.home.dashboard.updateStudent

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.FileUtils
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
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
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import hg.divineschool.admin.R
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.models.Student
import hg.divineschool.admin.data.utils.validateStudentObjectBeforeUpload
import hg.divineschool.admin.ui.home.DPSBar
import hg.divineschool.admin.ui.home.dashboard.registerStudent.*
import hg.divineschool.admin.ui.theme.NoImageBackground
import hg.divineschool.admin.ui.theme.boldFont
import hg.divineschool.admin.ui.theme.cardColors
import hg.divineschool.admin.ui.theme.regularFont
import hg.divineschool.admin.ui.utils.CircularProgress
import hg.divineschool.admin.ui.utils.Log_Tag
import hg.divineschool.admin.ui.utils.customGetSerializable
import hg.divineschool.admin.ui.utils.toast
import id.zelory.compressor.Compressor
import kotlinx.coroutines.launch
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalFoundationApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun UpdateStudent(
    classID: String,
    className: String,
    navController: NavController,
    viewModel: RegisterStudentViewModel
) {
    val currentStudent =
        navController.previousBackStackEntry?.arguments?.customGetSerializable<Student>("studentObj")

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
        mutableStateOf(currentStudent?.dateOfBirth?.toLocalDate())
    }
    var pickedAdmissionDate by remember {
        mutableStateOf(currentStudent?.dateOfAdmission?.toLocalDate())
    }
    val classColor = cardColors[classID.toInt()]
    var rollNumber by remember { mutableStateOf(TextFieldValue(currentStudent?.rollNumber.toString())) }
    var scholarNumber by remember { mutableStateOf(TextFieldValue(currentStudent?.scholarNumber.toString())) }
    var firstName by remember { mutableStateOf(TextFieldValue(currentStudent?.firstName.toString())) }
    var lastName by remember { mutableStateOf(TextFieldValue(currentStudent?.lastName.toString())) }
    val dateOfBirth by remember {
        derivedStateOf {
            DateTimeFormatter.ofPattern("MMM dd yyyy").format(pickedBirthDate)
        }
    }
    var gender by remember { mutableStateOf(genderOptions.getValue(currentStudent?.gender.toString())) }
    var fathersName by remember { mutableStateOf(TextFieldValue(currentStudent?.fathersName.toString())) }
    var mothersName by remember { mutableStateOf(TextFieldValue(currentStudent?.mothersName.toString())) }
    var guardianOccupation by remember { mutableStateOf(TextFieldValue(currentStudent?.guardianOccupation.toString())) }
    var religion by remember { mutableStateOf(religionOptions.getValue(currentStudent?.religion.toString())) }
    var address by remember { mutableStateOf(TextFieldValue(currentStudent?.address.toString())) }
    var contactNumber by remember { mutableStateOf(TextFieldValue(currentStudent?.contactNumber.toString())) }
    var aadharNumber by remember { mutableStateOf(TextFieldValue(currentStudent?.aadharNumber.toString())) }
    val dateOfAdmission by remember {
        derivedStateOf {
            DateTimeFormatter.ofPattern("MMM dd yyyy").format(pickedAdmissionDate)
        }
    }
    var entryClass by remember { mutableStateOf(classEntryOptions.getValue(currentStudent?.entryClass.toString())) }
    var schoolAttended by remember { mutableStateOf(TextFieldValue(currentStudent?.schoolAttended.toString())) }
    var transportStudent by remember { mutableStateOf(currentStudent?.transportStudent) }
    var newStudent by remember { mutableStateOf(currentStudent?.newStudent) }
    var isOrphan by remember { mutableStateOf(currentStudent?.orphan) }
    var isRte by remember { mutableStateOf(currentStudent?.rte) }
    val birthDateDialogState = rememberMaterialDialogState()
    val admissionDateDialogState = rememberMaterialDialogState()
    var genderExpanded by remember { mutableStateOf(false) }
    var religionExpanded by remember { mutableStateOf(false) }
    var entryClassExpanded by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val updateState = viewModel.updateStudentFlow.collectAsState()
    val uriString = remember {
        if (currentStudent!!.image.isEmpty()) {
            mutableStateOf("")
        } else {
            mutableStateOf(currentStudent.image)
        }
    }
    val showImage = remember {
        if (uriString.value.isEmpty()) {
            mutableStateOf(false)
        } else {
            mutableStateOf(true)
        }
    }
    val clickImage =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                uriString.value = it.data?.getStringExtra("imageUri").toString()
                showImage.value = true
                coroutineScope.launch {
                    val compressedFileImage =
                        Compressor.compress(context, File(Uri.parse(uriString.value).path!!))
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
        }, className = "${currentStudent?.firstName} ${currentStudent?.lastName}")
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
                        transportStudent = transportStudent!!,
                        newStudent = newStudent!!,
                        orphan = isOrphan!!,
                        rte = isRte!!,
                        image = currentStudent!!.image
                    )
                    val formValidation = validateStudentObjectBeforeUpload(student)
                    if (formValidation == null) {
                        viewModel.updateStudent(
                            student, classID, className, uriString.value
                        )
                    } else {
                        context.toast(formValidation)
                    }

                } catch (e: NumberFormatException) {
                    context.toast("Roll or Enrollment can't be null")
                } catch (e: Exception) {
                    e.printStackTrace()
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
                    text = "Update", style = TextStyle(
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
                                if (uriString.value.startsWith("https")) {
                                    AsyncImage(
                                        model = ImageRequest.Builder(LocalContext.current)
                                            .data(uriString.value)
                                            .crossfade(true).build(),
                                        contentScale = ContentScale.Crop,
                                        contentDescription = "Profile Image",
                                        modifier = Modifier
                                            .requiredSize(220.dp)
                                            .background(color = Color.White)
                                    )
                                } else {
                                    GlideImage(
                                        model = Uri.parse(uriString.value).path,
                                        contentDescription = "Profile Image",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .requiredSize(220.dp)
                                            .background(color = Color.White)
                                    )
                                }
                            }
                        } else {
                            Image(
                                painter = painterResource(id = R.drawable.image_missing),
                                contentDescription = "",
                                contentScale = ContentScale.FillWidth,
                                modifier = Modifier
                                    .requiredSize(145.dp)
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
                    isEnabled = false,
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
                        fontSize = 18.sp,
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
                        fontSize = 18.sp,
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
                transportStudent = transportStudent!!,
                newStudent = newStudent!!,
                isOrphan = isOrphan!!,
                isRte = isRte!!,
                onTransportChange = { transportStudent = it },
                onNewStudentChange = { newStudent = it },
                onIsOrphanChange = { isOrphan = it },
                onRteChange = { isRte = it }
            )
            Spacer(modifier = Modifier.height(70.dp))
        }
        updateState.value.let { value ->
            when (value) {
                is Resource.Failure -> {
                    value.exception.message?.let { it1 -> context.toast(it1) }
                }
                is Resource.Success -> {
                    LaunchedEffect(Unit) {
                        Log.i(Log_Tag, "Student Updated")
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