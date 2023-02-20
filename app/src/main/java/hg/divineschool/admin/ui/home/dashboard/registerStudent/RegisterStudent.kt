package hg.divineschool.admin.ui.home.dashboard.registerStudent

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import hg.divineschool.admin.R
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.models.Student
import hg.divineschool.admin.ui.home.DPSBar
import hg.divineschool.admin.ui.theme.boldFont
import hg.divineschool.admin.ui.theme.cardColors
import hg.divineschool.admin.ui.utils.toast

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun RegisterStudent(
    classID: String,
    className: String,
    navController: NavController,
    viewModel: RegisterStudentViewModel
) {
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

    Scaffold(scaffoldState = rememberScaffoldState(), topBar = {
        DPSBar(onBackPressed = {
            navController.popBackStack()
        }, className = className)
    }, floatingActionButton = {
        ExtendedFloatingActionButton(onClick = {
            if (uriString.value.isNotEmpty()) {
                viewModel.registerStudent(
                    Student(),
                    classID, className, uriString.value
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
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(color = MaterialTheme.colors.background.copy(0.8f))
        ) {
            Card(
                shape = CircleShape,
                elevation = 4.dp,
                border = BorderStroke(4.dp, cardColors[classID.toInt()]),
                modifier = Modifier
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
                                    clickImage.launch(Intent(context, CameraActivity::class.java))
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


