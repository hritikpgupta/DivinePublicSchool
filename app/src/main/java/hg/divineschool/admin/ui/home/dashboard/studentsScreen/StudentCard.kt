package hg.divineschool.admin.ui.home.dashboard.studentsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import hg.divineschool.admin.R
import hg.divineschool.admin.data.models.Student
import hg.divineschool.admin.ui.theme.lightFont
import hg.divineschool.admin.ui.theme.regularFont

@Composable
fun StudentCard(
    student: Student,
    color: Color,
    onViewClick: () -> Unit,
    onInvoiceClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        elevation = 6.dp,
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
                        .requiredSize(105.dp)
                        .shadow(10.dp, CircleShape, true, color, color)
                        .clip(
                            CircleShape
                        )
                        .background(color = color)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.padding(top = 0.dp)) {
                    Text(
                        text = "${student.firstName} ${student.lastName}",
                        style = TextStyle(
                            fontFamily = regularFont,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.SemiBold
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        softWrap = true
                    )
                    Text(
                        text = "Roll No: ${student.rollNumber}",
                        style = TextStyle(
                            fontFamily = lightFont,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Normal
                        )
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.BottomEnd)
                    ) {
                        IconButton(onClick = { expanded = true }) {
                            Icon(
                                Icons.Default.MoreVert,
                                contentDescription = "",
                                modifier = Modifier.requiredSize(30.dp)
                            )
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier.border(
                                width = 1.5.dp,
                                color = color,
                                shape = RectangleShape
                            )
                        ) {
                            DropdownMenuItem(onClick = {
                                expanded = false
                                onViewClick()
                            }) {
                                Icon(
                                    Icons.Default.RemoveRedEye,
                                    contentDescription = "",
                                    tint = color,
                                    modifier = Modifier.requiredSize(25.dp)
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Text(text = "View", style = TextStyle(fontSize = 18.sp))
                            }
                            Divider(thickness = 1.dp, color = color)
                            DropdownMenuItem(
                                onClick = {
                                    expanded = false
                                    onInvoiceClick()
                                }
                            ) {
                                Icon(
                                    ImageVector.vectorResource(id = R.drawable.ic_invoice),
                                    contentDescription = "",
                                    tint = color,
                                    modifier = Modifier.requiredSize(25.dp)
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Text(text = "Invoice", style = TextStyle(fontSize = 18.sp))
                            }
                        }
                    }
                }
            }
        }
    }
}
