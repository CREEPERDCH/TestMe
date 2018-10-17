package xyz.creeperdch.testme.base

import android.os.Bundle
import android.widget.Toast
import com.alibaba.android.arouter.launcher.ARouter
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import xyz.creeperdch.testme.widget.view.FloatToast


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
        initView()
        initData()
        initListener()
    }

    abstract fun initView()
    abstract fun loadLayout(): Int
    abstract fun initData()
    abstract fun initListener()

    fun toast(string: String?) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }

    fun toast(resId: Int) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
    }

    fun floatToast(title: String, content: String) {
        FloatToast.getInstance().createToast(title, content)
    }

    override fun onDestroy() {
        super.onDestroy()
        isDestory = true
    }
}