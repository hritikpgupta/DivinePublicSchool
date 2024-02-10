package hg.divineschool.admin.ui.home.setting.pendingDues

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hg.divineschool.admin.data.models.PendingInvoice
import hg.divineschool.admin.ui.home.dashboard.invoiceWebView.invoiceUIUnit.InvoiceLayout
import hg.divineschool.admin.ui.theme.regularFont

@Composable
fun PendingInvoiceDetailedSection(
    invoiceList: List<PendingInvoice>,
    modifier: Modifier,
    pushRemark: (String, String, List<String>) -> Unit,
    settleInvoice: (PendingInvoice) -> Unit
) {
    val selectedMonthFee = remember {
        mutableStateOf(PendingInvoice())
    }
    var selectedInvoiceId by remember { mutableStateOf("") }


    Row(modifier = modifier.fillMaxWidth()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.18f)
                .padding(bottom = 0.dp)
        ) {
            items(invoiceList) { data ->
                InvoiceCard(
                    bgColor = (if (data.invoice.invoiceNumber == selectedInvoiceId) Color.Green.copy(
                        0.05f
                    ) else Color.LightGray.copy(0.2f)), invoice = data
                ) {
                    selectedInvoiceId = it.invoice.invoiceNumber
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
                .padding(bottom = 0.dp)
        ) {
            if (selectedMonthFee.value.remarks.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(0.6f)
                    ) {
                        InvoiceLayout(invoice = selectedMonthFee.value.invoice)
                    }
                    RemarkSection(modifier = Modifier.weight(0.4f),
                        selectedMonthFee.value.remarks,
                        {
                            pushRemark(
                                it,
                                selectedMonthFee.value.invoice.invoiceNumber,
                                selectedMonthFee.value.remarks
                            )
                        }) {
                        settleInvoice(selectedMonthFee.value)
                    }
                }
            } else {
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
                            text = "Please Select Invoice", style = TextStyle(
                                fontFamily = regularFont,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Medium
                            ), color = Color.DarkGray
                        )
                    }
                }
            }
        }
    }
}

