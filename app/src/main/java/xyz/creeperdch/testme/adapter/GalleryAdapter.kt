package xyz.creeperdch.testme.adapter

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import xyz.creeperdch.testme.R
import xyz.creeperdch.testme.bean.BenefitBean

/**
 * Created by creeper on 2018/9/8
 * Hint:
 * Email: wwwwyn4240@gmail.com
 */
class GalleryAdapter(data: MutableList<BenefitBean>?) : BaseQuickAdapter<BenefitBean, BaseViewHolder>(R.layout.recycle_item_benefit_layout, data) {

    override fun convert(helper: BaseViewHolder, item: BenefitBean) {
        val imageView = helper.getView<ImageView>(R.id.imageView)
        Glide.with(mContext).load(item.url).into(imageView)
        helper.addOnClickListener(R.id.imageView)
    }
}