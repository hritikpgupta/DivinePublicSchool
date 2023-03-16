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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import hg.divineschool.admin.ui.home.DPSBar
import hg.divineschool.admin.ui.theme.lightFont
import kotlinx.coroutines.launch

@Composable
fun AdminScreen(viewModel: AdminViewModel, navController: NavController) {

    val context = LocalContext.current
    val scroll = rememberScrollState()
    var logString by remember { mutableStateOf("Started Database Migration...") }
    val scope = rememberCoroutineScope()

    Scaffold(topBar = {
        DPSBar(onBackPressed = {
            navController.popBackStack()
        }, className = "Admin Setting")
    }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(500.dp)
                .padding(padding)
                .padding(20.dp)
                .background(color = Color.DarkGray.copy(0.20f))
        ) {
            Text(
                text = logString,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scroll),
                style = TextStyle(
                    fontFamily = lightFont,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Medium,
                    textIndent = TextIndent(firstLine = 12.sp, restLine = 0.sp),
                ),
                lineHeight = 28.sp,
                letterSpacing = 1.sp, color = Color.Black
            )
            LaunchedEffect(Unit) {
                scope.launch {
                    viewModel.migrationEvent.collect { event ->
                        when (event) {
                            is MigrationEvent.SendLog -> {
                                logString = logString + "\n" + event.msg
                            }
                            is MigrationEvent.Success -> {}
                            is MigrationEvent.Failure -> {

                            }
                        }
                    }
                }
            }

        }
    }
}