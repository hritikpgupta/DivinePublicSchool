package hg.divineschool.admin.ui.home.dashboard.registerStudent

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hg.divineschool.admin.R
import hg.divineschool.admin.ui.theme.mediumFont
import hg.divineschool.admin.ui.theme.regularFont
import kotlinx.coroutines.launch

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

val dropDownModifier = Modifier
    .fillMaxWidth()
    .padding(horizontal = 10.dp)

@Composable
fun FormEditText(
    textValue: TextFieldValue,
    text: String,
    keyboardType: KeyboardType,
    color: Color,
    modifier: Modifier,
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
        onValueChange = { onValueChanged(it) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = color,
            cursorColor = color,
        ),
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
    onTransportChange: (status: Boolean) -> Unit,
    onNewStudentChange: (status: Boolean) -> Unit,
    onIsOrphanChange: (status: Boolean) -> Unit
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
            Text(
                text = "Transport", style = TextStyle(fontFamily = regularFont, fontSize = 30.sp)
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
            Text(
                text = "New Student", style = TextStyle(fontFamily = mediumFont, fontSize = 30.sp)
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
            Text(
                text = "Orphan", style = TextStyle(fontFamily = mediumFont, fontSize = 30.sp)
            )
            Checkbox(
                checked = isOrphan,
                modifier = Modifier.requiredSize(60.dp),
                colors = CheckboxDefaults.colors(checkedColor = color),
                onCheckedChange = { onIsOrphanChange(it) },
            )
        }
    }

}

