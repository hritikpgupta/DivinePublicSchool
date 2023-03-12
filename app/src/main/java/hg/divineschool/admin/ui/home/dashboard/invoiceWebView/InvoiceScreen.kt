package hg.divineschool.admin.ui.home.dashboard.invoiceWebView

import android.content.Context
import android.os.Bundle
import android.print.PrintAttributes
import android.print.PrintManager
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import hg.divineschool.admin.R
import hg.divineschool.admin.data.models.Invoice
import hg.divineschool.admin.data.utils.HtmlString
import hg.divineschool.admin.data.utils.getSerializable
import hg.divineschool.admin.ui.utils.isBookListLong
import hg.divineschool.admin.ui.utils.splitDateTime

class InvoiceScreen : ComponentActivity() {
    private var mWebView: WebView? = null
    private var count = 0
    private lateinit var invoice: Invoice

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        invoice = intent.getSerializable("invoiceObject", Invoice::class.java)
        setContentView(R.layout.activity_invoice_screen)
        count = 1
        mWebView = findViewById(R.id.myWeb)
        webView()
    }

    private fun webView() {
        val webView = WebView(this)
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ) = false

            override fun onPageFinished(view: WebView?, url: String?) {
                mWebView = null
                createWebPrintJob(view!!)
            }
        }
        // Generate an HTML document on the fly:
        var bookText1 =""
        var bookText2 =""
        if (invoice.bookList.isBookListLong()){

        }else{
            bookText1 = invoice.bookList
            bookText2 =""
        }


        val htmlDocument = HtmlString.getString(
            invoice.invoiceNumber.toString(),
            invoice.studentName,
            invoice.guardianName,
            invoice.rollNumber.toString(),
            invoice.className,
            invoice.address,
            invoice.date.splitDateTime(),
            invoice.computerFee.toString(),
            invoice.annualCharge.toString(),
            invoice.lateFee.toString(),
            invoice.admissionFee.toString(),
            invoice.transportFee.toString(),
            invoice.examFee.toString(),
            invoice.supplementaryFee.toString(),
            invoice.tuitionFee.toString(),
            invoice.bookFee.toString(),
            invoice.total.toString(),
            invoice.supplementsList,
            invoice.tuitionFeeMonthList,
            bookText1,
            bookText2
        )
        webView.loadDataWithBaseURL(null, htmlDocument, "text/HTML", "UTF-8", null)
        mWebView = webView
    }

    private fun createWebPrintJob(webView: WebView) {

        (this.getSystemService(Context.PRINT_SERVICE) as? PrintManager)?.let { printManager ->

            val jobName = "${getString(R.string.app_name)} Document"

            val printAdapter = webView.createPrintDocumentAdapter(jobName)

            printManager.print(
                jobName,
                printAdapter,
                PrintAttributes.Builder().build()
            ).also { printJob ->

                if (printJob.isCompleted) {
                    this.finishActivity(RESULT_OK)
                } else if (printJob.isCancelled) {
                    this.finishActivity(RESULT_OK)
                } else if (printJob.isFailed) {
                    this.finishActivity(RESULT_OK)
                } else {
                    this.finishActivity(RESULT_OK)
                }
            }
        }
    }


    override fun onResume() {
        super.onResume()
        if (count != 1) {
            finish()
        }
    }

    override fun onPause() {
        super.onPause()
        count = 2
    }

}