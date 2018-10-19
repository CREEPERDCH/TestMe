package xyz.creeperdch.testme.fragment

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.fragment_settings.*
import xyz.creeperdch.testme.R
import xyz.creeperdch.testme.adapter.SettingsAdapter
import xyz.creeperdch.testme.base.BaseFragment
import xyz.creeperdch.testme.bean.SettingsBean
import xyz.creeperdch.testme.instance.ARouterConfig
import xyz.creeperdch.testme.instance.BaseConfig

/**
 * Created by creeper on 2018/9/8
 * Hint:
 * Email: wwwwyn4240@gmail.com
 */
class SettingsFragment : BaseFragment() {

    private var aRouter = ARouter.getInstance()
    private var settingData: ArrayList<SettingsBean>? = null
    private var settingsAdapter: SettingsAdapter? = null

    override fun loadLayout(): Int {
        return R.layout.fragment_settings
    }

    override fun initView(view: View) {
        recycle_settings.layoutManager = LinearLayoutManager(activity!!)
        settingsAdapter = SettingsAdapter(null)
        settingsAdapter?.openLoadAnimation(BaseQuickAdapter.ALPHAIN)
        recycle_settings.adapter = settingsAdapter
    }

    override fun initData(view: View) {
        settingData = ArrayList()
        settingData?.add(SettingsBean("沙雕手电筒", BaseConfig.SETTINGS_LIGHT))
        settingsAdapter?.setNewData(settingData)
    }

    override fun initListener(view: View) {
        settingsAdapter?.setOnItemChildClickListener { adapter, view, position ->
            val clickedItem = adapter.data[position] as SettingsBean
            when (clickedItem.id) {
                BaseConfig.SETTINGS_LIGHT -> {//沙雕手电筒
                    aRouter.build(ARouterConfig.ACTIVITY_URL_LIGHT).navigation()
                }
            }
        }
    }

    override fun onTaskDestroy() {

    }
}