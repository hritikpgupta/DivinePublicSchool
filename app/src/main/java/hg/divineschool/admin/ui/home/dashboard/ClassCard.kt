package hg.divineschool.admin.ui.home.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hg.divineschool.admin.R

@Composable
fun ClassCard() {
    Card(
        backgroundColor = Color.White.copy(0.6f),
        shape = RoundedCornerShape(4.dp),
        elevation = 4.dp,
        modifier = Modifier
            .requiredSize(270.dp, 150.dp)
            .clip(RoundedCornerShape(4.dp))
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Play Group",
                style = TextStyle(color = Color.DarkGray.copy(0.9f), fontSize = 28.sp),
            )
            Spacer(modifier = Modifier.height(26.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .requiredHeight(50.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                Column(Modifier.weight(1f)) {
                    ClassInfo(icon = R.drawable.class_teacher, text = "Poonam Pandey")
                }
            }
            Spacer(modifier = Modifier.height(26.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .requiredHeight(50.dp),
                horizontalArrangement = Arrangement.Center,
            ) {
                Column(Modifier.weight(1f)) {
                    ClassInfo(icon = R.drawable.presentation, text = "120")
                }
                Column(Modifier.weight(1f)) {
                    ClassInfo(icon = R.drawable.bus, text = "400")
                }
                Column(Modifier.weight(1f)) {
                    ClassInfo(icon = R.drawable.new_student, text = "200")
                }
            }
        }
    }
}

@Composable
fun ClassInfo(icon: Int, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(2.dp)
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = stringResource(id = R.string.students_icon_content_description),
            modifier = Modifier.requiredSize(30.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text, style = TextStyle(
                color = Color.DarkGray.copy(0.80f), textAlign = TextAlign.Start, fontSize = 20.sp
            ), overflow = TextOverflow.Clip, maxLines = 1, softWrap = true
        )
    }
}

@Preview
@Composable
fun ViewClassInfo() {
    ClassInfo(R.drawable.students, "44")
}


//@Preview(showSystemUi = true, device = Devices.TABLET)
//@Composable
//fun ViewCard() {
//    ClassCard()
//}

@Preview
@Composable
fun ViewCard2() {
    ClassCard()
}