package hg.divineschool.admin.ui.home.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hg.divineschool.admin.R
import hg.divineschool.admin.data.models.ClassInformation
import hg.divineschool.admin.ui.theme.mediumFont
import hg.divineschool.admin.ui.theme.regularFont

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ClassCard(
    classInformation: ClassInformation,
    cardColor: Color,
    onCardClick: (id: Long, name: String) -> Unit,
    onLongPress:(id:Long) -> Unit

) {
    Card(
        elevation = 6.dp,
        backgroundColor = MaterialTheme.colors.background,
        modifier = Modifier
            .requiredSize(280.dp, 170.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.pointerInput(Unit) {
                detectTapGestures(onLongPress = {
                    onLongPress(classInformation.id)
                }, onTap = {
                    onCardClick(classInformation.id, classInformation.name)
                })
            }
        ) {
            Text(
                text = " ${classInformation.name}",
                style = TextStyle(
                    color = Color.Black.copy(0.80f),
                    fontSize = 30.sp,
                    fontFamily = mediumFont,
                    textAlign = TextAlign.Start
                ),
                overflow = TextOverflow.Clip,
                maxLines = 1,
                softWrap = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = cardColor)
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Black.copy(0.5f))
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .requiredHeight(40.dp)
                    .padding(start = 2.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                Column(Modifier.weight(1f)) {
                    ClassInfo(
                        icon = R.drawable.class_teacher, text = classInformation.classTeacherName
                    )
                }
            }
            Spacer(modifier = Modifier.height(2.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .requiredHeight(40.dp)
                    .padding(start = 2.dp),
                horizontalArrangement = Arrangement.Center,
            ) {
                Column(Modifier.weight(1f)) {
                    ClassInfo(
                        icon = R.drawable.presentation,
                        text = classInformation.studentsCount.toString()
                    )
                }
                Column(Modifier.weight(1f)) {
                    ClassInfo(
                        icon = R.drawable.bus,
                        text = classInformation.transportStudentsCount.toString()
                    )
                }
                Column(Modifier.weight(1f)) {
                    ClassInfo(
                        icon = R.drawable.new_student,
                        text = classInformation.newAdmission.toString()
                    )
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
            modifier = Modifier.requiredSize(40.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text, style = TextStyle(
                color = MaterialTheme.colors.onBackground,
                fontFamily = regularFont,
                textAlign = TextAlign.Start,
                fontSize = 26.sp
            ), overflow = TextOverflow.Clip, maxLines = 1, softWrap = true
        )
    }
}


