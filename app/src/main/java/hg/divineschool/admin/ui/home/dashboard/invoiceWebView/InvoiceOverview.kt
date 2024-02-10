package hg.divineschool.admin.ui.home.dashboard.invoiceWebView

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hg.divineschool.admin.data.models.Invoice
import hg.divineschool.admin.data.utils.getSerializable
import hg.divineschool.admin.ui.home.DPSBar
import hg.divineschool.admin.ui.home.dashboard.invoiceWebView.invoiceUIUnit.InvoiceLayout
import hg.divineschool.admin.ui.theme.DivinePublicSchoolTheme
import hg.divineschool.admin.ui.theme.boldFont

class InvoiceOverview : ComponentActivity() {
    private lateinit var invoice: Invoice

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        invoice = intent.getSerializable("invoiceObject", Invoice::class.java)
        setContent {
            DivinePublicSchoolTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    val context = LocalContext.current
                    val startInvoiceScreen =
                        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {}
                    Scaffold(scaffoldState = rememberScaffoldState(), topBar = {
                        DPSBar(
                            onBackPressed = { finish() },
                            className = "Invoice ${invoice.invoiceNumber}"
                        )
                    }, floatingActionButton = {
                        ExtendedFloatingActionButton(onClick = {
                            val intent = Intent(
                                context, InvoiceScreen::class.java
                            )
                            intent.putExtra("invoiceObject", invoice)
                            startInvoiceScreen.launch(intent)
                        },
                            modifier = Modifier.padding(bottom = 40.dp, end = 10.dp),
                            elevation = FloatingActionButtonDefaults.elevation(4.dp),
                            shape = RoundedCornerShape(8.dp),
                            icon = {
                                Icon(
                                    Icons.Filled.Print,
                                    null,
                                    tint = Color.White,
                                    modifier = Modifier.requiredSize(40.dp)
                                )
                            },
                            text = {
                                Text(
                                    text = "Print", style = TextStyle(
                                        fontFamily = boldFont,
                                        fontSize = 28.sp,
                                        fontWeight = FontWeight.SemiBold
                                    ), color = Color.White
                                )
                            })

                    }, modifier = Modifier.fillMaxSize()
                    ) { innerPadding ->
                        InvoiceLayout(innerPadding = innerPadding, invoice = invoice)
                    }
                }
            }
        }
    }
}