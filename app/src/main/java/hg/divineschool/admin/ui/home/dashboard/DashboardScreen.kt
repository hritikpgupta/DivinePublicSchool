package hg.divineschool.admin.ui.home.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hg.divineschool.admin.AppScreen
import hg.divineschool.admin.BottomNavItem
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.ui.home.DPSAppBar
import hg.divineschool.admin.ui.theme.cardColors
import hg.divineschool.admin.ui.utils.toast

@Composable
fun DashboardScreen(viewModel: DashboardViewModel, navController: NavController) {

    val classListFlow = viewModel.classListFlow.collectAsState()
    val context = LocalContext.current

    Scaffold(topBar = { DPSAppBar() }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(color = MaterialTheme.colors.background.copy(0.8f))
                .wrapContentSize(Alignment.Center)
        ) {
            classListFlow.value.let {
                when (it) {
                    is Resource.Loading -> {
                        Box(
                            contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                    is Resource.Failure -> {
                        it.exception.message?.let { it1 -> context.toast(it1) }
                    }
                    is Resource.Success -> {
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
                            items(it.result) { classInfo ->
                                ClassCard(
                                    classInformation = classInfo,
                                    cardColor = cardColors[classInfo.id.toInt()]
                                ) { id, name ->
                                    navController.navigate(AppScreen.StudentScreen.StudentList.route + "/${id.toInt()}/$name")
                                };
                            }
                        }
                    }
                    else -> {}
                }
            }
        }
    }
}