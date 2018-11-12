package xyz.creeperdch.testme.activity

import android.annotation.SuppressLint
import com.alibaba.android.arouter.facade.annotation.Route
import kotlinx.android.synthetic.main.activity_count_down.*
import xyz.creeperdch.testme.R
import xyz.creeperdch.testme.base.BaseActivity
import xyz.creeperdch.testme.instance.ARouterConfig
import xyz.creeperdch.testme.interfaces.IHandleTimerListener
import java.text.SimpleDateFormat
import java.util.*

@Route(path = ARouterConfig.ACTIVITY_URL_COUNT_DOWN)
class CountDownActivity : BaseActivity() {

    override fun loadLayout(): Int {
        return R.layout.activity_count_down
    }

    override fun initView() {
        titleBar.centerTextView?.text = "活动倒计时"
    }

    @SuppressLint("SimpleDateFormat")
    override fun initData() {
        val sf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val nowTime = sf.format(Date(System.currentTimeMillis()))
        timerView.setEndTime(nowTime, "2018-12-21 00:00:00")
        timerView.setListener(object : IHandleTimerListener {
            override fun handleTimer() {
                floatToast("It just a test. ", "wwwwyn@vip.qq.com")
            }
        })
        timerView.start()
    }

    override fun initListener() {
        titleBar.leftImageButton.setOnClickListener { finish() }
    }

    override fun onDestroy() {
        super.onDestroy()
        timerView.stop()
    }
}
