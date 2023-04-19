package hg.divineschool.admin.ui.home.dashboard.invoiceWebView

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import hg.divineschool.admin.data.models.Invoice
import hg.divineschool.admin.data.utils.getSerializable
import hg.divineschool.admin.ui.home.DPSBar

class InvoiceOverview : ComponentActivity() {
    private lateinit var invoice: Invoice

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        invoice = intent.getSerializable("invoiceObject", Invoice::class.java)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
            ) {
                Scaffold(
                    scaffoldState = rememberScaffoldState(), topBar = {
                        DPSBar(
                            onBackPressed = { finish() },
                            className = "Invoice ${invoice.invoiceNumber}"
                        )
                    }, modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .background(color = androidx.compose.material.MaterialTheme.colors.background)
                    ) {

                    }
                }
            }

        }

    }
}