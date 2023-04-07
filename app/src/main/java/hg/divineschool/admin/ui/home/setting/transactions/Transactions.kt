package hg.divineschool.admin.ui.home.setting.transactions

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.insets.ui.Scaffold
import hg.divineschool.admin.ui.home.DPSBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Transactions(navController: NavController) {
    val dateRangeState = rememberDateRangePickerState()


    Scaffold(scaffoldState = rememberScaffoldState(), topBar = {
        DPSBar(onBackPressed = {
            navController.popBackStack()
        }, className = "Transactions")
    }) {
        DateRangePicker(state = dateRangeState,
            modifier = Modifier
                .fillMaxHeight()
                .requiredWidth(500.dp)
                .padding(top = 60.dp, start = 0.dp, bottom = 0.dp))

    }
}