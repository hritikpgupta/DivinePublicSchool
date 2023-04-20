package hg.divineschool.admin.ui.home.dashboard.invoiceWebView.invoiceUIUnit

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hg.divineschool.admin.ui.theme.mediumFont

@Composable
fun ColumnHeader(months: String, date: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(45.dp)
                .weight(0.5f)

        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 10.dp)
            ) {
                Text(
                    text = "Month(s)",
                    modifier = Modifier
                        .padding(start = 0.dp, bottom = 0.dp)
                        .wrapContentWidth(),
                    color = Color.Black,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = mediumFont,
                        fontWeight = FontWeight.SemiBold,
                        textDecoration = TextDecoration.Underline,
                        fontStyle = FontStyle.Normal
                    )
                )
                Text(
                    text = months,
                    modifier = Modifier
                        .padding(start = 0.dp, bottom = 0.dp)
                        .wrapContentWidth(),
                    color = Color.Black,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = mediumFont,
                        fontWeight = FontWeight.SemiBold,
                        fontStyle = FontStyle.Normal
                    )
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(45.dp)
                .weight(0.5f)

        ) {
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 10.dp)
            ) {
                Text(
                    text = "Date",
                    modifier = Modifier
                        .padding(start = 0.dp, bottom = 0.dp)
                        .wrapContentWidth(),
                    color = Color.Black,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = mediumFont,
                        fontWeight = FontWeight.SemiBold,
                        textDecoration = TextDecoration.Underline,
                        fontStyle = FontStyle.Normal
                    )
                )
                Text(
                    text = date,
                    modifier = Modifier
                        .padding(start = 0.dp, bottom = 0.dp)
                        .wrapContentWidth(),
                    color = Color.Black,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = mediumFont,
                        fontWeight = FontWeight.SemiBold,
                        fontStyle = FontStyle.Normal
                    )
                )
            }
        }
    }

}