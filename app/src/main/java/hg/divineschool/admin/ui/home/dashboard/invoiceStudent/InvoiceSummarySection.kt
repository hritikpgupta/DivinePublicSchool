package hg.divineschool.admin.ui.home.dashboard.invoiceStudent

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hg.divineschool.admin.data.models.Student
import hg.divineschool.admin.ui.theme.boldFont
import hg.divineschool.admin.ui.theme.lightFont
import hg.divineschool.admin.ui.utils.INR

/*@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Preview() {
    InvoiceSummarySection(
        classID = "0",
        student = Student(),
        modifier = Modifier,
        color = cardColors[0],
        tuitionFee = 0,
        admissionFee = 0
    )
}*/

@Composable
fun InvoiceSummarySection(
    classID: String, student: Student,
    color: Color, modifier: Modifier, tuitionFee: Int,
    admissionFee: Int, transportFee: Int, bookFee: Int, supplementFee: Int,
    examinationFee: Int, annualFee: Int, computerFee : Int
) {
    val sum = tuitionFee+admissionFee+transportFee+bookFee+supplementFee+examinationFee+annualFee+computerFee

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.LightGray.copy(0.2f))

    ) {

        Text(
            text = " Summary",
            style = TextStyle(
                textAlign = TextAlign.Start,
                fontFamily = lightFont,
                fontSize = 28.sp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.LightGray)
        )
        Spacer(modifier = Modifier.height(10.dp))
        StudentInformation(student, classID)
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = " Amount Breakup",
            style = TextStyle(
                textAlign = TextAlign.Start,
                fontFamily = lightFont,
                fontSize = 28.sp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.LightGray)
        )
        Spacer(modifier = Modifier.height(10.dp))

        Row {
            TableHeading(heading = "Tuition Fee", modifier = Modifier.weight(0.6f))
            TableValue(
                value = "$INR $tuitionFee ",
                modifier = Modifier.weight(0.4f),
                align = TextAlign.End
            )
        }
        Row {
            TableHeading(heading = "Transport Fee", modifier = Modifier.weight(0.6f))
            TableValue(
                value = "$INR $transportFee ",
                modifier = Modifier.weight(0.4f),
                align = TextAlign.End
            )
        }
        Row {
            TableHeading(heading = "Examination Fee", modifier = Modifier.weight(0.6f))
            TableValue(
                value = "$INR $examinationFee ",
                modifier = Modifier.weight(0.4f),
                align = TextAlign.End
            )
        }
        Row {
            TableHeading(heading = "Computer Fee", modifier = Modifier.weight(0.6f))
            TableValue(
                value = "$INR $computerFee ",
                modifier = Modifier.weight(0.4f),
                align = TextAlign.End
            )
        }
        Row {
            TableHeading(heading = "Annual Fee", modifier = Modifier.weight(0.6f))
            TableValue(
                value = "$INR $annualFee ",
                modifier = Modifier.weight(0.4f),
                align = TextAlign.End
            )
        }
        Row {
            TableHeading(heading = "Admission Fee", modifier = Modifier.weight(0.6f))
            TableValue(
                value = "$INR $admissionFee ",
                modifier = Modifier.weight(0.4f),
                align = TextAlign.End
            )
        }
        Row {
            TableHeading(heading = "Supplements Fee", modifier = Modifier.weight(0.6f))
            TableValue(
                value = "$INR $supplementFee ",
                modifier = Modifier.weight(0.4f),
                align = TextAlign.End
            )
        }
        Row {
            TableHeading(heading = "Books Fee", modifier = Modifier.weight(0.6f))
            TableValue(
                value = "$INR $bookFee ",
                modifier = Modifier.weight(0.4f),
                align = TextAlign.End
            )
        }
        Row {
            Text(
                text = " Total",
                color = color,
                modifier = modifier
                    .weight(0.6f)
                    .border(1.dp, Color.LightGray.copy(0.6f), RectangleShape),
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = TextStyle(fontFamily = boldFont, fontSize = 20.sp)
            )
            Text(
                text = " $INR $sum ",
                modifier = modifier
                    .weight(0.4f)
                    .border(1.dp, Color.LightGray.copy(0.4f), RectangleShape),
                textAlign = TextAlign.End,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = TextStyle(fontFamily = boldFont, fontSize = 20.sp)

            )

        }
        Divider(thickness = 2.dp, color = Color.LightGray)


    }

}