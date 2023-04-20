package hg.divineschool.admin.ui.home.dashboard.invoiceWebView.invoiceUIUnit

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import hg.divineschool.admin.ui.theme.cellColor
import hg.divineschool.admin.ui.utils.TableCell

@Composable
fun Detail(computerFee : Long, annualFee : Long, lateFee : Long, admissionFee : Long, transportFee : Long, examFee : Long, supplementFee : Long, tuitionFee : Long, bookFee : Long, total : Long) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(horizontal = 10.dp)) {

        Spacer(modifier = Modifier.requiredHeight(8.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            TableCell(text = "Fee Type", weight = 0.8f, color = cellColor.copy(0.15f), align = TextAlign.Center)
            TableCell(text = "Amount", weight = 0.2f, color = cellColor.copy(0.15f), align = TextAlign.Center)
        }
        Row(modifier = Modifier.fillMaxWidth().padding(top = 0.2.dp)) {
            TableCell(text = "Computer Fee", weight = 0.8f)
            TableCell(text = computerFee.toString(), weight = 0.2f, align = TextAlign.Center)
        }
        Row(modifier = Modifier.fillMaxWidth().padding(top = 0.2.dp)) {
            TableCell(text = "Annual Fee", weight = 0.8f)
            TableCell(text = annualFee.toString(), weight = 0.2f, align = TextAlign.Center)
        }
        Row(modifier = Modifier.fillMaxWidth().padding(top = 0.2.dp)) {
            TableCell(text = "Late Fee", weight = 0.8f)
            TableCell(text = lateFee.toString(), weight = 0.2f, align = TextAlign.Center)
        }
        Row(modifier = Modifier.fillMaxWidth().padding(top = 0.2.dp)) {
            TableCell(text = "Admission Fee", weight = 0.8f)
            TableCell(text = admissionFee.toString(), weight = 0.2f, align = TextAlign.Center)
        }
        Row(modifier = Modifier.fillMaxWidth().padding(top = 0.2.dp)) {
            TableCell(text = "Transport Fee", weight = 0.8f)
            TableCell(text = transportFee.toString(), weight = 0.2f, align = TextAlign.Center)
        }
        Row(modifier = Modifier.fillMaxWidth().padding(top = 0.2.dp)) {
            TableCell(text = "Exam Fee", weight = 0.8f)
            TableCell(text = examFee.toString(), weight = 0.2f, align = TextAlign.Center)
        }
        Row(modifier = Modifier.fillMaxWidth().padding(top = 0.2.dp)) {
            TableCell(text = "Supplement Fee", weight = 0.8f)
            TableCell(text = supplementFee.toString(), weight = 0.2f, align = TextAlign.Center)
        }
        Row(modifier = Modifier.fillMaxWidth().padding(top = 0.2.dp)) {
            TableCell(text = "Tuition Fee", weight = 0.8f)
            TableCell(text = tuitionFee.toString(), weight = 0.2f, align = TextAlign.Center)
        }
        Row(modifier = Modifier.fillMaxWidth().padding(top = 0.2.dp)) {
            TableCell(text = "Book Fee", weight = 0.8f)
            TableCell(text = bookFee.toString(), weight = 0.2f, align = TextAlign.Center)
        }
        Row(modifier = Modifier.fillMaxWidth().padding(top = 0.2.dp)) {
            TableCell(text = "Total", weight = 0.8f, align = TextAlign.Center)
            TableCell(text = total.toString(), weight = 0.2f, align = TextAlign.Center)
        }



        
    }

}