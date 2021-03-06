package xyz.creeperdch.testme.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import xyz.creeperdch.testme.R
import xyz.creeperdch.testme.bean.ItemsBean

/**
 * Created by creeper on 2018/10/19
 * Hint:
 * Email: wwwwyn4240@gmail.com
 */
class SettingsAdapter(data: MutableList<ItemsBean>?) : BaseQuickAdapter<ItemsBean, BaseViewHolder>(R.layout.recycle_item_settings_layout, data) {

    override fun convert(helper: BaseViewHolder, item: ItemsBean) {
        helper.setText(R.id.tv_title, item.title)
                .addOnClickListener(R.id.tv_title)
    }
}