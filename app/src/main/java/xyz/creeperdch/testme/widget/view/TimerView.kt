package xyz.creeperdch.testme.widget.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import xyz.creeperdch.testme.R
import xyz.creeperdch.testme.interfaces.IHandleTimerListener
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by creeper on 2018/10/29
 * Hint: 倒计时
 * Email: wwwwyn4240@gmail.com
 */
class TimerView : LinearLayout {

    private var dayTitle: TextView? = null
    private var dayTv: TextView? = null
    private var hourTv: TextView? = null
    private var minuteTv: TextView? = null
    private var secondTv: TextView? = null

    private var handleTimerListener: IHandleTimerListener? = null

    private var intervalSeconds: Long = 0L

    private var timer: Timer? = null

    private var handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message?) {
            intervalSeconds--
            setTime()
            if (intervalSeconds <= 0L && handleTimerListener != null) {
                handleTimerListener!!.handleTimer()
            }
        }
    }

    constructor(context: Context?) : super(context) {
        initData(context)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initData(context)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initData(context)
    }

    private fun initData(context: Context?) {
        LayoutInflater.from(context).inflate(R.layout.custom_timer_count_layout, this, true)
        dayTitle = findViewById(R.id.tv_day_title)
        dayTv = findViewById(R.id.tv_day)
        hourTv = findViewById(R.id.tv_hour)
        minuteTv = findViewById(R.id.tv_minute)
        secondTv = findViewById(R.id.tv_second)

        dayTitle?.text = "day"

    }

    @SuppressLint("SimpleDateFormat")
    fun setEndTime(nowTime: String, endTime: String) {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var nowMillionSeconds = 0L
        var endMillionSeconds = 0L
        try {
            nowMillionSeconds = sdf.parse(nowTime).time
            endMillionSeconds = sdf.parse(endTime).time
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        intervalSeconds = (endMillionSeconds - nowMillionSeconds) / 1000

        setTime()
    }

    private fun setTime() {
        val day = intervalSeconds / (24 * 3600)
        val hour = intervalSeconds % (24 * 3600) / 3600
        val minute = intervalSeconds % 3600 / 60
        val second = intervalSeconds % 60
//
//        val day = intervalSeconds / (24 * 3600)
//        val hour = (intervalSeconds / (60 * 60) - day * 24)
//        val minute = ((intervalSeconds / 60) - day * 24 * 60 - hour * 60)
//        val second = (intervalSeconds - day * 24 * 60 * 60 - hour * 60 * 60 - minute * 60)

        if (intervalSeconds >= 24 * 3600) {
            dayTv?.text = getValue(day)
            dayTv?.visibility = View.VISIBLE
            dayTitle?.visibility = View.VISIBLE
        } else {
            dayTv?.visibility = View.GONE
            dayTitle?.visibility = View.GONE
        }

        hourTv?.text = getValue(hour)
        minuteTv?.text = getValue(minute)
        secondTv?.text = getValue(second)
    }

    private fun getValue(value: Long): String {
        return if (value < 10) {
            "0$value"
        } else {
            value.toString()
        }
    }

    fun start() {
        if (intervalSeconds <= 0) {
            return
        }
        if (null == timer) {
            timer = Timer()
            timer!!.schedule(object : TimerTask() {
                override fun run() {
                    if (intervalSeconds > 0) {
                        handler.sendEmptyMessage(0)
                    } else {
                        stop()
                    }
                }
            }, 0, 1000)
        }
    }

    fun stop() {
        if (null != timer) {
            timer?.cancel()
            timer = null
        }
    }

    fun setListener(listener: IHandleTimerListener) {
        this.handleTimerListener = listener
    }
}