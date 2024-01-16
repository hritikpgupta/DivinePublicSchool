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
import hg.divineschool.admin.ui.utils.isFeeZero
import hg.divineschool.admin.ui.utils.splitBookList

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
        var bookText1 = ""
        var bookText2 = ""
        var bookText3 = ""
        if (invoice.bookList.isBookListLong()) {
            val list = invoice.bookList.splitBookList()
            bookText1 = list[0]
            bookText2 = list[1]
            bookText3 = list[2]

        } else {
            bookText1 = invoice.bookList
            bookText2 = ""
            bookText3 = ""
        }


        val htmlDocument = HtmlString.getString(
            invoice.invoiceNumber,
            invoice.studentName,
            invoice.guardianName,
            invoice.rollNumber.toString(),
            invoice.className,
            invoice.address,
            invoice.date.trim(),
            invoice.computerFee.toString().isFeeZero(),
            invoice.annualCharge.toString().isFeeZero(),
            invoice.lateFee.toString().isFeeZero(),
            invoice.developmentFee.toString().isFeeZero(),
            invoice.transportFee.toString().isFeeZero(),
            invoice.examFee.toString().isFeeZero(),
            invoice.supplementaryFee.toString().isFeeZero(),
            invoice.tuitionFee.toString().isFeeZero(),
            invoice.bookFee.toString().isFeeZero(),
            invoice.total.toString(),
            invoice.supplementsList,
            invoice.tuitionFeeMonthList,
            bookText1,
            bookText2,
            bookText3
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