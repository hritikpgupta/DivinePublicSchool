package hg.divineschool.admin.ui.home.dashboard.invoiceStudent

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.ContentAlpha
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import hg.divineschool.admin.data.models.Student
import hg.divineschool.admin.data.models.Supplement
import hg.divineschool.admin.data.utils.toYesOrNo
import hg.divineschool.admin.ui.theme.boldFont
import hg.divineschool.admin.ui.theme.lightFont
import hg.divineschool.admin.ui.theme.mediumFont
import hg.divineschool.admin.ui.theme.regularFont
import hg.divineschool.admin.ui.utils.INR
import hg.divineschool.admin.ui.utils.convertIdToName

@Composable
fun MonthSelectList(
    items: List<MonthFee>, color: Color, onMonthSelect: (list: List<MonthFee>) -> Unit
) {

    val selectedItems = remember { mutableStateListOf<MonthFee>() }
    LazyVerticalGrid(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(0.dp),
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
            MonthCard(month = item,
                isSelected = selectedItems.contains(item),
                color = color,
                onSelected = {
                    if (!item.isPaid) {
                        if (selectedItems.contains(item)) {
                            selectedItems.remove(item)
                        } else {
                            selectedItems.add(item)
                        }
                    }
                    onMonthSelect(selectedItems.toList())
                })
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MonthCard(month: MonthFee, isSelected: Boolean, color: Color, onSelected: () -> Unit) {
    val backgroundColor = if (month.isPaid) {
        color
    } else {
        if (isSelected) {
            color.copy(0.65f)
        } else {
            Color.LightGray
        }
    }
    Card(elevation = 6.dp,
        backgroundColor = MaterialTheme.colors.background.copy(1f),
        modifier = Modifier.requiredSize(width = 140.dp, height = 50.dp),
        onClick = {
            onSelected()
        }) {
        Box(contentAlignment = Alignment.TopEnd) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp)
                    .background(color = backgroundColor)
            ) {
                Text(
                    text = month.month, style = TextStyle(
                        textAlign = TextAlign.Center,
                        fontFamily = regularFont,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
                    ), color = Color.White, modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp)
                )

            }
            if (month.isPaid) {
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


@Composable
fun TableHeading(heading: String, modifier: Modifier) {
    Text(
        text = " $heading",
        modifier = modifier.border(1.dp, Color.LightGray.copy(0.5f), RectangleShape),
        textAlign = TextAlign.Start,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        style = TextStyle(fontFamily = lightFont, fontSize = 20.sp)
    )
}

@Composable
fun TableHeading2(heading: String, modifier: Modifier, color: Color) {
    Text(
        text = " $heading",
        color = color,
        modifier = modifier.border(1.dp, Color.LightGray.copy(0.5f), RectangleShape),
        textAlign = TextAlign.Center,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        style = TextStyle(fontFamily = boldFont, fontSize = 22.sp)
    )
}

@Composable
fun TableValue2(value: String, modifier: Modifier) {
    Text(
        text = value,
        modifier = modifier.border(1.dp, Color.LightGray.copy(0.5f), RectangleShape),
        textAlign = TextAlign.End,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        style = TextStyle(fontFamily = mediumFont, fontSize = 22.sp, fontWeight = FontWeight.Bold)

    )
}

@Composable
fun TableValue(value: String, modifier: Modifier, align: TextAlign) {
    Text(
        text = " $value",
        modifier = modifier.border(1.dp, Color.LightGray.copy(0.5f), RectangleShape),
        textAlign = align,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        style = TextStyle(fontFamily = lightFont, fontSize = 20.sp)

    )
}


@Composable
fun InvoiceCheckBoxes(
    text: String, color: Color, value: Boolean, onChecked: (b: Boolean) -> Unit, modifier: Modifier
) {
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
fun StudentInformation(student: Student, classID: String) {

    Row {
        TableHeading(heading = "Name", modifier = Modifier.weight(0.3f))
        TableValue(
            value = "${student.firstName} ${student.lastName}",
            modifier = Modifier.weight(0.7f),
            align = TextAlign.Start
        )
    }
    Row {
        TableHeading(heading = "Father", modifier = Modifier.weight(0.3f))
        TableValue(
            value = student.fathersName, modifier = Modifier.weight(0.7f), align = TextAlign.Start
        )
    }
    Row {
        TableHeading(heading = "Class", modifier = Modifier.weight(0.3f))
        TableValue(
            value = classID.convertIdToName(),
            modifier = Modifier.weight(0.7f),
            align = TextAlign.Start
        )
    }
    Row {
        TableHeading(heading = "Roll No", modifier = Modifier.weight(0.3f))
        TableValue(
            value = "${student.rollNumber}",
            modifier = Modifier.weight(0.7f),
            align = TextAlign.Start
        )
    }
    Row {
        TableHeading(heading = "Transport", modifier = Modifier.weight(0.3f))
        TableValue(
            value = student.transportStudent.toYesOrNo(),
            modifier = Modifier.weight(0.7f),
            align = TextAlign.Start
        )
    }
    Row {
        TableHeading(heading = "New", modifier = Modifier.weight(0.3f))
        TableValue(
            value = student.newStudent.toYesOrNo(),
            modifier = Modifier.weight(0.7f),
            align = TextAlign.Start
        )
    }

}

fun List<Supplement>.getString(): String {
    if (this.isNotEmpty()) {
        var stringBuilder = StringBuilder()
        this.forEach { supplement ->
            stringBuilder.append(supplement.itemName + ", ")
        }
        return stringBuilder.toString()
    } else {
        return "Select Accessory & Supplies"
    }
}

@Composable
fun AccessoryDropdown(
    items: List<Supplement>,
    selectedItems: MutableState<List<Supplement>>,
    onItemsSelected: (list: List<Supplement>) -> Unit,
    color: Color,
    modifier: Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.clickable { expanded = true }) {
            Text(
                if (selectedItems.value.isEmpty()) {
                    "Select Accessory & Supplies"
                } else {
                    selectedItems.value.getString()
                },
                color = if (selectedItems.value.isEmpty()) {
                    MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium)
                } else {
                    MaterialTheme.colors.onSurface
                },
                modifier = Modifier.weight(1f),
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(fontFamily = mediumFont, fontSize = 28.sp),
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
            onDismissRequest = { expanded = false },
            modifier = Modifier.requiredWidth(400.dp)

        ) {
            items.forEach { item ->
                DropdownMenuItem(onClick = {
                    if (selectedItems.value.contains(item)) {
                        selectedItems.value = selectedItems.value - item
                        onItemsSelected(selectedItems.value)
                    } else {
                        selectedItems.value = selectedItems.value + item
                        onItemsSelected(selectedItems.value)
                    }
                }) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(0.8f)
                        ) {
                            Checkbox(
                                checked = selectedItems.value.contains(item),
                                onCheckedChange = null,
                                colors = CheckboxDefaults.colors(checkedColor = color),
                                modifier = Modifier.padding(end = 16.dp)
                            )
                            Text(
                                text = item.itemName, style = TextStyle(
                                    fontFamily = mediumFont,
                                    fontSize = 30.sp,
                                    textAlign = TextAlign.Start
                                )
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(0.3f)
                        ) {
                            Text(
                                text = "$INR ${item.price}", style = TextStyle(
                                    fontFamily = mediumFont,
                                    fontSize = 30.sp,
                                    textAlign = TextAlign.End
                                )
                            )
                        }
                    }


                }
            }
        }
    }
}

@Composable
fun BookSelectList(
    items: List<Book>, color: Color, onBookSelect: (list: List<Book>) -> Unit
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
            BookCard(book = item,
                isSelected = selectedItems.contains(item),
                color = color,
                onSelected = {
                    if (selectedItems.contains(item)) {
                        selectedItems.remove(item)
                    } else {
                        selectedItems.add(item)
                    }
                    onBookSelect(selectedItems.toList())
                })

        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BookCard(book: Book, isSelected: Boolean, color: Color, onSelected: () -> Unit) {
    val backgroundColor = if (isSelected) color.copy(0.65f) else Color.LightGray
    Card(elevation = 6.dp,
        backgroundColor = MaterialTheme.colors.background.copy(1f),
        modifier = Modifier.requiredSize(width = 220.dp, height = 90.dp),
        onClick = {onSelected()}) {
        Box(contentAlignment = Alignment.TopEnd) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp)
                    .background(color = backgroundColor)
            ) {
                Text(
                    text = book.bookName,
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
                    text = "$INR ${book.bookPrice}", style = TextStyle(
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


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DestinationSelectList(
    items: List<Place>, color: Color, onDestinationSelect: (place: Place?) -> Unit
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
                    if (selectedItem == item) {
                        selectedItem = null
                        onDestinationSelect(null)
                    } else {
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
