package xyz.creeperdch.testme

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter

/**
 * Created by creeper on 2018/9/8
 * Hint:
 * Email: wwwwyn4240@gmail.com
 */
class TestMeApplication : Application() {

    companion object {
        lateinit var app: TestMeApplication
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        //开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        if (BuildConfig.DEBUG) {
            //这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog()
            ARouter.openDebug()
        }
        //尽可能早，推荐在Application中初始化
        ARouter.init(app)
    }
}