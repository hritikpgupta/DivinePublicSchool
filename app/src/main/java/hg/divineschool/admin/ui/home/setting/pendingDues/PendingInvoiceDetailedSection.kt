package hg.divineschool.admin.ui.home.setting.pendingDues

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hg.divineschool.admin.data.models.PendingInvoice
import hg.divineschool.admin.ui.home.dashboard.invoiceWebView.invoiceUIUnit.InvoiceLayout
import hg.divineschool.admin.ui.theme.regularFont
import hg.divineschool.admin.ui.utils.INR

@Composable
fun PendingInvoiceDetailedSection(
    invoiceList: List<PendingInvoice>, modifier: Modifier, onItemSelected: (String) -> Unit
) {
    val selectedMonthFee = remember {
        mutableStateOf(PendingInvoice())
    }
    Row(modifier = modifier.fillMaxWidth()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.18f)
        ) {
            items(invoiceList) { data ->
                InvoiceCard(invoice = data) {
                    selectedMonthFee.value = it
                }
            }
        }
        androidx.compose.material.Divider(
            color = Color.LightGray, modifier = Modifier
                .fillMaxHeight()
                .width(3.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.85f)
        ) {
            if (selectedMonthFee.value.remarks.isNotEmpty()) {
                InvoiceLayout(invoice = selectedMonthFee.value.invoice)
            }
            else {
                Box(
                    contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .wrapContentHeight()
                            .wrapContentWidth()
                    ) {
                        Text(
                            text = "No Invoice Selected", style = TextStyle(
                                fontFamily = regularFont,
                                fontSize = 34.sp,
                                fontWeight = FontWeight.Medium
                            ), color = Color.DarkGray
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun InvoiceCard(invoice: PendingInvoice, onItemSelected: (PendingInvoice) -> Unit) {
    Card(
        onClick = { onItemSelected(invoice) },
        shape = RoundedCornerShape(2.dp),
        backgroundColor = Color.LightGray.copy(0.2f),
        elevation = 1.dp,
        modifier = Modifier
            .requiredHeight(95.dp)
            .requiredWidth(200.dp)
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
                    style = TextStyle(fontWeight = FontWeight.Bold)
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
                    modifier = Modifier.weight(0.55f)
                )
                Text(
                    text = invoice.invoice.className.plus("aa"),
                    maxLines = 1,
                    modifier = Modifier.weight(0.45f)
                )
            }
            Text(
                text = invoice.invoice.guardianName, maxLines = 1, overflow = TextOverflow.Ellipsis
            )
            Divider(
                thickness = 1.5.dp,
                color = Color.LightGray,
                modifier = Modifier.padding(vertical = 2.dp)
            )
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Total $INR ${invoice.invoice.total}",
                    style = TextStyle(fontWeight = FontWeight.Medium)
                )
            }

        }

    }


}