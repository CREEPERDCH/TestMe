package xyz.creeperdch.testme.widget.view

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.util.Log
import android.webkit.*

/**
 * Created by creeper on 2018/9/12
 * Hint:
 * Email: wwwwyn4240@gmail.com
 */
class TestMeWebView : WebView {

    companion object {
        private var TAG = "creeperdch"
    }

    private var isStart = true
    private var lastUrl: String? = null

    constructor(context: Context?) : super(context) {
        initUI()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initUI()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initUI()
    }

    private fun initUI() {
        initWebViewSettings()
    }

    private val client = object : WebViewClient() {

        override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
            Log.i("myWebView", "========onReceivedSslError:$error===========")
            handler.proceed()
        }

        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            isStart = true
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            isStart = false
            if (!url.contains("error_")) {
                lastUrl = url
            }
        }

        override fun onLoadResource(webView: WebView, url: String) {
            //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
            if (url.startsWith("http") || url.startsWith("https") || url.startsWith("ftp")) {
            } else {
                try {
                    val intent = Intent()
                    intent.action = Intent.ACTION_VIEW
                    intent.data = Uri.parse(url)
                    context.startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }
        }


        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
            Log.i("myWebView", "========onReceivedError:$error===========")
            if (request.isForMainFrame) {
                stop(view)
            }
        }

        @TargetApi(Build.VERSION_CODES.M)
        override fun onReceivedHttpError(view: WebView, request: WebResourceRequest, errorResponse: WebResourceResponse) {
            val statusCode = errorResponse.statusCode
            if (502 == statusCode) {
                stop(view)
            }
        }
    }

    private val chromeClient = WebChromeClient()

    internal var downloadListener: DownloadListener = DownloadListener { url, _, _, _, _ ->
        val uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        context.startActivity(intent)
    }

    @SuppressLint("SetJavaScriptEnabled", "ObsoleteSdkInt")
    private fun initWebViewSettings() {
        setBackgroundColor(ContextCompat.getColor(context, android.R.color.white))
        webViewClient = client
        webChromeClient = chromeClient
        setDownloadListener(downloadListener)
        isClickable = true
        setOnTouchListener { _, _ -> false }
        val webSetting = settings
        webSetting.userAgentString = webSetting.userAgentString
        webSetting.javaScriptEnabled = true
        webSetting.javaScriptCanOpenWindowsAutomatically = true
        webSetting.allowFileAccess = true
        webSetting.allowContentAccess = true
        webSetting.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
        webSetting.setSupportZoom(true)
        webSetting.builtInZoomControls = false
        webSetting.loadsImagesAutomatically = true
        webSetting.allowUniversalAccessFromFileURLs = true
        webSetting.useWideViewPort = true
        webSetting.loadWithOverviewMode = true
        webSetting.databaseEnabled = true
        webSetting.setAppCacheEnabled(true)
        webSetting.setAppCachePath(context.cacheDir.toString() + "/webviewCache")
        webSetting.domStorageEnabled = true
        webSetting.setGeolocationEnabled(true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSetting.mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setWebContentsDebuggingEnabled(true)
        }
        webSetting.setSupportMultipleWindows(false)
    }

    private fun stop(view: WebView) {
        try {
            view.stopLoading()
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
        }

        try {
            view.clearView()
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
        }

        view.post {
            view.clearCache(true)
            view.loadUrl("about:blank") // 避免出现默认的错误界面
        }
    }
}