package xyz.creeperdch.testme.fragment

import android.view.KeyEvent
import android.view.View
import kotlinx.android.synthetic.main.fragment_home.*
import xyz.creeperdch.testme.R
import xyz.creeperdch.testme.base.BaseFragment

/**
 * Created by creeper on 2018/9/8
 * Hint:
 * Email: wwwwyn4240@gmail.com
 */
class HomeFragment : BaseFragment() {

    override fun loadLayout(): Int {
        return R.layout.fragment_home
    }

    override fun initView(view: View) {

    }

    override fun initData(view: View) {
        webView.loadUrl("https://晨晨.xyz/")
    }

    override fun initListener(view: View) {
        webView.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
                webView.goBack()
                true
            } else false
        }
        home_smartRefresh.setOnRefreshListener {
            home_smartRefresh.finishRefresh(2000)
            webView.loadUrl("https://晨晨.xyz/")
        }
    }

    override fun onTaskDestroy() {

    }
}