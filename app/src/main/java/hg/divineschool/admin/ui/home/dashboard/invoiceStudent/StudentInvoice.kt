package hg.divineschool.admin.ui.home.dashboard.invoiceStudent

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
        val accessoryList = listOf("Junior Tie", "Senior Tie", "Diary", "Belt", "ID & Fee Card")
        val selectedAccessory = remember {
            mutableStateOf(emptyList<String>())
        }
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(bottom = 65.dp, start = 5.dp, end = 5.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.75f)

            ) {
                FormRow(padding = 12) {
                    Card(elevation = 2.dp, modifier = Modifier.padding(horizontal = 10.dp)) {
                        Row {
                            InvoiceCheckBoxes(
                                text = "Admission Fee", classColor, modifier = Modifier
                                    .weight(1f)
                                    .padding(6.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Row(modifier = Modifier.weight(2f)) {
                                AccessoryDropdown(
                                    items = accessoryList,
                                    selectedItems = selectedAccessory,
                                    color = classColor,
                                    modifier = Modifier.padding(6.dp)
                                )
                            }
                        }
                    }

                }
                FormRow(padding = 12) {
                    MonthSelectList(items, classColor) {}
                }
            }
            Divider(
                color = Color.LightGray, modifier = Modifier
                    .fillMaxHeight()
                    .width(6.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(.25f)
            ) {

            }
        }
    }
}









