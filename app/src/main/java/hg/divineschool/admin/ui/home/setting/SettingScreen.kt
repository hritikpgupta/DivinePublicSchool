package hg.divineschool.admin.ui.home.setting

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import hg.divineschool.admin.AppScreen
import hg.divineschool.admin.BottomNavItem
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.ui.home.DPSAppBar
import hg.divineschool.admin.ui.theme.regularFont
import hg.divineschool.admin.ui.utils.CircularProgress
import hg.divineschool.admin.ui.utils.decideSettingMenu
import hg.divineschool.admin.ui.utils.toast

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SettingScreen(navController: NavController, viewModel: SettingViewModel) {

    val isAdmin = FirebaseAuth.getInstance().currentUser?.email.equals("admin@dps.com")
    val state = viewModel.studentCountFlow.collectAsState()
    var totalStudent by remember { mutableStateOf("Loading...") }
    var transportStudent by remember { mutableStateOf("Loading...") }
    var rteStudent by remember { mutableStateOf("Loading...") }
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.getStudentsCount()
    }

    Scaffold(topBar = { DPSAppBar() }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(color = MaterialTheme.colors.background.copy(0.8f))
        ) {
            StatisticCard(totalStudent, transportStudent, rteStudent, {
                viewModel.updateSchoolOpenState(it)
            }, {
                viewModel.updateStartTime(it)
            }, {
                viewModel.updateEndTime(it)
            })
            LazyVerticalGrid(
                verticalArrangement = Arrangement.spacedBy(18.dp),
                userScrollEnabled = true,
                contentPadding = PaddingValues(
                    top = 15.dp, start = 8.dp, end = 8.dp, bottom = 80.dp
                ),
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colors.background.copy(0.6f)),
                columns = GridCells.Adaptive(280.dp)
            ) {
                items(isAdmin.decideSettingMenu()) { str ->
                    val cardModifier = if (str.enabled) {
                        Modifier
                            .requiredWidth(280.dp)
                            .requiredHeight(150.dp)
                    } else {
                        Modifier
                            .requiredWidth(280.dp)
                            .requiredHeight(150.dp)
                            .drawWithContent {
                                drawContent()
                                drawRect(color = Color.LightGray.copy(0.7f))
                            }
                    }
                    Card(backgroundColor = Color.LightGray.copy(0.2f),
                        shape = RoundedCornerShape(4.dp),
                        elevation = 2.dp,
                        enabled = str.enabled,
                        border = BorderStroke(4.dp, Color.Black.copy(0.6f)),
                        modifier = cardModifier,
                        onClick = {
                            when (str.id) {
                                1 -> {
                                    navController.navigate(AppScreen.SettingScreen.CheckDues.route)
                                }
                                2 -> {
                                    navController.navigate(AppScreen.SettingScreen.Transaction.route)
                                }
                                3 -> {
                                    navController.navigate(AppScreen.SettingScreen.ManageFees.route)
                                }
                                4 -> {
                                    navController.navigate(AppScreen.SettingScreen.ManageBooks.route)
                                }
                                5 -> {
                                    navController.navigate(AppScreen.SettingScreen.ManageLocation.route)
                                }
                                6 -> {
                                    FirebaseAuth.getInstance().signOut()
                                    navController.navigate(AppScreen.SettingScreen.LogOut.route) {
                                        popUpTo(BottomNavItem.Home.route) { inclusive = true }
                                    }
                                }
                            }
                        }) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(start = 4.dp)
                        ) {
                            Image(
                                painter = painterResource(id = str.icon),
                                contentDescription = str.name,
                                modifier = Modifier.requiredSize(80.dp)
                            )
                            Spacer(modifier = Modifier.requiredHeight(12.dp))
                            Text(
                                text = str.name, fontSize = 30.sp, style = TextStyle(
                                    fontFamily = regularFont, fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }
            }
        }
        state.value.let {
            when (it) {
                is Resource.Failure -> {
                    it.exception.message?.let { it1 -> context.toast(it1) }
                }
                is Resource.Success -> {
                    it.result.apply {
                        totalStudent = "$first"
                        transportStudent = "$second"
                        rteStudent = "$third"
                    }
                }
                else -> {}
            }
        }
    }
}
