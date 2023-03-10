package hg.divineschool.admin.ui.home.dashboard.invoiceWebView

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.print.PrintAttributes
import android.print.PrintManager
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import hg.divineschool.admin.R
import hg.divineschool.admin.data.utils.HtmlString
import hg.divineschool.admin.ui.utils.Log_Tag
import hg.divineschool.admin.ui.utils.getActivity

class InvoiceScreen : ComponentActivity() {
    private var mWebView: WebView? = null
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(Log_Tag, "onCreate")
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
        HtmlString.pdfStr = HtmlString.getHtmlForPdf(
            232,
            "24-10-1996",
            "Class One",
            "Hritik Gupta",
            250,
            100,
            300,
            120,
            0,
            100,
            0,
            3340,
            0,
            4532,
            "Jan"
        )

        val htmlDocument = HtmlString.getString()
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

                if (printJob.isCompleted){
                    this.finishActivity(RESULT_OK)
                }else if (printJob.isCancelled){
                    this.finishActivity(RESULT_OK)
                }else if (printJob.isFailed){
                    this.finishActivity(RESULT_OK)
                }else{
                    this.finishActivity(RESULT_OK)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.i(Log_Tag, "onStart $count")
    }

    override fun onResume() {
        super.onResume()
        Log.i(Log_Tag, "onResume $count")
        if (count!= 1){
            finish()
        }
    }

    override fun onPause() {
        super.onPause()
        count = 2
        Log.i(Log_Tag, "onPause $count")
    }

}