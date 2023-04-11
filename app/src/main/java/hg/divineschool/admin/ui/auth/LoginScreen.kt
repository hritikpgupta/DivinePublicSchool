package hg.divineschool.admin.ui.auth

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import hg.divineschool.admin.R
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.ui.home.HomeActivity
import hg.divineschool.admin.ui.theme.boldFont
import hg.divineschool.admin.ui.theme.regularFont
import hg.divineschool.admin.ui.utils.startNewActivity
import hg.divineschool.admin.ui.utils.toast
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LoginScreen(viewModel: AuthViewModel) {
//    var email by remember { mutableStateOf("test@test.com") }
//    var password by remember { mutableStateOf("123456") }
    var email by remember { mutableStateOf("admin@dps.com") }
    var password by remember { mutableStateOf("Hritik@0724") }
    val coroutineScope = rememberCoroutineScope()
    val bringIntoViewRequester = BringIntoViewRequester()
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val loginFlow = viewModel.loginFlow.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background),
    ) {
        Card(
            modifier = Modifier
                .requiredWidth(400.dp)
                .requiredHeight(400.dp)
                .align(Alignment.Center)
                .background(color = MaterialTheme.colors.background.copy(0.78f)),
            elevation = CardDefaults.elevatedCardElevation(8.dp),
            shape = RectangleShape
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colors.background.copy(0.78f))

            ) {
                Image(
                    contentScale = ContentScale.Crop,
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = stringResource(id = R.string.logo),
                    modifier = Modifier
                        .requiredSize(150.dp)
                        .clip(CircleShape)
                )
                OutlinedTextField(value = email, onValueChange = { email = it }, label = {
                    Text(
                        text = stringResource(id = R.string.email),
                        style = TextStyle(fontSize = 20.sp, fontFamily = regularFont)
                    )
                }, modifier = Modifier
                    .padding(top = 8.dp)
                    .onFocusEvent {
                        if (it.isFocused) {
                            coroutineScope.launch { bringIntoViewRequester.bringIntoView() }
                        }
                    }, keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                )
                )
                OutlinedTextField(value = password, onValueChange = { password = it }, label = {
                    Text(
                        text = stringResource(id = R.string.password),
                        style = TextStyle(fontSize = 20.sp, fontFamily = regularFont)
                    )
                }, modifier = Modifier
                    .padding(top = 8.dp)
                    .onFocusEvent {
                        if (it.isFocused) {
                            coroutineScope.launch { bringIntoViewRequester.bringIntoView() }
                        }
                    }, keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ), keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                }), visualTransformation = PasswordVisualTransformation()
                )
                Button(
                    onClick = { viewModel.login(email.trim(), password.trim()) },
                    shape = CutCornerShape(10),
                    modifier = Modifier.padding(top = 14.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.login_btn_text),
                        style = TextStyle(
                            color = Color.White,
                            fontFamily = boldFont,
                            fontSize = 24.sp
                        )
                    )
                }
            }
        }
    }

    loginFlow.value?.let {
        when (it) {
            is Resource.Failure -> {
                it.exception.message?.let { it1 -> context.toast(it1) }
            }
            is Resource.Success -> {
                context.startNewActivity(HomeActivity::class.java)
            }
            is Resource.Loading -> {
                Box(
                    contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator()
                }
            }
            is Resource.FailureMessage -> {}
            else -> {}
        }
    }

}

@Preview(device = "spec:width=1280dp,height=800dp,dpi=480")
@Composable
fun Show() {
    LoginScreen(hiltViewModel())
}