package hg.divineschool.admin.ui.home.dashboard.invoiceStudent

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.insets.ui.Scaffold
import hg.divineschool.admin.R
import hg.divineschool.admin.data.models.MonthFee
import hg.divineschool.admin.ui.home.DPSBar
import hg.divineschool.admin.ui.theme.cardColors
import hg.divineschool.admin.ui.theme.mediumFont
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
            Row() {
                StudentInformation(modifier = Modifier.weight(1f))
                InvoiceCheckBoxes(modifier = Modifier.weight(1f), classColor)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 2.dp)
                    .wrapContentWidth(Alignment.Start)
            ) {
                MonthSelectList(items, classColor) {
                }
            }

        }
    }
}

@Composable
fun InvoiceCheckBoxes(modifier: Modifier, color : Color) {
    Column(modifier = modifier) {
        Row {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "Admission Fee",
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(fontFamily = mediumFont, fontSize = 30.sp),
                    maxLines = 1,
                    softWrap = true,
                )
                Spacer(modifier = Modifier.width(2.dp))
                Checkbox(
                    checked = true,
                    modifier = Modifier.requiredSize(60.dp),
                    colors = CheckboxDefaults.colors(checkedColor = color),
                    onCheckedChange = {  },
                )
            }
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "ID & Fee Card",
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(fontFamily = mediumFont, fontSize = 30.sp),
                    maxLines = 1,
                    softWrap = true,
                )
                Spacer(modifier = Modifier.width(2.dp))
                Checkbox(
                    checked = true,
                    modifier = Modifier.requiredSize(60.dp),
                    colors = CheckboxDefaults.colors(checkedColor = color),
                    onCheckedChange = {  },
                )
            }
        }
        Row {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "Junior Tie",
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(fontFamily = mediumFont, fontSize = 30.sp),
                    maxLines = 1,
                    softWrap = true,
                )
                Spacer(modifier = Modifier.width(2.dp))
                Checkbox(
                    checked = true,
                    modifier = Modifier.requiredSize(60.dp),
                    colors = CheckboxDefaults.colors(checkedColor = color),
                    onCheckedChange = {  },
                )
            }
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "Senior Fee",
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(fontFamily = mediumFont, fontSize = 30.sp),
                    maxLines = 1,
                    softWrap = true,
                )
                Spacer(modifier = Modifier.width(2.dp))
                Checkbox(
                    checked = true,
                    modifier = Modifier.requiredSize(60.dp),
                    colors = CheckboxDefaults.colors(checkedColor = color),
                    onCheckedChange = {  },
                )
            }
        }
        Row {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "Diary",
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(fontFamily = mediumFont, fontSize = 30.sp),
                    maxLines = 1,
                    softWrap = true,
                )
                Spacer(modifier = Modifier.width(2.dp))
                Checkbox(
                    checked = true,
                    modifier = Modifier.requiredSize(60.dp),
                    colors = CheckboxDefaults.colors(checkedColor = color),
                    onCheckedChange = {  },
                )
            }
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "Belt",
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(fontFamily = mediumFont, fontSize = 30.sp),
                    maxLines = 1,
                    softWrap = true,
                )
                Spacer(modifier = Modifier.width(2.dp))
                Checkbox(
                    checked = true,
                    modifier = Modifier.requiredSize(60.dp),
                    colors = CheckboxDefaults.colors(checkedColor = color),
                    onCheckedChange = {  },
                )
            }
        }
    }
}







