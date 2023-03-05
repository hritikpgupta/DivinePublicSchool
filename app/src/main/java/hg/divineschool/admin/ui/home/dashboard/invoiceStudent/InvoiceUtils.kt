package hg.divineschool.admin.ui.home.dashboard.invoiceStudent

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hg.divineschool.admin.R
import hg.divineschool.admin.data.models.Book
import hg.divineschool.admin.data.models.MonthFee
import hg.divineschool.admin.data.models.Place
import hg.divineschool.admin.ui.theme.cardColors
import hg.divineschool.admin.ui.theme.mediumFont
import hg.divineschool.admin.ui.theme.regularFont
import hg.divineschool.admin.ui.utils.INR
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
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        userScrollEnabled = true,
        contentPadding = PaddingValues(
            top = 15.dp, start = 1.dp, end = 1.dp, bottom = 10.dp
        ),
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background.copy(0.6f)),
        columns = GridCells.Adaptive(150.dp)
    ) {
        items(items) { item ->
            val isSelected = selectedItems.contains(item)
            val cardBgColor = remember { mutableStateOf(item.isPaid.getActivatedColor(color)) }
            Card(elevation = 6.dp,
                backgroundColor = MaterialTheme.colors.background.copy(1f),
                modifier = Modifier.requiredSize(width = 140.dp, height = 50.dp),
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
                            .padding(4.dp)
                            .background(color = cardBgColor.value)
                    ) {
                        Text(
                            text = item.month, style = TextStyle(
                                textAlign = TextAlign.Center,
                                fontFamily = regularFont,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 20.sp
                            ), color = Color.White, modifier = Modifier
                                .fillMaxWidth()
                                .padding(6.dp)
                        )

                    }
                    if (item.isPaid) {
                        Image(
                            painter = painterResource(id = R.drawable.green_tick),
                            modifier = Modifier
                                .requiredSize(25.dp)
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
fun TableHeading(heading: String, modifier: Modifier) {
    Text(
        text = " $heading",
        modifier = modifier.border(1.dp, Color.LightGray.copy(0.5f), RectangleShape),
        textAlign = TextAlign.Start,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        style = TextStyle(fontFamily = regularFont, fontSize = 30.sp)
    )
}

@Composable
fun TableValue(value: String, modifier: Modifier) {
    Text(
        text = " $value",
        modifier = modifier.border(1.dp, Color.LightGray.copy(0.5f), RectangleShape),
        textAlign = TextAlign.Start,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        style = TextStyle(fontFamily = regularFont, fontSize = 30.sp)

    )
}

@Composable
fun InvoiceCheckBoxes(text: String, color: Color, value :Boolean, onChecked :(b : Boolean) ->Unit ,modifier: Modifier) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(horizontal = 20.dp)
    ) {
        androidx.compose.material3.Text(
            text = text,
            overflow = TextOverflow.Ellipsis,
            style = TextStyle(fontFamily = mediumFont, fontSize = 30.sp),
            maxLines = 1,
            softWrap = true,
        )
        Spacer(modifier = Modifier.fillMaxWidth())
        Checkbox(
            checked = value,
            modifier = Modifier.requiredSize(30.dp),
            colors = CheckboxDefaults.colors(checkedColor = color),
            onCheckedChange = { onChecked(it) },
        )
    }

}

@Composable
fun StudentInformation(modifier: Modifier) {

    Card(
        elevation = 8.dp, modifier = modifier
            .padding(4.dp)
            .border(
                3.dp, cardColors[0],
                RectangleShape
            )
    ) {
        Column(modifier = modifier.padding(4.dp)) {
            Text(
                text = "Student Information",
                style = TextStyle(fontFamily = mediumFont, fontSize = 28.sp),
                textAlign = TextAlign.Center
            )
            Divider(thickness = 1.dp, color = Color.DarkGray.copy(0.4f))
            Row {
                TableHeading(heading = "Name", modifier = Modifier.weight(1f))
                TableValue(value = "Hritik Gupta", modifier = Modifier.weight(1f))
            }
            Row {
                TableHeading(heading = "Father's Name", modifier = Modifier.weight(1f))
                TableValue(value = "Rakesh Kumar Gupta", modifier = Modifier.weight(1f))
            }
            Row {
                TableHeading(heading = "Class", modifier = Modifier.weight(1f))
                TableValue(value = "Play Group", modifier = Modifier.weight(1f))
            }
            Row {
                TableHeading(heading = "Transport Student", modifier = Modifier.weight(1f))
                TableValue(value = "Yes", modifier = Modifier.weight(1f))
            }
            Row {
                TableHeading(heading = "New Student", modifier = Modifier.weight(1f))
                TableValue(value = "No", modifier = Modifier.weight(1f))
            }

        }
    }


}

@Composable
fun AccessoryDropdown(
    items: List<String>,
    selectedItems: MutableState<List<String>>,
    color: Color,
    modifier: Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.clickable { expanded = true }
        ) {
            Text(
                if (selectedItems.value.isEmpty()) {
                    "Select Accessory & Supplies"
                } else {
                    selectedItems.value.joinToString(", ")
                },
                color = if (selectedItems.value.isEmpty()) {
                    MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium)
                } else {
                    MaterialTheme.colors.onSurface
                },
                modifier = Modifier.weight(1f),
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(fontFamily = mediumFont, fontSize = 30.sp),
                maxLines = 1,
                softWrap = true,
            )
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = null,
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        if (selectedItems.value.contains(item)) {
                            selectedItems.value = selectedItems.value - item
                        } else {
                            selectedItems.value = selectedItems.value + item
                        }
                        println(selectedItems.value)
                    }
                ) {
                    Checkbox(
                        checked = selectedItems.value.contains(item),
                        onCheckedChange = null,
                        colors = CheckboxDefaults.colors(checkedColor = color),
                        modifier = Modifier.padding(end = 16.dp)
                    )
                    Text(text = item, style = TextStyle(fontFamily = mediumFont, fontSize = 30.sp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BookSelectList(
    items: List<Book>,
    color: Color,
    onBookSelect: (list: List<Book>) -> Unit
) {

    val selectedItems = remember { mutableStateListOf<Book>() }
    LazyVerticalGrid(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        userScrollEnabled = true,
        contentPadding = PaddingValues(
            top = 15.dp, start = 1.dp, end = 1.dp, bottom = 10.dp
        ),
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background.copy(0.6f)),
        columns = GridCells.Adaptive(220.dp)
    ) {
        items(items) { item ->
            val isSelected = selectedItems.contains(item)
            val cardBgColor = remember { mutableStateOf(Color.LightGray) }
            Card(elevation = 6.dp,
                backgroundColor = MaterialTheme.colors.background.copy(1f),
                modifier = Modifier.requiredSize(width = 220.dp, height = 90.dp),
                onClick = {
                    if (isSelected) {
                        selectedItems.remove(item)
                        cardBgColor.value = Color.LightGray
                    } else {
                        selectedItems.add(item)
                        cardBgColor.value = color.copy(0.65f)
                    }
                    onBookSelect(selectedItems.toList())

                }) {
                Box(contentAlignment = Alignment.TopEnd) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(4.dp)
                            .background(color = cardBgColor.value)
                    ) {
                        Text(
                            text = item.bookName,
                            overflow = TextOverflow.Ellipsis,

                            style = TextStyle(
                                textAlign = TextAlign.Center,
                                fontFamily = regularFont,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 24.sp
                            ),
                            color = Color.White,
                            maxLines = 1,
                            softWrap = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(6.dp)
                        )
                        Text(
                            text = "$INR ${item.bookPrice}", style = TextStyle(
                                textAlign = TextAlign.Center,
                                fontFamily = regularFont,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 22.sp
                            ), color = Color.White, modifier = Modifier
                                .fillMaxWidth()
                                .padding(6.dp)
                        )

                    }
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DestinationSelectList(
    items: List<Place>,
    color: Color,
    onDestinationSelect: (place: Place?) -> Unit
) {

    var selectedItem: Place? by remember { mutableStateOf(null) }
    LazyVerticalGrid(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        userScrollEnabled = true,
        contentPadding = PaddingValues(
            top = 15.dp, start = 1.dp, end = 1.dp, bottom = 10.dp
        ),
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background.copy(0.6f)),
        columns = GridCells.Adaptive(220.dp)
    ) {
        items(items) { item ->

            Card(elevation = 6.dp,
                backgroundColor = MaterialTheme.colors.background.copy(1f),
                modifier = Modifier.requiredSize(width = 220.dp, height = 90.dp),
                onClick = {
                    if (selectedItem == item ){
                        selectedItem = null
                        onDestinationSelect(null)
                    }else{
                        selectedItem = item
                        onDestinationSelect(selectedItem!!)
                    }

                }) {
                Box(contentAlignment = Alignment.TopEnd) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(4.dp)
                            .background(
                                color = if (selectedItem == item) {
                                    color.copy(0.65f)
                                } else if (selectedItem == null) {
                                    Color.LightGray
                                } else {
                                    Color.LightGray
                                }
                            )
                    ) {
                        Text(
                            text = item.placeName,
                            overflow = TextOverflow.Ellipsis,

                            style = TextStyle(
                                textAlign = TextAlign.Center,
                                fontFamily = regularFont,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 24.sp
                            ),
                            color = Color.White,
                            maxLines = 1,
                            softWrap = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(6.dp)
                        )
                        Text(
                            text = "$INR ${item.placePrice}", style = TextStyle(
                                textAlign = TextAlign.Center,
                                fontFamily = regularFont,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 22.sp
                            ), color = Color.White, modifier = Modifier
                                .fillMaxWidth()
                                .padding(6.dp)
                        )

                    }
                }
            }

        }
    }
}
