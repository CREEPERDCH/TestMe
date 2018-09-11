package xyz.creeperdch.testme

import android.app.Application

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
    }
}