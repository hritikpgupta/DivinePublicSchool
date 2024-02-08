package hg.divineschool.admin.ui.home.setting.pendingDues

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import hg.divineschool.admin.data.models.PendingInvoice

@Composable
fun PendingInvoiceDetailedSection(
    invoiceList: List<PendingInvoice>, modifier: Modifier, onItemSelected: (String) -> Unit
) {
    Row(modifier = modifier) {

        LazyColumn(modifier = modifier.fillMaxSize()) {
            items(invoiceList) { data ->
                Text(text = data.invoice.invoiceNumber)
            }
        }

    }
}