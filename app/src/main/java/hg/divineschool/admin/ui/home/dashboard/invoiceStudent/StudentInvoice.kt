package hg.divineschool.admin.ui.home.dashboard.invoiceStudent

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.insets.ui.Scaffold
import hg.divineschool.admin.data.models.MonthFee
import hg.divineschool.admin.ui.home.DPSBar
import hg.divineschool.admin.ui.home.dashboard.registerStudent.FormRow
import hg.divineschool.admin.ui.theme.cardColors
import hg.divineschool.admin.ui.utils.Log_Tag

@Composable
fun StudentInvoice(
    classID: String,
    className: String,
    scholarNumber: String,
    navController: NavController,
    viewModel: StudentInvoiceViewModel
) {
    val scrollState = rememberScrollState()
    val classColor = cardColors[classID.toInt()]
    Log.i(Log_Tag, classID) //0
    Log.i(Log_Tag, className) // Play Group
    Log.i(Log_Tag, scholarNumber) // 2547

    Scaffold(scaffoldState = rememberScaffoldState(), topBar = {
        DPSBar(onBackPressed = { navController.popBackStack() }, className = "Generate Bill")
    }) {
        val items = listOf(
            MonthFee(isPaid = false, month = "January"),
            MonthFee(isPaid = false, month = "February"),
            MonthFee(isPaid = false, month = "March"),
            MonthFee(isPaid = true, month = "April"),
            MonthFee(isPaid = true, month = "May"),
            MonthFee(isPaid = true, month = "June"),
            MonthFee(isPaid = false, month = "July"),
            MonthFee(isPaid = false, month = "August"),
            MonthFee(isPaid = false, month = "September"),
            MonthFee(isPaid = false, month = "October"),
            MonthFee(isPaid = false, month = "November"),
            MonthFee(isPaid = false, month = "December"),
        )
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(bottom = 65.dp, start = 5.dp, end = 5.dp)
                .background(color = MaterialTheme.colors.background.copy(0.8f))
        ) {

            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.requiredWidth(600.dp)) {
                StudentInformation(modifier = Modifier.weight(1f))
            }
            FormRow(padding = 24) {
                InvoiceCheckBoxes(
                    text = "Admission Fee", classColor, modifier = Modifier.weight(1f)
                )
                InvoiceCheckBoxes(
                    text = "ID & Fee Card Fee", classColor, modifier = Modifier.weight(1f)
                )
                InvoiceCheckBoxes(text = "Diary Fee", classColor, modifier = Modifier.weight(1f))
            }
            FormRow(padding = 24) {
                InvoiceCheckBoxes(text = "Belt Fee", classColor, modifier = Modifier.weight(1f))
                InvoiceCheckBoxes(
                    text = "Junior Tie Fee", classColor, modifier = Modifier.weight(1f)
                )
                InvoiceCheckBoxes(
                    text = "Senior Tie Fee", classColor, modifier = Modifier.weight(1f)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 2.dp)
                    .wrapContentWidth(Alignment.Start)
            ) {
                MonthSelectList(items, classColor) {}
            }

        }
    }
}









