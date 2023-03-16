package hg.divineschool.admin.ui.home.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.ui.home.DPSBar
import hg.divineschool.admin.ui.theme.lightFont
import hg.divineschool.admin.ui.utils.CircularProgress
import hg.divineschool.admin.ui.utils.toast

@Composable
fun AdminScreen(viewModel: AdminViewModel, navController: NavController) {

    val adminSettingFlow = viewModel.adminSettingFlow.collectAsState()
    val logFlow = viewModel.logFlow.collectAsState()
    val context = LocalContext.current
    val scroll = rememberScrollState()
    var logString by remember { mutableStateOf("") }

    Scaffold(topBar = {
        DPSBar(onBackPressed = {
            navController.popBackStack()
        }, className = "Admin Setting")
    }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(color = MaterialTheme.colors.background.copy(0.8f))
        ) {
            Text(
                text = logString,
                modifier = Modifier
                    .fillMaxWidth()
                    .requiredHeight(300.dp)
                    .verticalScroll(scroll),
                style = TextStyle(fontFamily = lightFont, fontSize = 20.sp)
            )

            adminSettingFlow.value.let {
                when (it) {
                    is Resource.Loading -> {
                        CircularProgress()
                    }
                    is Resource.Success -> {
                        context.toast("Success")
                    }
                    is Resource.Failure -> {
                        it.exception.message?.let { it1 -> context.toast(it1) }
                    }
                    is Resource.FailureMessage -> {}
                    else -> {}
                }
            }
            logFlow.value.let {
                if (it != null) {
                    logString = it
                }
            }
        }
    }
}