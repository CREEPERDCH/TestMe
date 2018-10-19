package xyz.creeperdch.testme.util

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.text.TextUtils

/**
 * Created by creeper on 2018/10/19
 * Hint:
 * Email: wwwwyn4240@gmail.com
 */
object FlashLightUtil {

    private var hasFlashLight: Boolean = false
    private var mCameraManager: CameraManager? = null
    private var cameraId: String? = null

    /**
     * 开启闪光灯
     * @return 是否开启
     */
    fun openFlashLight(context: Context): Boolean {
        initCameraFlash(context)
        if (!hasFlashLight) return false
        if (TextUtils.isEmpty(cameraId)) return false
        return try {
            mCameraManager?.setTorchMode(cameraId!!, true)
            true
        } catch (e: CameraAccessException) {
            e.printStackTrace()
            false
        }
    }

    /**
     * 关闭闪光灯
     * @return 是否关闭
     */
    fun closeFlashLight(context: Context): Boolean {
        initCameraFlash(context)
        if (!hasFlashLight) return false
        if (TextUtils.isEmpty(cameraId)) return false
        return try {
            mCameraManager?.setTorchMode(cameraId!!, false)
            true
        } catch (e: CameraAccessException) {
            e.printStackTrace()
            false
        }
    }

    private fun initCameraFlash(context: Context) {
        hasFlashLight = context.applicationContext.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
        mCameraManager = context.applicationContext.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            if (null != mCameraManager) {
                cameraId = mCameraManager?.cameraIdList!![0]
            }
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }
}