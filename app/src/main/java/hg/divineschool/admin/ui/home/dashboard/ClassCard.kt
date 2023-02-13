package hg.divineschool.admin.ui.home.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
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
import hg.divineschool.admin.data.models.ClassInformation
import hg.divineschool.admin.ui.theme.lightFont
import hg.divineschool.admin.ui.theme.mediumFont
import hg.divineschool.admin.ui.theme.regularFont

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ClassCard(classInformation: ClassInformation,onCardClick: () -> Unit) {
    Card(
        backgroundColor = Color.White.copy(0.6f),
        shape = RoundedCornerShape(4.dp),
        elevation = 4.dp,
        modifier = Modifier
            .requiredSize(270.dp, 150.dp)
            .background(color = Color.Green.copy(0.25f))
            .clip(RoundedCornerShape(4.dp)),
        onClick = onCardClick
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = classInformation.name,
                style = TextStyle(
                    color = Color.Black.copy(0.80f),
                    fontSize = 28.sp,
                    fontFamily = mediumFont
                ),
            )
            Spacer(modifier = Modifier
                .height(4.dp))
            Spacer(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(color = Color.Black.copy(0.1f))
            )
            Spacer(modifier = Modifier
                .height(15.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .requiredHeight(50.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                Column(Modifier.weight(1f)) {
                    ClassInfo(icon = R.drawable.class_teacher, text = classInformation.classTeacherName)
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .requiredHeight(50.dp),
                horizontalArrangement = Arrangement.Center,
            ) {
                Column(Modifier.weight(1f)) {
                    ClassInfo(icon = R.drawable.presentation, text = classInformation.studentsCount.toString())
                }
                Column(Modifier.weight(1f)) {
                    ClassInfo(icon = R.drawable.bus, text = classInformation.transportStudentsCount.toString())
                }
                Column(Modifier.weight(1f)) {
                    ClassInfo(icon = R.drawable.new_student, text = classInformation.newAdmission.toString())
                }
            }
        }
    }
}

@Composable
fun ClassInfo(icon: Int, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(2.dp)
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = stringResource(id = R.string.students_icon_content_description),
            modifier = Modifier.requiredSize(30.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text, style = TextStyle(
                color = Color.Black.copy(0.80f),
                fontFamily = regularFont,
                textAlign = TextAlign.Start,
                fontSize = 20.sp
            ), overflow = TextOverflow.Clip, maxLines = 1, softWrap = true
        )
    }
}


