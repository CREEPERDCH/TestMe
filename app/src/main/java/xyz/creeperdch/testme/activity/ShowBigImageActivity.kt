package xyz.creeperdch.testme.activity

import android.support.design.widget.Snackbar
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_show_big_image.*
import xyz.creeperdch.testme.R
import xyz.creeperdch.testme.base.BaseActivity
import xyz.creeperdch.testme.instance.ARouterConfig

@Route(path = ARouterConfig.ACTIVITY_URL_SHOW_BIG_IMAGE)
class ShowBigImageActivity : BaseActivity() {

    @JvmField
    @Autowired(name = "url")
    var url: String? = null

    override fun loadLayout(): Int {
        return R.layout.activity_show_big_image
    }

    override fun initView() {
    }

    override fun initData() {
        Glide.with(this).load(url).into(photoView)
    }

    override fun initListener() {
        photoView.setOnViewTapListener { _, _, _ ->
            finish()
        }
        photoView.setOnLongClickListener {
            Snackbar.make(ll_show_root, "保存到相册？", Snackbar.LENGTH_LONG)
                    .setAction("保存") {
                        //                        floatToast("保存成功！", "路径: /data/data/media/")
                        toast("保存成功！")
                    }
                    .show()
            true
        }
    }
}