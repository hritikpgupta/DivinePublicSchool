package hg.divineschool.admin.ui.home.setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hg.divineschool.admin.ui.theme.boldFont

@Preview
@Composable
fun StatisticCard() {
    Card(
        backgroundColor = Color.LightGray.copy(0.2f),
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(280.dp)
            .padding(6.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.8f)
                    .background(color = Color.Red.copy(0.3f))
            ) {

            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .padding(end = 4.dp, top = 4.dp)
                    .weight(0.2f)
            ) {
                Spacer(modifier = Modifier.requiredHeight(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = hg.divineschool.admin.R.drawable.open),
                        contentDescription = "Open",
                        modifier = Modifier.requiredSize(65.dp)
                    )
                    Spacer(modifier = Modifier.requiredWidth(20.dp))
                    ClickableText(text = AnnotatedString("7:00 am"), style = TextStyle(
                        fontFamily = boldFont,
                        fontSize = 30.sp,
                        textDecoration = TextDecoration.Underline
                    ), modifier = Modifier.padding(top = 6.dp), onClick = {})
                }
                Spacer(modifier = Modifier.requiredHeight(20.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = hg.divineschool.admin.R.drawable.close),
                        contentDescription = "Close",
                        modifier = Modifier.requiredSize(65.dp)
                    )
                    Spacer(modifier = Modifier.requiredWidth(20.dp))
                    ClickableText(text = AnnotatedString("3:00 pm"), style = TextStyle(
                        fontFamily = boldFont,
                        fontSize = 30.sp,
                        textDecoration = TextDecoration.Underline
                    ), modifier = Modifier.padding(top = 6.dp), onClick = {})
                }
                Spacer(modifier = Modifier.requiredHeight(20.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = hg.divineschool.admin.R.drawable.school_open),
                        contentDescription = "School Open",
                        modifier = Modifier.requiredSize(65.dp).weight(1f)
                    )
                    Spacer(modifier = Modifier.requiredWidth(20.dp).weight(1f))
                    ClickableText(text = AnnotatedString("Open"), style = TextStyle(
                        fontFamily = boldFont,
                        fontSize = 30.sp,
                        textDecoration = TextDecoration.Underline
                    ), modifier = Modifier.padding(top = 6.dp).weight(1f), onClick = {})
                }

            }
        }

    }
}