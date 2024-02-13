package hg.divineschool.admin.ui.home.setting.pendingDues

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hg.divineschool.admin.data.models.PendingInvoice
import hg.divineschool.admin.ui.utils.INR

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun InvoiceCard(bgColor: Color, invoice: PendingInvoice, onItemSelected: (PendingInvoice) -> Unit) {
    Card(
        onClick = { onItemSelected(invoice) },
        shape = RoundedCornerShape(2.dp),
        backgroundColor = bgColor,
        elevation = 1.dp,
        modifier = Modifier
            .requiredHeight(85.dp)
            .requiredWidth(190.dp)
            .padding(vertical = 4.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.Start, modifier = Modifier
                .padding(4.dp)
                .fillMaxSize()
        ) {
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Invoice No: ${invoice.invoice.invoiceNumber}",
                    maxLines = 1,
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 12.sp),
                )
            }
            Divider(
                thickness = 1.5.dp,
                color = Color.LightGray,
                modifier = Modifier.padding(vertical = 2.dp)
            )
            Row(
                horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = invoice.invoice.studentName,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(fontSize = 12.sp),
                    modifier = Modifier.weight(0.55f)
                )
                Text(
                    text = invoice.invoice.className,
                    maxLines = 1,
                    style = TextStyle(fontSize = 12.sp),
                    modifier = Modifier.weight(0.45f)

                )
            }
            Text(
                text = invoice.invoice.guardianName,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(fontSize = 12.sp)
            )
            Divider(
                thickness = 1.5.dp,
                color = Color.LightGray,
                modifier = Modifier.padding(vertical = 2.dp)
            )
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Total $INR ${invoice.invoice.total}",
                    style = TextStyle(fontWeight = FontWeight.Medium, fontSize = 12.sp),
                    )
            }
        }
    }
}