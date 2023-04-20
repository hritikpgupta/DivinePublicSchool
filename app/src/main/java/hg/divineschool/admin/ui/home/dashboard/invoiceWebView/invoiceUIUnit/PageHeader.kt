package hg.divineschool.admin.ui.home.dashboard.invoiceWebView.invoiceUIUnit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hg.divineschool.admin.ui.theme.mediumFont

@Composable
fun PageHeader(
    name: String,
    fatherName: String,
    rollNumber: String,
    className: String,
    address: String
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(150.dp)
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
                    text = "Divine Public School",
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
                Text(
                    text = "Satyanganj, Ahraura",
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
                Text(
                    text = "Mirzapur - 231301",
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
                Text(
                    text = "Uttar Pradesh",
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
                Text(
                    text = "Phone: 9838996103",
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
                .requiredHeight(150.dp)
                .weight(0.5f)

        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 80.dp)
            ) {
                Text(
                    text = "Bill To :",
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
                Text(
                    text = name,
                    modifier = Modifier
                        .padding(start = 0.dp, bottom = 0.dp)
                        .wrapContentWidth(),
                    color = Color.Black,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = mediumFont,
                        fontWeight = FontWeight.SemiBold,
                        fontStyle = FontStyle.Normal
                    )
                )
                Text(
                    text = fatherName,
                    modifier = Modifier
                        .padding(start = 0.dp, bottom = 0.dp)
                        .wrapContentWidth(),
                    color = Color.Black,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = mediumFont,
                        fontWeight = FontWeight.SemiBold,
                        fontStyle = FontStyle.Normal
                    )
                )
                Text(
                    text = rollNumber,
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
                Text(
                    text = className,
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
                Text(
                    text = address,
                    modifier = Modifier
                        .padding(start = 0.dp, bottom = 0.dp)
                        .wrapContentWidth(),
                    color = Color.Black,
                    overflow = TextOverflow.Ellipsis,
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