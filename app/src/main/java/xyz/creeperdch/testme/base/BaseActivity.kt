package xyz.creeperdch.testme.base

import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import xyz.creeperdch.testme.widget.view.FloatToast
import xyz.creeperdch.testme.widget.view.TestToast


/**
 * Activity基类
 */
abstract class BaseActivity : RxAppCompatActivity() {

    private var isDestory: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ARouter.getInstance().inject(this)
        setContentView(loadLayout())
        isDestory = false
        FloatToast.getInstance().init(this)
        TestToast.getInstance().init(this)
        initView()
        initData()
        initListener()
    }

    abstract fun initView()
    abstract fun loadLayout(): Int
    abstract fun initData()
    abstract fun initListener()

    fun toast(string: String?) {
        TestToast.getInstance().buildToast(string)
    }

    fun toast(resId: Int) {
        TestToast.getInstance().buildToast(getString(resId))
    }

    fun floatToast(title: String, content: String) {
        FloatToast.getInstance().createToast(title, content)
    }

    override fun onDestroy() {
        super.onDestroy()
        isDestory = true
    }
}