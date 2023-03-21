package hg.divineschool.admin.ui.home.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hg.divineschool.admin.ui.home.DPSAppBar

@Composable
fun SettingScreen(navController: NavController) {

    val settings = listOf("Check Dues", "Manage Fees", "Transaction Summary", "Manage Books")
    val scrollState = rememberScrollState()
    Scaffold(topBar = { DPSAppBar() })
    { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(color = MaterialTheme.colors.background.copy(0.8f))
        ) {
            StatisticCard()
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
                items(settings) { str ->
                    Text(text = str)
                }
            }

        }
    }
}
