package xyz.creeperdch.testme.fragment

import android.util.Log
import android.view.View
import xyz.creeperdch.testme.R
import xyz.creeperdch.testme.base.BaseFragment
import xyz.creeperdch.testme.bean.RegisterBean
import xyz.creeperdch.testme.net.ApiFactory
import xyz.creeperdch.testme.net.ApiUserObserver
import xyz.creeperdch.testme.net.UserResult
import xyz.creeperdch.testme.util.MD5Util

/**
 * Created by creeper on 2018/9/8
 * Hint:
 * Email: wwwwyn4240@gmail.com
 */
class UserFragment : BaseFragment() {
    override fun loadLayout(): Int {
        return R.layout.fragment_user
    }

    override fun initView(view: View) {
    }

    override fun initData(view: View) {
        register()
    }

    private fun register() {
        val observable = ApiFactory.getInstanceForUser().register("CREEPERDCH", MD5Util.getMD5("Gouliguojiass1"))
        observable.compose(compose(this.bindToLifecycle<UserResult<RegisterBean>>()))
                .subscribe(object : ApiUserObserver<RegisterBean>(activity!!) {
                    override fun onHandleSuccess(t: RegisterBean) {
                        Log.d("creeperdch", t.err_msg)
                    }

                    override fun onError(e: Throwable) {
                        Log.d("creeperdch", e.message)
                    }
                })
    }

    override fun initListener(view: View) {
    }

    override fun onTaskDestroy() {

    }
}