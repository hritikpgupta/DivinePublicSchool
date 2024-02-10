package hg.divineschool.admin.ui.home.dashboard.invoiceWebView.invoiceUIUnit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import hg.divineschool.admin.data.models.Invoice

@Composable
fun InvoiceLayout(innerPadding : PaddingValues = PaddingValues(0.dp), invoice: Invoice) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(innerPadding)
            .background(
                color = Color.LightGray.copy(
                    0.5f
                )
            )
    ) {
        Spacer(modifier = Modifier.requiredHeight(20.dp))
        Column(
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .requiredWidth(595.dp)
                .requiredHeight(900.dp)
                .background(color = Color.White)

        ) {
            Title(invoiceNumber = invoice.invoiceNumber)
            PageHeader(
                name = invoice.studentName,
                fatherName = invoice.guardianName,
                rollNumber = invoice.rollNumber.toString(),
                className = invoice.className,
                address = invoice.address
            )
            Divider(
                thickness = 2.dp,
                color = Color.LightGray,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            )
            ColumnHeader(
                months = invoice.tuitionFeeMonthList,
                date = invoice.date.trim()
            )
            Detail(
                computerFee = invoice.computerFee,
                invoice.annualCharge,
                invoice.lateFee,
                invoice.developmentFee,
                invoice.transportFee,
                invoice.examFee,
                invoice.supplementaryFee,
                invoice.tuitionFee,
                invoice.bookFee,
                invoice.total
            )
            ColumnFooter(
                bookDetail = invoice.bookList,
                supplementDetail = invoice.supplementsList
            )
        }
    }
}