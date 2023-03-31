package hg.divineschool.admin.ui.home.setting

import android.app.TimePickerDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hg.divineschool.admin.R
import hg.divineschool.admin.data.models.SchoolInformation
import hg.divineschool.admin.ui.theme.boldFont
import hg.divineschool.admin.ui.theme.mediumFont
import hg.divineschool.admin.ui.theme.regularFont
import java.util.*

@Composable
fun StatisticCard(
    total: String,
    transport: String,
    rte: String,
    isFetched: Boolean,
    onClassOpenChanged: (isOpen: Boolean) -> Unit,
    onStartTimeChanged: (time: String) -> Unit,
    onEndTimeChanged: (time: String) -> Unit
) {

    var openTime by remember {
        mutableStateOf(SchoolInformation.SCHOOL_INFO.startsAt)
    }
    var closeTime by remember {
        mutableStateOf(SchoolInformation.SCHOOL_INFO.endsAt)
    }
    val openText = remember {
        if (SchoolInformation.SCHOOL_INFO.isOpen) {
            mutableStateOf("Open")
        } else {
            mutableStateOf("Close")
        }
    }
    val icon = remember {
        if (SchoolInformation.SCHOOL_INFO.isOpen) {
            mutableStateOf(hg.divineschool.admin.R.drawable.school_open)
        } else {
            mutableStateOf(hg.divineschool.admin.R.drawable.school_close)
        }
    }
    val mContext = LocalContext.current
    val mCalendar = Calendar.getInstance()
    val mHour = mCalendar[Calendar.HOUR_OF_DAY]
    val mMinute = mCalendar[Calendar.MINUTE]
    val startTimePickerDialog = TimePickerDialog(
        mContext, { _, mHour: Int, mMinute: Int ->
            openTime = "$mHour:$mMinute am"
            SchoolInformation.SCHOOL_INFO.startsAt = openTime
            openTime = SchoolInformation.SCHOOL_INFO.startsAt
            onStartTimeChanged(openTime)
        }, mHour, mMinute, false
    )
    val endTimePickerDialog = TimePickerDialog(
        mContext, { _, mHour: Int, mMinute: Int ->
            closeTime = "$mHour:$mMinute pm"
            SchoolInformation.SCHOOL_INFO.endsAt = closeTime
            closeTime = SchoolInformation.SCHOOL_INFO.endsAt
            onEndTimeChanged(closeTime)
        }, mHour, mMinute, false
    )

    Card(
        backgroundColor = Color.White.copy(0.0f),
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(280.dp)
            .padding(6.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 10.dp)
                    .weight(0.6f)

            ) {
                Text(
                    text = SchoolInformation.SCHOOL_INFO.name,
                    style = TextStyle(fontSize = 46.sp, fontFamily = mediumFont)
                )

                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(top = 16.dp)

                ) {
                    Card(
                        elevation = 0.dp,
                        backgroundColor = Color.LightGray.copy(0.5f),
                        modifier = Modifier
                            .clip(CircleShape)
                            .requiredSize(40.dp)
                            .padding(2.dp)
                    ) {
                        Icon(
                            Icons.Default.Phone,
                            contentDescription = "",
                            modifier = Modifier
                                .clip(CircleShape)
                                .requiredSize(30.dp)
                                .background(color = Color.LightGray.copy(0.5f))
                        )
                    }
                    Spacer(modifier = Modifier.requiredWidth(15.dp))
                    Text(
                        text = SchoolInformation.SCHOOL_INFO.contact.toString(), style = TextStyle(
                            fontSize = 30.sp, fontFamily = regularFont, fontWeight = FontWeight.Thin
                        )
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(top = 8.dp)

                ) {
                    Card(
                        elevation = 0.dp,
                        backgroundColor = Color.LightGray.copy(0.5f),
                        modifier = Modifier
                            .clip(CircleShape)
                            .requiredSize(40.dp)
                            .padding(2.dp)
                    ) {
                        Icon(
                            Icons.Default.AlternateEmail,
                            contentDescription = "",
                            modifier = Modifier
                                .clip(CircleShape)
                                .requiredSize(30.dp)
                                .background(color = Color.LightGray.copy(0.5f))
                        )
                    }
                    Spacer(modifier = Modifier.requiredWidth(15.dp))
                    Text(
                        text = SchoolInformation.SCHOOL_INFO.email, style = TextStyle(
                            fontSize = 30.sp, fontFamily = regularFont, fontWeight = FontWeight.Thin
                        )
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(top = 8.dp)

                ) {
                    Card(
                        elevation = 0.dp,
                        backgroundColor = Color.LightGray.copy(0.5f),
                        modifier = Modifier
                            .clip(CircleShape)
                            .requiredSize(40.dp)
                            .padding(2.dp)
                    ) {
                        Icon(
                            Icons.Default.Link,
                            contentDescription = "",
                            modifier = Modifier
                                .clip(CircleShape)
                                .requiredSize(30.dp)
                                .background(color = Color.LightGray.copy(0.5f))
                        )
                    }
                    Spacer(modifier = Modifier.requiredWidth(15.dp))
                    Text(
                        text = SchoolInformation.SCHOOL_INFO.website, style = TextStyle(
                            fontSize = 30.sp,
                            fontFamily = regularFont,
                            fontWeight = FontWeight.Thin,
                            textDecoration = TextDecoration.Underline
                        )
                    )
                }

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
                        painter = painterResource(id = R.drawable.presentation),
                        contentDescription = "Open",
                        modifier = Modifier
                            .requiredSize(65.dp)
                            .weight(0.4f)
                    )
                    if (isFetched) {
                        ClickableText(text = AnnotatedString(total), style = TextStyle(
                            fontFamily = boldFont,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black.copy(0.6f)
                        ), modifier = Modifier
                            .padding(top = 6.dp)
                            .weight(0.6f), onClick = {})
                    } else {
                        CircularProgressIndicator(
                            strokeWidth = 4.dp, modifier = Modifier.requiredSize(24.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.requiredHeight(20.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.bus),
                        contentDescription = "Close",
                        modifier = Modifier
                            .requiredSize(65.dp)
                            .weight(0.4f)
                    )
                    if (isFetched) {
                        ClickableText(text = AnnotatedString(transport), style = TextStyle(
                            fontFamily = boldFont,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black.copy(0.6f)
                        ), modifier = Modifier
                            .padding(top = 6.dp)
                            .weight(0.6f), onClick = {})
                    } else {
                        CircularProgressIndicator(
                            strokeWidth = 4.dp, modifier = Modifier.requiredSize(24.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.requiredHeight(20.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.right_to_education),
                        contentDescription = "School Open",
                        modifier = Modifier
                            .requiredSize(65.dp)
                            .weight(0.4f)
                    )
                    if (isFetched) {
                        ClickableText(text = AnnotatedString(rte), style = TextStyle(
                            fontFamily = boldFont,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black.copy(0.6f)
                        ), modifier = Modifier
                            .padding(top = 6.dp)
                            .weight(0.6f), onClick = {})
                    } else {
                        CircularProgressIndicator(
                            strokeWidth = 4.dp, modifier = Modifier.requiredSize(24.dp)
                        )
                    }
                }
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
                        modifier = Modifier
                            .requiredSize(65.dp)
                            .weight(0.4f)
                    )
                    ClickableText(text = AnnotatedString(openTime), style = TextStyle(
                        fontFamily = boldFont,
                        fontSize = 28.sp,
                        textDecoration = TextDecoration.Underline,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black.copy(0.6f)
                    ), modifier = Modifier
                        .padding(top = 6.dp)
                        .weight(0.6f), onClick = {
                        startTimePickerDialog.show()
                    })
                }
                Spacer(modifier = Modifier.requiredHeight(20.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = hg.divineschool.admin.R.drawable.close),
                        contentDescription = "Close",
                        modifier = Modifier
                            .requiredSize(65.dp)
                            .weight(0.4f)
                    )
                    ClickableText(text = AnnotatedString(closeTime), style = TextStyle(
                        fontFamily = boldFont,
                        fontSize = 28.sp,
                        textDecoration = TextDecoration.Underline,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black.copy(0.6f)
                    ), modifier = Modifier
                        .padding(top = 6.dp)
                        .weight(0.6f), onClick = {
                        endTimePickerDialog.show()
                    })
                }
                Spacer(modifier = Modifier.requiredHeight(20.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = icon.value),
                        contentDescription = "School Open",
                        modifier = Modifier
                            .requiredSize(65.dp)
                            .weight(0.4f)
                    )
                    ClickableText(text = AnnotatedString(openText.value), style = TextStyle(
                        fontFamily = boldFont,
                        fontSize = 28.sp,
                        textDecoration = TextDecoration.Underline,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black.copy(0.6f)
                    ), modifier = Modifier
                        .padding(top = 6.dp)
                        .weight(0.6f), onClick = {
                        SchoolInformation.SCHOOL_INFO.isOpen = !SchoolInformation.SCHOOL_INFO.isOpen
                        if (SchoolInformation.SCHOOL_INFO.isOpen) {
                            openText.value = "Open"
                            icon.value = R.drawable.school_open
                        } else {
                            openText.value = "Close"
                            icon.value = R.drawable.school_close
                        }
                        onClassOpenChanged(SchoolInformation.SCHOOL_INFO.isOpen)
                    })
                }
            }
        }

    }
}