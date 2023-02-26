package hg.divineschool.admin.ui.home.dashboard.studentsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import hg.divineschool.admin.data.models.Student
import hg.divineschool.admin.ui.theme.boldFont
import hg.divineschool.admin.ui.theme.regularFont

@OptIn(ExperimentalMaterialApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun StudentCard(student: Student, color: Color, onCardClick: () -> Unit) {
    Card(
        elevation = 4.dp,
        onClick = onCardClick,
        backgroundColor = MaterialTheme.colors.background.copy(1f),
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.padding(10.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).data(student.image)
                        .crossfade(true).build(),
                    contentScale = ContentScale.FillWidth,
                    contentDescription = "Logo",
                    modifier = Modifier
                        .requiredSize(90.dp)
                        .shadow(6.dp, CircleShape, true, color, color)
                        .clip(
                            CircleShape
                        )
                        .background(color = color)


                )
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.padding(top = 8.dp)) {
                    Text(
                        text = "${student.firstName} ${student.lastName}",
                        style = TextStyle(fontFamily = boldFont, fontSize = 25.sp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        softWrap = true
                    )
                    Text(
                        text = student.rollNumber.toString(),
                        style = TextStyle(fontFamily = regularFont, fontSize = 25.sp)
                    )
                }
            }
        }
    }
}
