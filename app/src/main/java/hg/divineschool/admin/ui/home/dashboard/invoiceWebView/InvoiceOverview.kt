package hg.divineschool.admin.ui.home.dashboard.invoiceWebView

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Print
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
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
import hg.divineschool.admin.ui.home.dashboard.invoiceWebView.invoiceUIUnit.*
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
                    modifier = Modifier.fillMaxSize(),
                    color = androidx.compose.material.MaterialTheme.colors.background
                ) {
                    val context = LocalContext.current
                    val startInvoiceScreen =
                        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {}
                    Scaffold(
                        scaffoldState = rememberScaffoldState(), topBar = {
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
                                    .requiredHeight(850.dp)
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
                                    invoice.admissionFee,
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
                            Spacer(modifier = Modifier.requiredHeight(20.dp))
                        }
                    }
                }
            }
        }
    }
}