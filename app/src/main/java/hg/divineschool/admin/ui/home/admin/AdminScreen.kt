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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import hg.divineschool.admin.data.dashboard.settings.SettingRepository
import hg.divineschool.admin.data.dashboard.settings.SettingRepositoryImpl
import hg.divineschool.admin.ui.home.DPSBar
import hg.divineschool.admin.ui.theme.lightFont
import kotlinx.coroutines.launch

@Composable
fun AdminScreen(viewModel: AdminViewModel, navController: NavController) {

    val context = LocalContext.current
    val scroll = rememberScrollState()
    var logString by remember { mutableStateOf("Test") }
    val scope = rememberCoroutineScope()
    val repository: SettingRepository = SettingRepositoryImpl(db = FirebaseFirestore.getInstance())

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
                    .requiredHeight(200.dp)
                    .verticalScroll(scroll),
                style = TextStyle(fontFamily = lightFont, fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
            )


            LaunchedEffect(Unit) {
                scope.launch {
                    viewModel.migrationEvent.collect { event ->
                        when (event) {
                            is MigrationEvent.SendLog -> {
                                logString = event.msg
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