package hg.divineschool.admin.ui.home.dashboard.invoiceStudent

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.insets.ui.Scaffold
import hg.divineschool.admin.R
import hg.divineschool.admin.data.models.MonthFee
import hg.divineschool.admin.ui.home.DPSBar
import hg.divineschool.admin.ui.theme.cardColors
import hg.divineschool.admin.ui.theme.regularFont
import hg.divineschool.admin.ui.utils.getActivatedColor

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

    Scaffold(scaffoldState = rememberScaffoldState(), topBar = {
        DPSBar(onBackPressed = { navController.popBackStack() }, className = "Generate Bill")
    }) {
        val items = listOf(
            MonthFee(isPaid = true, month = "January"),
            MonthFee(isPaid = false, month = "Feb"),
            MonthFee(isPaid = false, month = "March"),
            MonthFee(isPaid = false, month = "April"),
            MonthFee(isPaid = false, month = "May"),
            MonthFee(isPaid = false, month = "June"),
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
            MultiSelectList(items, classColor)
        }
    }

}

//@Preview(showSystemUi = true, showBackground = true, device = Devices.TABLET)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MultiSelectList(items: List<MonthFee>, color: Color) {
    //items: List<MonthFee> ,color: Color
/*    val items =
        listOf(
            MonthFee(isPaid = true, month = "January"),
            MonthFee(isPaid = false, month = "Feb"),
            MonthFee(isPaid = false, month = "March"),
            MonthFee(isPaid = false, month = "April"),
            MonthFee(isPaid = false, month = "May"),
            MonthFee(isPaid = false, month = "June"),
        )
    val color = cardColors[0]*/

    val selectedItems = remember { mutableStateListOf<MonthFee>() }
    LazyVerticalGrid(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        userScrollEnabled = true,
        contentPadding = PaddingValues(
            top = 15.dp, start = 10.dp, end = 10.dp, bottom = 80.dp
        ),
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background.copy(0.6f)),
        columns = GridCells.Adaptive(180.dp)
    ) {
        items(items) { item ->
            val isSelected = selectedItems.contains(item)
            val cardBgColor = remember { mutableStateOf(item.isPaid.getActivatedColor(color)) }
            Card(elevation = 6.dp,
                backgroundColor = MaterialTheme.colors.background.copy(1f),
                modifier = Modifier.requiredSize(width = 180.dp, height = 80.dp),
                onClick = {
                    if (!item.isPaid) {
                        if (isSelected) {
                            selectedItems.remove(item)
                            cardBgColor.value = Color.LightGray
                        } else {
                            selectedItems.add(item)
                            cardBgColor.value = color.copy(0.65f)
                        }
                    }
                }) {
                Box(contentAlignment = Alignment.TopEnd) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                            .background(color = cardBgColor.value)
                    ) {
                        Text(
                            text = item.month, style = TextStyle(
                                textAlign = TextAlign.Center,
                                fontFamily = regularFont,
                                fontSize = 24.sp
                            ), color = Color.White, modifier = Modifier
                                .fillMaxWidth()
                                .padding(6.dp)
                        )
                    }
                    if (item.isPaid){
                        Image(
                            painter = painterResource(id = R.drawable.green_tick),
                            modifier = Modifier
                                .requiredSize(35.dp)
                                .padding(start = 0.dp, end = 0.dp, top = 0.dp, bottom = 0.dp)
                                .clip(CircleShape),
                            contentDescription = ""
                        )
                    }
                }
            }

        }
    }
}




