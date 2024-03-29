package hg.divineschool.admin.ui.home.dashboard.invoiceStudent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import hg.divineschool.admin.data.models.Invoice
import hg.divineschool.admin.data.models.Student
import hg.divineschool.admin.ui.theme.lightFont
import hg.divineschool.admin.ui.utils.INR
import hg.divineschool.admin.ui.utils.convertIdToName

@Composable
fun InvoiceSummarySection(
    classID: String,
    student: Student,
    color: Color,
    modifier: Modifier,
    tuitionFee: Long,
    defaultNumberOfMonthsForDestination: Int,
    developmentFee: Long,
    transportFee: Long,
    bookFee: Long,
    supplementFee: Long,
    examinationFee: Long,
    annualFee: Long,
    computerFee: Long,
    onGenerateClicked: (invoice: Invoice) -> Unit
) {
    val sum =
        tuitionFee + developmentFee + transportFee + bookFee + supplementFee + examinationFee + annualFee + computerFee
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.LightGray.copy(0.2f))
            .verticalScroll(scrollState)


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
            TableHeading(heading = "Tuition Fee", modifier = Modifier.weight(0.65f))
            TableValue(
                value = "$INR $tuitionFee ",
                modifier = Modifier.weight(0.35f),
                align = TextAlign.End
            )
        }
        Row {
            TableHeading(heading = "Transport Fee", modifier = Modifier.weight(0.65f))
            TableValue(
                value = "$INR ${transportFee * defaultNumberOfMonthsForDestination} ",
                modifier = Modifier.weight(0.35f),
                align = TextAlign.End
            )
        }
        Row {
            TableHeading(heading = "Examination Fee", modifier = Modifier.weight(0.65f))
            TableValue(
                value = "$INR $examinationFee ",
                modifier = Modifier.weight(0.35f),
                align = TextAlign.End
            )
        }
        Row {
            TableHeading(heading = "Computer Fee", modifier = Modifier.weight(0.65f))
            TableValue(
                value = "$INR $computerFee ",
                modifier = Modifier.weight(0.35f),
                align = TextAlign.End
            )
        }
        Row {
            TableHeading(heading = "Annual Fee", modifier = Modifier.weight(0.65f))
            TableValue(
                value = "$INR $annualFee ",
                modifier = Modifier.weight(0.35f),
                align = TextAlign.End
            )
        }
        Row {
            TableHeading(heading = "Development Fee", modifier = Modifier.weight(0.65f))
            TableValue(
                value = "$INR $developmentFee ",
                modifier = Modifier.weight(0.35f),
                align = TextAlign.End
            )
        }
        Row {
            TableHeading(heading = "Supplements Fee", modifier = Modifier.weight(0.65f))
            TableValue(
                value = "$INR $supplementFee ",
                modifier = Modifier.weight(0.35f),
                align = TextAlign.End
            )
        }
        Row {
            TableHeading(heading = "Books Fee", modifier = Modifier.weight(0.65f))
            TableValue(
                value = "$INR $bookFee ",
                modifier = Modifier.weight(0.35f),
                align = TextAlign.End
            )
        }
        Row {
            TableHeading2(heading = "Total", modifier = Modifier.weight(0.65f), color = color)
            TableValue2(
                value = "$INR $sum ",
                modifier = Modifier.weight(0.35f)
            )
        }
        Divider(thickness = 2.dp, color = Color.LightGray)
        Spacer(modifier = Modifier.requiredHeight(8.dp))

        Box(
            contentAlignment = Alignment.BottomCenter, modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 10.dp, start = 2.dp, end = 2.dp, top = 0.dp)
        ) {
            Column{
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = color),
                    elevation = ButtonDefaults.elevation(
                        defaultElevation = 5.dp,
                        pressedElevation = 5.dp,
                        disabledElevation = 0.dp
                    ),
                    onClick = {
                        onGenerateClicked(
                            Invoice(
                                tuitionFee = tuitionFee,
                                developmentFee = developmentFee,
                                transportFee = transportFee * defaultNumberOfMonthsForDestination,
                                bookFee = bookFee,
                                supplementaryFee = supplementFee,
                                examFee = examinationFee,
                                annualCharge = annualFee,
                                computerFee = computerFee,
                                total = sum,
                                rollNumber = student.rollNumber,
                                studentName = "${student.firstName} ${student.lastName}",
                                scholarNumber = student.scholarNumber,
                                guardianName = "S/0 ${student.fathersName}",
                                address = student.address,
                                className = classID.convertIdToName(),
                                invoiceNumber = "${student.scholarNumber}-1",
                                systemPaid = false,
                            )
                        )
                    },
                modifier = Modifier.fillMaxWidth(),
                    enabled = !FirebaseAuth.getInstance().currentUser?.email.equals("hgupta@dps.com")
                ) {
                    Text(
                        text = "Generate Invoice",
                        color = Color.White,
                        style = TextStyle(fontSize = 24.sp)
                    )
                }
                if (FirebaseAuth.getInstance().currentUser?.email.equals("hgupta@dps.com")){
                    Spacer(modifier = Modifier.requiredHeight(15.dp))
                    Button(
                        colors = ButtonDefaults.buttonColors(backgroundColor = color), onClick = {
                            onGenerateClicked(
                                Invoice(
                                    tuitionFee = tuitionFee,
                                    developmentFee = developmentFee,
                                    transportFee = transportFee * defaultNumberOfMonthsForDestination,
                                    bookFee = bookFee,
                                    supplementaryFee = supplementFee,
                                    examFee = examinationFee,
                                    annualCharge = annualFee,
                                    computerFee = computerFee,
                                    total = sum,
                                    rollNumber = student.rollNumber,
                                    studentName = "${student.firstName} ${student.lastName}",
                                    scholarNumber = student.scholarNumber,
                                    guardianName = "S/0 ${student.fathersName}",
                                    address = student.address,
                                    className = classID.convertIdToName(),
                                    invoiceNumber = "${student.scholarNumber}-1",
                                    systemPaid = true,
                                )
                            )
                        }, elevation = ButtonDefaults.elevation(
                            defaultElevation = 5.dp,
                            pressedElevation = 5.dp,
                            disabledElevation = 0.dp
                        ), modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Pending Invoice",
                            color = Color.White,
                            style = TextStyle(fontSize = 24.sp)
                        )
                    }
                }
            }
        }

    }

}