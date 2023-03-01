package hg.divineschool.admin.ui.home.dashboard.registerStudent

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hg.divineschool.admin.R
import hg.divineschool.admin.ui.theme.mediumFont
import hg.divineschool.admin.ui.theme.regularFont
import java.time.LocalDate
import java.time.Month

val genderOptions = listOf("Boy", "Girl")
val religionOptions = listOf("Buddhism", "Christianity", "Hinduism", "Islam", "Jainism", "Sikhism")
val classEntryOptions = listOf(
    "PLay Group",
    "Lower Nursery",
    "Upper Nursery",
    "Class One",
    "Class Two",
    "Class Three",
    "Class Four",
    "Class Five",
    "Class Six",
    "Class Seven",
    "Class Eight"
)


fun String.toLocalDate(): LocalDate {
    return if (this.isEmpty() || this.isBlank()) {
        LocalDate.now()
    } else {
        val dateList = this.trim().split(" ")
        LocalDate.of(
            dateList[2].trim().toInt(),
            decideMonth(dateList[0].trim()), dateList[1].trim().toInt()
        )
    }
}

fun decideMonth(string: String): Month {
    return when (string) {
        "Jan" -> {
            Month.JANUARY
        }
        "Feb" -> {
            Month.FEBRUARY
        }
        "Mar" -> {
            Month.MARCH
        }
        "Apr" -> {
            Month.APRIL
        }
        "May" -> {
            Month.MAY
        }
        "Jun" -> {
            Month.JUNE
        }
        "Jul" -> {
            Month.JULY
        }
        "Aug" -> {
            Month.AUGUST
        }
        "Sep" -> {
            Month.SEPTEMBER
        }
        "Oct" -> {
            Month.OCTOBER
        }
        "Nov" -> {
            Month.NOVEMBER
        }
        else -> {
            Month.DECEMBER
        }
    }
}

fun List<String>.getValue(value: String): String {
    return if (value == null || value.isEmpty() || value.isBlank()) {
        this[0]
    } else {
        if (this.indexOf(value) == -1) {
            this[0]
        } else {
            this[this.indexOf(value)]
        }
    }
}

val dropDownModifier = Modifier
    .fillMaxWidth()
    .padding(horizontal = 10.dp)

@Composable
fun decideTextFieldColors(b: Boolean, color: Color): TextFieldColors {
    return if (b) {
        TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = color,
            cursorColor = color,
        )
    } else {
        TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = color,
            focusedLabelColor = color,
            unfocusedBorderColor = color,
            unfocusedLabelColor = color,
            leadingIconColor = color,
            cursorColor = color,
        )
    }
}

@Composable
fun FormEditText(
    textValue: TextFieldValue,
    text: String,
    keyboardType: KeyboardType,
    color: Color,
    modifier: Modifier,
    isEnabled: Boolean,
    onValueChanged: (value: TextFieldValue) -> Unit
) {
    OutlinedTextField(
        value = textValue,
        label = {
            Text(
                text = text, style = TextStyle(
                    fontFamily = mediumFont, fontSize = 20.sp, fontWeight = FontWeight.SemiBold
                )
            )
        },
        maxLines = 1,
        enabled = isEnabled,
        onValueChange = { onValueChanged(it) },
        colors = decideTextFieldColors(b = isEnabled, color = color),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            keyboardType = keyboardType,
            autoCorrect = false,
            capitalization = KeyboardCapitalization.None,
        ),
        modifier = modifier
    )

}

@Composable
fun FormRow(padding: Int, content: @Composable() (RowScope.() -> Unit)) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = padding.dp),
        content = content
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DropDown(
    lableText: String,
    expanded: Boolean,
    onExpand: (status: Boolean) -> Unit,
    onItemClick: (clickedValue: String) -> Unit,
    onDismiss: () -> Unit,
    values: List<String>,
    color: Color,
    selectedValue: String,
    modifier: Modifier
) {
    ExposedDropdownMenuBox(expanded = expanded,
        modifier = modifier,
        onExpandedChange = { onExpand(expanded) }) {
        TextField(
            readOnly = true,
            value = selectedValue,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { },
            label = {
                Text(
                    lableText, style = TextStyle(
                        textAlign = TextAlign.Center,
                        fontFamily = regularFont,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colors.onBackground
                    )
                )
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = color,
                cursorColor = color,
                trailingIconColor = color,
                focusedBorderColor = color,
                backgroundColor = MaterialTheme.colors.onBackground.copy(.05f)

            )
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { onDismiss() }) {
            values.forEach { selectionOption ->
                DropdownMenuItem(onClick = {
                    onItemClick(selectionOption)
                }) {
                    Text(
                        text = selectionOption, style = TextStyle(
                            textAlign = TextAlign.Center,
                            fontFamily = regularFont,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colors.onBackground
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun FormCheckboxes(
    color: Color,
    transportStudent: Boolean,
    newStudent: Boolean,
    isOrphan: Boolean,
    isRte: Boolean,
    onTransportChange: (status: Boolean) -> Unit,
    onNewStudentChange: (status: Boolean) -> Unit,
    onIsOrphanChange: (status: Boolean) -> Unit,
    onRteChange: (status: Boolean) -> Unit
) {
    FormRow(padding = 24) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Image(
                painter = painterResource(id = R.drawable.bus),
                contentDescription = "",
                modifier = Modifier.requiredSize(40.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Vehicle",
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(fontFamily = mediumFont, fontSize = 30.sp),
                maxLines = 1,
                softWrap = true,
            )
            Checkbox(
                checked = transportStudent,
                modifier = Modifier.requiredSize(60.dp),
                colors = CheckboxDefaults.colors(checkedColor = color),
                onCheckedChange = { onTransportChange(it) },
            )
        }
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Image(
                painter = painterResource(id = R.drawable.new_student),
                contentDescription = "",
                modifier = Modifier.requiredSize(40.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "New",
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(fontFamily = mediumFont, fontSize = 30.sp),
                maxLines = 1,
                softWrap = true,
            )
            Checkbox(
                checked = newStudent,
                modifier = Modifier.requiredSize(60.dp),
                colors = CheckboxDefaults.colors(checkedColor = color),
                onCheckedChange = { onNewStudentChange(it) },
            )
        }
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Image(
                painter = painterResource(id = R.drawable.orphan),
                contentDescription = "",
                modifier = Modifier.requiredSize(40.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Orphan",
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(fontFamily = mediumFont, fontSize = 30.sp),
                maxLines = 1,
                softWrap = true,
            )
            Checkbox(
                checked = isOrphan,
                modifier = Modifier.requiredSize(60.dp),
                colors = CheckboxDefaults.colors(checkedColor = color),
                onCheckedChange = { onIsOrphanChange(it) },
            )
        }
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Image(
                painter = painterResource(id = R.drawable.right_to_education),
                contentDescription = "",
                modifier = Modifier.requiredSize(40.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "RTE",
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(fontFamily = mediumFont, fontSize = 30.sp),
                maxLines = 1,
                softWrap = true,
            )
            Checkbox(
                checked = isRte,
                modifier = Modifier.requiredSize(60.dp),
                colors = CheckboxDefaults.colors(checkedColor = color),
                onCheckedChange = { onRteChange(it) },
            )
        }
    }

}

