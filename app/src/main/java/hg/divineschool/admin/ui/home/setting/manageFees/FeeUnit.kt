package hg.divineschool.admin.ui.home.setting.manageFees

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hg.divineschool.admin.ui.home.dashboard.registerStudent.FormEditText
import hg.divineschool.admin.ui.theme.mediumFont


@Composable
fun FeeUnit(
    modifier: Modifier,
    editTextModifier: Modifier,
    name: TextFieldValue,
    textLable : String,
    onValueChanged: (value: TextFieldValue) -> Unit
) {
    Column(modifier = modifier) {
        Text(
            text = textLable, style = TextStyle(
                fontFamily = mediumFont,
                fontSize = 26.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            ), modifier = Modifier.padding(start = 8.dp, top = 4.dp)
        )
        FormEditText(textValue = name,
            text = "Enter Tuition Fee",
            keyboardType = KeyboardType.Text,
            color = Color.Black,
            modifier = editTextModifier,
            isEnabled = true,
            onValueChanged = { onValueChanged(it) })
    }
}