package xyz.creeperdch.testme.activity

import com.alibaba.android.arouter.facade.annotation.Route
import kotlinx.android.synthetic.main.activity_history.*
import xyz.creeperdch.testme.R
import xyz.creeperdch.testme.base.BaseActivity
import xyz.creeperdch.testme.instance.ARouterConfig

@Route(path = ARouterConfig.ACTIVITY_URL_HISTORY)
class HistoryActivity : BaseActivity() {

    override fun initView() {
        titleBar.centerTextView.text = "历史上的今天"
    }

    override fun loadLayout(): Int {
        return R.layout.activity_history
    }

    override fun initData() {
        rainyView.start()
    }

    override fun initListener() {
        titleBar.leftImageButton.setOnClickListener { finish() }
    }

    override fun onPause() {
        super.onPause()
        rainyView.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        rainyView.stop()
    }
}
