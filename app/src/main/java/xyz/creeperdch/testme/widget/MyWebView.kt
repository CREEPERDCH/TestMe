package xyz.creeperdch.testme.widget

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
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.webkit.*
import android.widget.AbsoluteLayout
import android.widget.ProgressBar

class MyWebView : WebView {
    internal var progressBar: ProgressBar? = null
    private var lastUrl: String? = null
    private var isStart = true
    private var listener: OnScrollListener? = null
    var mFilePathCallback: ValueCallback<Uri>? = null
    var mFilePathCallbackAboveL: ValueCallback<Array<Uri>>? = null

    private val chromeClient = object : WebChromeClient() {
        override fun onReceivedTitle(view: WebView, title: String) {
        }


        override fun onProgressChanged(view: WebView, newProgress: Int) {
            progressBar!!.progress = newProgress
            if (progressBar != null && isStart && newProgress < 100) {
                progressBar!!.visibility = View.VISIBLE
            } else if (progressBar != null) {
                progressBar!!.visibility = View.GONE
            }
            super.onProgressChanged(view, newProgress)
        }

        override fun onShowFileChooser(webView: WebView, filePathCallback: ValueCallback<Array<Uri>>, fileChooserParams: FileChooserParams): Boolean {//5.0+
            mFilePathCallbackAboveL = filePathCallback
            return true
        }

        //openFileChooser 方法是隐藏方法
        fun openFileChooser(uploadMsg: ValueCallback<Uri>, acceptType: String, capture: String) {// android 系统版本>4.1.1
            mFilePathCallback = uploadMsg
        }

        fun openFileChooser(uploadMsg: ValueCallback<Uri>) {//android 系统版本<3.0
            mFilePathCallback = uploadMsg
        }

        fun openFileChooser(uploadMsg: ValueCallback<Uri>, acceptType: String) {//android 系统版本3.0+
            mFilePathCallback = uploadMsg
        }
    }


    private val client = object : WebViewClient() {

        override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
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


        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            return false
        }

        override fun shouldInterceptRequest(view: WebView, url: String): WebResourceResponse? {
            return super.shouldInterceptRequest(view, url)

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
                }

            }
        }


        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
        }

        @TargetApi(Build.VERSION_CODES.M)
        override fun onReceivedHttpError(view: WebView, request: WebResourceRequest, errorResponse: WebResourceResponse) {
            // 这个方法在6.0才出现
            val statusCode = errorResponse.statusCode
            if (502 == statusCode) {
                stop(view)
            }
        }

        override fun onReceivedError(view: WebView, errorCode: Int, description: String, failingUrl: String) {
            super.onReceivedError(view, errorCode, description, failingUrl)

        }
    }

    internal var downloadListener: DownloadListener = DownloadListener { url, userAgent, contentDisposition, mimetype, contentLength ->
        val uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        context.startActivity(intent)
    }

    constructor(context: Context) : super(context) {
        initUI()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initUI()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initUI()
    }

    fun setShowProgress(showProgress: Boolean) {
        if (showProgress) {
            progressBar!!.visibility = View.VISIBLE
        } else {
            progressBar!!.visibility = View.GONE
        }
    }

    private fun initUI() {
        progressBar = ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal)
        progressBar!!.max = 100
        addView(progressBar)
        initWebViewSettings()
    }

    override fun loadUrl(url: String?) {
        super.loadUrl(url)
    }

    interface OnScrollListener {
        fun onScrollUp() //上滑

        fun onScrollDown() //下滑

        fun scrollHeight(h: Int)
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun initWebViewSettings() {
        setBackgroundColor(resources.getColor(android.R.color.white))
        webViewClient = client
        webChromeClient = chromeClient
        setDownloadListener(downloadListener)
        isClickable = true
        setOnTouchListener { v, event -> false }
        val webSetting = settings
        webSetting.userAgentString = webSetting.userAgentString + " Thisshop/2.0.1"
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
        webSetting.setAppCacheMaxSize(java.lang.Long.MAX_VALUE)
        webSetting.pluginState = WebSettings.PluginState.ON_DEMAND
        webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSetting.mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setWebContentsDebuggingEnabled(true)
        }
        webSetting.setSupportMultipleWindows(false)
    }


    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        val lp = progressBar!!.layoutParams as AbsoluteLayout.LayoutParams
        lp.x = l
        lp.y = t
        progressBar!!.layoutParams = lp
        super.onScrollChanged(l, t, oldl, oldt)
        if (listener != null) {
            if (t - oldt <= 2) {
                listener!!.onScrollDown()
            }
            if (oldt - t >= 2) {
                listener!!.onScrollUp()
            }
            listener!!.scrollHeight(t)
        }
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

    companion object {
        private val TAG = MyWebView::class.java.simpleName
    }
}
