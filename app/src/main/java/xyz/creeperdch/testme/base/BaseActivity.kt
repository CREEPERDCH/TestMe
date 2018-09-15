package xyz.creeperdch.testme.base

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * Activity基类
 */
abstract class BaseActivity : RxAppCompatActivity() {

    private var isDestory: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(loadLayout())
        isDestory = false
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

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return when {
            ev?.action == MotionEvent.ACTION_DOWN -> when {
                !isShouldHideInput(currentFocus, ev) -> super.dispatchTouchEvent(ev)
                else -> {
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
                    super.dispatchTouchEvent(ev)
                }
            }
            window.superDispatchTouchEvent(ev) -> true
            else -> onTouchEvent(ev)
        }
    }

    private fun isShouldHideInput(v: View?, event: MotionEvent): Boolean {
        return if (v != null && v is EditText) {
            val leftTop = intArrayOf(0, 0)
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop)
            val left = leftTop[0]
            val top = leftTop[1]
            val bottom = top + v.height
            val right = left + v.width
            !(event.rawX > left && event.rawX < right
                    && event.rawY > top && event.rawY < bottom)
        } else false
    }

    /**
     * 线程调度
     */
    protected fun <T> compose(lifecycle: LifecycleTransformer<T>): ObservableTransformer<T, T> {
        return ObservableTransformer { observable ->
            observable
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe {
                        // 可添加网络连接判断等
                    }
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(lifecycle)
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            val decorView = window.decorView
            decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        isDestory = true
    }
}