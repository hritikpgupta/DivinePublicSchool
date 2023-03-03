package hg.divineschool.admin.ui.home.dashboard.invoiceStudent

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hg.divineschool.admin.R
import hg.divineschool.admin.data.models.MonthFee
import hg.divineschool.admin.ui.theme.regularFont
import hg.divineschool.admin.ui.utils.getActivatedColor

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MonthSelectList(
    items: List<MonthFee>,
    color: Color,
    onMonthSelect: (list: List<MonthFee>) -> Unit
) {

    val selectedItems = remember { mutableStateListOf<MonthFee>() }
    LazyVerticalGrid(
        verticalArrangement = Arrangement.spacedBy(14.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        userScrollEnabled = true,
        contentPadding = PaddingValues(
            top = 15.dp, start = 10.dp, end = 10.dp, bottom = 80.dp
        ),
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background.copy(0.6f)),
        columns = GridCells.Adaptive(170.dp)
    ) {
        items(items) { item ->
            val isSelected = selectedItems.contains(item)
            val cardBgColor = remember { mutableStateOf(item.isPaid.getActivatedColor(color)) }
            Card(elevation = 6.dp,
                backgroundColor = MaterialTheme.colors.background.copy(1f),
                modifier = Modifier.requiredSize(width = 170.dp, height = 80.dp),
                onClick = {
                    if (!item.isPaid) {
                        if (isSelected) {
                            selectedItems.remove(item)
                            cardBgColor.value = Color.LightGray
                        } else {
                            selectedItems.add(item)
                            cardBgColor.value = color.copy(0.65f)
                        }
                        onMonthSelect(selectedItems.toList())
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
                    if (item.isPaid) {
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

@Composable
fun TableHeading(heading : String, modifier: Modifier) {
    Text(
        text = " $heading",
        modifier = modifier.border(0.5.dp, Color.LightGray, RectangleShape),
        textAlign = TextAlign.Start,
        style = TextStyle(fontFamily = regularFont, fontSize = 24.sp)
    )
}
@Composable
fun TableValue(value : String, modifier: Modifier) {
    Text(
        text = " $value",
        modifier = modifier.border(0.5.dp, Color.LightGray, RectangleShape),
        textAlign = TextAlign.Start,
        style = TextStyle(fontFamily = regularFont, fontSize = 24.sp)

    )
}

@Composable
fun StudentInformation(modifier: Modifier) {
    Card(
        backgroundColor = MaterialTheme.colors.background,
        elevation = 6.dp,
        modifier = modifier

    ) {
        Column(modifier = Modifier.padding(2.dp)) {
            Row {
                TableHeading(heading = "Name", modifier = Modifier.weight(1f))
                TableValue(value = "Hritik Gupta", modifier = Modifier.weight(2f))
            }
            Row {
                TableHeading(heading = "Father's Name", modifier = Modifier.weight(1f))
                TableValue(value = "Rakesh Kumar Gupta", modifier = Modifier.weight(2f))
            }
            Row {
                TableHeading(heading = "Class", modifier = Modifier.weight(1f))
                TableValue(value = "Play Group", modifier = Modifier.weight(2f))
            }
            Row {
                TableHeading(heading = "Transport Student", modifier = Modifier.weight(1f))
                TableValue(value = "Yes", modifier = Modifier.weight(2f))
            }
            Row {
                TableHeading(heading = "New Student", modifier = Modifier.weight(1f))
                TableValue(value = "No", modifier = Modifier.weight(2f))
            }

        }
    }

}