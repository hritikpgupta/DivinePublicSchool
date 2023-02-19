package hg.divineschool.admin.ui.home.dashboard.studentsScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import hg.divineschool.admin.data.models.Student
import hg.divineschool.admin.ui.theme.boldFont
import hg.divineschool.admin.ui.theme.lightFont
import hg.divineschool.admin.ui.theme.mediumFont
import hg.divineschool.admin.ui.theme.regularFont

@Composable
fun StudentCard(student: Student) {
    Card(
        elevation = 2.dp,
        backgroundColor = MaterialTheme.colors.background.copy(0.8f),
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.padding(8.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(student.image)
                        .crossfade(true)
                        .build(),
                    contentScale = ContentScale.FillWidth,
                    contentDescription = "Logo", modifier = Modifier
                        .requiredSize(90.dp)
                        .clip(
                            CircleShape
                        )
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.padding(top = 8.dp)) {
                    Text(
                        text = "Hritik Gupta",
                        style = TextStyle(fontFamily = boldFont, fontSize = 28.sp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        softWrap = true
                    )
                    Text(
                        text = "Roll No. 34",
                        style = TextStyle(fontFamily = regularFont, fontSize = 26.sp)
                    )
                }
            }
        }
    }
}

@Preview(device = Devices.TABLET)
@Composable
fun ShowCard() {
    StudentCard(student = Student(image = "https://firebasestorage.googleapis.com/v0/b/dpsadmin-339a7.appspot.com/o/studentImages%2F1480.jpg?alt=media&token=6762614c-0d87-4fc1-975f-db2e5d7186fe"))
}