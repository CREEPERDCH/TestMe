package xyz.creeperdch.testme.widget.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Message
import android.view.Gravity.BOTTOM
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import xyz.creeperdch.testme.R
import java.lang.reflect.Field
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

/**
 * Created by creeper on 2018/12/18
 * Hint:
 * Email: wwwwyn4240@gmail.com
 */
class TestToast {

    private var activity: Activity? = null
    private var mToast: Toast? = null
    private var mTN: Any? = null
    private var mShow: Method? = null
    private var mHide: Method? = null
    private var mViewField: Field? = null
    private var durationTime: Long = 3 * 1000

    private val handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                SHOW -> {
                    sendEmptyMessageDelayed(HIDE, durationTime)
                    show()
                }
                HIDE -> hide()
            }
        }
    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        private var mInstance: TestToast? = null

        private var SHOW: Int = 1
        private var HIDE: Int = 0

        fun getInstance(): TestToast {
            if (null == mInstance) {
                synchronized(TestToast::class.java) {
                    if (null == mInstance) {
                        mInstance = TestToast()
                    }
                }
            }
            return mInstance!!
        }
    }

    fun buildToast(message: String?) {
        if (null == activity) return

        @SuppressLint("InflateParams") val inflateView = activity!!.layoutInflater.inflate(R.layout.weight_view_test_toast_layout, null)
        val messageTv = inflateView.findViewById<TextView>(R.id.tv_msg)
        messageTv.text = message

        mToast = Toast(activity)
        mToast?.view = inflateView
        mToast?.duration = Toast.LENGTH_LONG
        mToast?.setGravity(BOTTOM, 0, 0)

        reflectEnableClick()
        reflectToast()

        if (null != mShow && null != mHide) {
            handler.sendEmptyMessage(SHOW)
        } else {
            mToast?.show()
        }

        inflateView?.setOnClickListener {
        }
    }

    private fun reflectEnableClick() {
        try {
            mTN = getField(mToast, "mTN")
            if (null != mTN) {
                val mParams = getField(mTN, "mParams")
                if (null != mParams && mParams is WindowManager.LayoutParams) {
                    val params: WindowManager.LayoutParams = mParams
                    params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    params.width = WindowManager.LayoutParams.MATCH_PARENT
                    params.height = WindowManager.LayoutParams.WRAP_CONTENT
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getField(any: Any?, fieldName: String): Any? {
        return try {
            val declaredField = any?.javaClass?.getDeclaredField(fieldName)
            declaredField?.isAccessible = true
            declaredField?.get(any)
        } catch (e: NoSuchFieldException) {
            null
        } catch (e: IllegalAccessException) {
            null
        }
    }

    private fun reflectToast() {
        val field: Field?
        try {
            field = mToast?.javaClass?.getDeclaredField("mTN")
            field?.isAccessible = true
            mTN = field?.get(field)
            mShow = mTN?.javaClass?.getDeclaredMethod("show")
            mHide = mTN?.javaClass?.getDeclaredMethod("hide")
            mViewField = mTN?.javaClass?.getDeclaredField("mNextView")
            mViewField?.isAccessible = true
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        }
    }

    fun init(activity: Activity) {
        this.activity = activity
    }

    private fun show() {
        try {
            val mNextViewField = mTN?.javaClass?.getDeclaredField("mNextView")
            mNextViewField?.isAccessible = true
            activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = mToast?.view
            mNextViewField?.set(mTN, view)
            val declaredMethod = mTN?.javaClass?.getDeclaredMethod("show", null)
            declaredMethod?.invoke(mTN, null)
            mShow?.invoke(mTN, null)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun hide() {
        try {
            mHide?.invoke(mTN, null)
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
    }
}