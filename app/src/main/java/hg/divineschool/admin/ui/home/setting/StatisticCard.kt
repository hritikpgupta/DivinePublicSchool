package hg.divineschool.admin.ui.home.setting

import android.app.TimePickerDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hg.divineschool.admin.R
import hg.divineschool.admin.data.models.SchoolInformation
import hg.divineschool.admin.ui.theme.boldFont
import java.util.*

@Composable
fun StatisticCard(
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
                        modifier = Modifier
                            .requiredSize(65.dp)
                            .weight(0.4f)
                    )
                    ClickableText(text = AnnotatedString(openTime), style = TextStyle(
                        fontFamily = boldFont,
                        fontSize = 28.sp,
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