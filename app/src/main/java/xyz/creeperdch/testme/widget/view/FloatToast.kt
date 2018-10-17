package xyz.creeperdch.testme.widget.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Message
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import xyz.creeperdch.testme.R
import java.lang.reflect.Field
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

/**
 * Created by creeper on 2018/10/17
 * Hint:
 * Email: wwwwyn4240@gmail.com
 */
class FloatToast {

    private var SHOW: Int = 1
    private var HIDE: Int = 0

    private var mActivity: Activity? = null
    private var mToast: Toast? = null
    private var mTN: Any? = null
    private var mShow: Method? = null
    private var mHide: Method? = null
    private var mViewField: Field? = null
    private var durationTime: Long = 5 * 1000

    companion object {

        @SuppressLint("StaticFieldLeak")
        private var mInstance: FloatToast? = null

        fun getInstance(): FloatToast {
            if (null == mInstance) {
                mInstance = FloatToast()
            }
            return mInstance!!
        }

    }

    @SuppressLint("InflateParams")
    fun createToast(title: String, content: String) {
        if (null == mActivity) return
        val view = mActivity?.layoutInflater?.inflate(R.layout.weight_view_float_toast_layout, null)
        val toastTitle = view?.findViewById<TextView>(R.id.tv_push_title)
        val toastContent = view?.findViewById<TextView>(R.id.tv_push_content)
        toastTitle?.text = title
        toastContent?.text = content

        mToast = Toast(mActivity)
        mToast?.view = view
        mToast?.duration = Toast.LENGTH_LONG
        mToast?.setGravity(Gravity.TOP, 0, 0)
        reflectEnableClick()
        reflectToast()
        view?.setOnClickListener {
        }
        if (null != mShow && null != mHide) {
            handler.sendEmptyMessage(SHOW)
        } else {
            mToast?.show()
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

    fun init(activity: Activity) {
        mActivity = activity
    }

    fun show() {
        try {
            val mNextViewField = mTN?.javaClass?.getDeclaredField("mNextView")
            mNextViewField?.isAccessible = true
            mActivity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
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