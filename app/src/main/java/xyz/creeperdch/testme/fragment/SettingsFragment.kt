package xyz.creeperdch.testme.fragment

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.fragment_settings.*
import xyz.creeperdch.testme.R
import xyz.creeperdch.testme.adapter.SettingsAdapter
import xyz.creeperdch.testme.base.BaseFragment
import xyz.creeperdch.testme.bean.ItemsBean
import xyz.creeperdch.testme.instance.ARouterConfig
import xyz.creeperdch.testme.instance.BaseConfig

/**
 * Created by creeper on 2018/9/8
 * Hint:
 * Email: wwwwyn4240@gmail.com
 */
class SettingsFragment : BaseFragment() {

    private var aRouter = ARouter.getInstance()
    private var mSettingData: ArrayList<ItemsBean>? = null
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
        mSettingData = ArrayList()
        mSettingData?.add(ItemsBean("沙雕手电筒", BaseConfig.SETTINGS_LIGHT))
        mSettingData?.add(ItemsBean("活动倒计时Demo", BaseConfig.SETTINGS_COUNT_DOWN))
        mSettingData?.add(ItemsBean("DialogDemo", BaseConfig.SETTINGS_DIALOG))
        mSettingData?.add(ItemsBean("历史上的今天", BaseConfig.SETTINGS_HISTORY))
        settingsAdapter?.setNewData(mSettingData)
    }

    override fun initListener(view: View) {
        settingsAdapter?.setOnItemChildClickListener { adapter, _, position ->
            val clickedItem = adapter.data[position] as ItemsBean
            when (clickedItem.id) {
                BaseConfig.SETTINGS_LIGHT -> {//沙雕手电筒
                    aRouter.build(ARouterConfig.ACTIVITY_URL_LIGHT).navigation()
                }
                BaseConfig.SETTINGS_COUNT_DOWN -> {//活动倒计时
                    aRouter.build(ARouterConfig.ACTIVITY_URL_COUNT_DOWN).navigation()
                }
                BaseConfig.SETTINGS_DIALOG -> {//Dialog
                    aRouter.build(ARouterConfig.ACTIVITY_URL_DIALOG).navigation()
                }
                BaseConfig.SETTINGS_HISTORY -> {//历史上的今天
                    aRouter.build(ARouterConfig.ACTIVITY_URL_HISTORY).navigation()
                }
            }
        }
    }

    override fun onTaskDestroy() {

    }
}