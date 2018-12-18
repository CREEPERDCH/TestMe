package xyz.creeperdch.testme.activity

import android.os.Handler
import com.alibaba.android.arouter.launcher.ARouter
import xyz.creeperdch.testme.R
import xyz.creeperdch.testme.base.BaseActivity
import xyz.creeperdch.testme.instance.ARouterConfig

class SplashActivity : BaseActivity() {

    override fun loadLayout(): Int {
        return R.layout.activity_splash
    }

    override fun initView() {
    }

    override fun initData() {
        Handler().postDelayed({
            ARouter.getInstance().build(ARouterConfig.ACTIVITY_URL_MAIN).navigation()
            finish()
        }, 1500)
    }

    override fun initListener() {
    }
}
