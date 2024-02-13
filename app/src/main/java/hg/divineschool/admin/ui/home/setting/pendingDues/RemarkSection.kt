package hg.divineschool.admin.ui.home.setting.pendingDues

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hg.divineschool.admin.ui.theme.regularFont

@Composable
fun RemarkSection(
    modifier: Modifier,
    remarks: List<String>,
    submitRemark: (String) -> Unit,
    settleInvoice: () -> Unit
) {
    var remarkMessage by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        verticalArrangement = Arrangement.Top,
        modifier = modifier
            .fillMaxSize()
            .background(Color.LightGray.copy(0.5f))
            .padding(top = 20.dp, end = 4.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(280.dp)
                .background(Color.White)
                .padding(top = 5.dp, start = 0.dp, end = 0.dp)
        ) {
            items(remarks) { msg ->
                Text(
                    text = msg,
                    style = TextStyle(
                        fontFamily = regularFont, fontSize = 13.sp, fontWeight = FontWeight.SemiBold
                    ),
                    color = Color.DarkGray,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(2.dp)
                )
                Divider(
                    thickness = 1.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 2.dp, bottom = 1.dp)
                )
            }
        }
        OutlinedTextField(
            value = remarkMessage,
            onValueChange = { remarkMessage = it },
            label = { Text(text = "Add Remark") },
            maxLines = 3,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text,
                autoCorrect = false,
                capitalization = KeyboardCapitalization.None,
            ),
            textStyle = TextStyle(
                fontFamily = regularFont, fontSize = 13.sp, fontWeight = FontWeight.SemiBold
            ),
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(80.dp)
                .padding(top = 5.dp, start = 0.dp, end = 0.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = {
                if (remarkMessage.text.isNotEmpty()) {
                    submitRemark(remarkMessage.text)
                    remarkMessage = TextFieldValue("")
                }
            }) {
                Icon(
                    Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Add Remark",
                    modifier = Modifier.requiredSize(30.dp)
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    settleInvoice()
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray),
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 5.dp, pressedElevation = 5.dp, disabledElevation = 0.dp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 0.dp, end = 0.dp)
            ) {
                Text(
                    text = "Settle Invoice",
                    color = Color.White,
                    style = TextStyle(fontSize = 15.sp)
                )
            }
        }
    }
}