package xyz.creeperdch.testme.activity

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.alibaba.android.arouter.facade.annotation.Route
import kotlinx.android.synthetic.main.activity_light.*
import xyz.creeperdch.testme.R
import xyz.creeperdch.testme.base.BaseActivity
import xyz.creeperdch.testme.instance.ARouterConfig
import xyz.creeperdch.testme.util.FlashLightUtil

@Route(path = ARouterConfig.ACTIVITY_URL_LIGHT)
class LightActivity : BaseActivity(), SensorEventListener {

    private var mSensorManager: SensorManager? = null
    private var sensorList: List<Sensor>? = null
    private var isContainSensor: Boolean = false
    private var criticalValue: Float = 40.0F//人眼亮暗临界值

    override fun loadLayout(): Int {
        return R.layout.activity_light
    }

    override fun initView() {
    }

    override fun initData() {
        mSensorManager = this.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorList = mSensorManager?.getSensorList(Sensor.TYPE_ALL)
        run loop@{
            sensorList!!.forEach {
                if (Sensor.TYPE_LIGHT == it.type) {
                    isContainSensor = true
                    return@loop
                }
            }
        }
        register()
    }

    override fun initListener() {
        titleBar.leftImageButton.setOnClickListener {
            unregister()
            finish()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (Sensor.TYPE_LIGHT == event.sensor.type) {
            val isBright = event.values[0] > criticalValue
            if (isBright) {
                FlashLightUtil.openFlashLight(this)
                iv_light_status.setImageResource(R.drawable.icon_open_light)
            } else {
                FlashLightUtil.closeFlashLight(this)
                iv_light_status.setImageResource(R.drawable.icon_close_light)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        register()
    }

    override fun onPause() {
        super.onPause()
        unregister()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregister()

    }

    private fun register() {
        if (null != mSensorManager) {
            val lightSensor = mSensorManager?.getDefaultSensor(Sensor.TYPE_LIGHT)
            if (null != lightSensor && isContainSensor) {
                mSensorManager?.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_FASTEST)
            }
        }
    }

    private fun unregister() {
        if (null != mSensorManager) {
            mSensorManager?.unregisterListener(this)
        }
        FlashLightUtil.closeFlashLight(this)
    }
}
