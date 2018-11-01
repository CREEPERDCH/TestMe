package xyz.creeperdch.testme.activity

import com.alibaba.android.arouter.facade.annotation.Route
import kotlinx.android.synthetic.main.activity_count_down.*
import xyz.creeperdch.testme.R
import xyz.creeperdch.testme.base.BaseActivity
import xyz.creeperdch.testme.instance.ARouterConfig

@Route(path = ARouterConfig.ACTIVITY_URL_COUNT_DOWN)
class CountDownActivity : BaseActivity() {

    override fun loadLayout(): Int {
        return R.layout.activity_count_down
    }

    override fun initView() {
    }

    override fun initData() {
        timerView.setTime(23, 59, 59)
        timerView.start()
    }

    override fun initListener() {
    }
}
