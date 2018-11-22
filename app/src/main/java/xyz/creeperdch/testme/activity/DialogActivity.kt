package xyz.creeperdch.testme.activity

import android.annotation.SuppressLint
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.activity_dialog.*
import per.goweii.anylayer.AnimHelper
import per.goweii.anylayer.AnyLayer
import xyz.creeperdch.testme.R
import xyz.creeperdch.testme.adapter.DialogAdapter
import xyz.creeperdch.testme.base.BaseActivity
import xyz.creeperdch.testme.bean.ItemsBean
import xyz.creeperdch.testme.instance.ARouterConfig

@Route(path = ARouterConfig.ACTIVITY_URL_DIALOG)
class DialogActivity : BaseActivity() {

    private var dialogAdapter: DialogAdapter? = null
    private var mDialogData: ArrayList<ItemsBean>? = null

    override fun loadLayout(): Int {
        return R.layout.activity_dialog
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        titleBar.centerTextView?.text = "Dialog"
        recycle_dialog.layoutManager = LinearLayoutManager(this)
        dialogAdapter = DialogAdapter(null)
        dialogAdapter?.openLoadAnimation(BaseQuickAdapter.ALPHAIN)
        recycle_dialog.adapter = dialogAdapter
    }

    override fun initData() {
        mDialogData = ArrayList()

        mDialogData?.add(ItemsBean("全屏", TypeDialog.SHOW_FULL))
        mDialogData?.add(ItemsBean("顶部", TypeDialog.SHOW_TOP))
        mDialogData?.add(ItemsBean("底部", TypeDialog.SHOW_BOTTOM))

        mDialogData?.add(ItemsBean("在右边", TypeDialog.SHOW_RIGHT))
        mDialogData?.add(ItemsBean("在左边", TypeDialog.SHOW_LEFT))

        mDialogData?.add(ItemsBean("背景高斯模糊", TypeDialog.SHOW_BLUR))
        mDialogData?.add(ItemsBean("背景变暗", TypeDialog.SHOW_DARK))
        mDialogData?.add(ItemsBean("背景全透明", TypeDialog.SHOW_TRAN))

        mDialogData?.add(ItemsBean("底部进入底部退出", TypeDialog.SHOW_BOTTOM_BOTTOM))
        mDialogData?.add(ItemsBean("底部进入底部退出alpha", TypeDialog.SHOW_BOTTOM_BOTTOM_ALPHA))
        mDialogData?.add(ItemsBean("顶部进入顶部退出", TypeDialog.SHOW_TOP_TOP))
        mDialogData?.add(ItemsBean("顶部进入顶部退出alpha", TypeDialog.SHOW_TOP_TOP_ALPHA))

        mDialogData?.add(ItemsBean("顶部进入底部退出", TypeDialog.SHOW_TOP_BOTTOM))
        mDialogData?.add(ItemsBean("顶部进入底部退出alpha", TypeDialog.SHOW_TOP_BOTTOM_ALPHA))
        mDialogData?.add(ItemsBean("底部进入顶部退出", TypeDialog.SHOW_BOTTOM_TOP))
        mDialogData?.add(ItemsBean("底部进入顶部退出alpha", TypeDialog.SHOW_BOTTOM_TOP_ALPHA))

        mDialogData?.add(ItemsBean("左边进入左边退出", TypeDialog.SHOW_LEFT_LEFT))
        mDialogData?.add(ItemsBean("左边进入左边退出alpha", TypeDialog.SHOW_LEFT_LEFT_ALPHA))
        mDialogData?.add(ItemsBean("右边进入右边退出", TypeDialog.SHOW_RIGHT_RIGHT))
        mDialogData?.add(ItemsBean("右边进入右边退出alpha", TypeDialog.SHOW_RIGHT_RIGHT_ALPHA))

        mDialogData?.add(ItemsBean("左边进入右边退出", TypeDialog.SHOW_LEFT_RIGHT))
        mDialogData?.add(ItemsBean("左边进入右边退出alpha", TypeDialog.SHOW_LEFT_RIGHT_ALPHA))
        mDialogData?.add(ItemsBean("右边进入左边退出", TypeDialog.SHOW_RIGHT_LEFT))
        mDialogData?.add(ItemsBean("右边进入左边退出alpha", TypeDialog.SHOW_RIGHT_LEFT_ALPHA))

        mDialogData?.add(ItemsBean("圆形", TypeDialog.SHOW_CIRCLE))

        dialogAdapter?.setNewData(mDialogData)
    }

    override fun initListener() {
        titleBar.leftImageButton.setOnClickListener { finish() }
        dialogAdapter?.setOnItemChildClickListener { adapter, _, position ->
            val currentItem = adapter.data[position] as ItemsBean
            when (currentItem.id) {
                TypeDialog.SHOW_FULL -> {
                    AnyLayer.with(this)
                            .contentView(R.layout.dialog_1_layout)
                            .cancelableOnTouchOutside(true)
                            .cancelableOnClickKeyBack(true)
                            .onClickToDismiss(R.id.iv_1)
                            .show()

                }
                TypeDialog.SHOW_TOP -> {
                    AnyLayer.with(this)
                            .contentView(R.layout.dialog_2_layout)
                            .cancelableOnTouchOutside(true)
                            .cancelableOnClickKeyBack(true)
                            .backgroundColorRes(R.color._dialog_background)
                            .gravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL)
                            .contentAnim(object : AnyLayer.IAnim {
                                override fun inAnim(target: View?): Long {
                                    AnimHelper.startTopInAnim(target, 300)
                                    return 300
                                }

                                override fun outAnim(target: View?): Long {
                                    AnimHelper.startTopOutAnim(target, 300)
                                    return 300
                                }
                            })
                            .onClick(R.id.tv_ok) { anyLayer, _ ->
                                anyLayer.dismiss()
                            }
                            .show()
                }
                TypeDialog.SHOW_BOTTOM -> {
                    AnyLayer.with(this)
                            .contentView(R.layout.dialog_2_layout)
                            .cancelableOnTouchOutside(true)
                            .cancelableOnClickKeyBack(true)
                            .backgroundColorRes(R.color._dialog_background)
                            .gravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL)
                            .contentAnim(object : AnyLayer.IAnim {
                                override fun inAnim(target: View?): Long {
                                    AnimHelper.startBottomInAnim(target, 300)
                                    return 300
                                }

                                override fun outAnim(target: View?): Long {
                                    AnimHelper.startBottomOutAnim(target, 300)
                                    return 300
                                }
                            })
                            .onClick(R.id.tv_ok) { anyLayer, _ ->
                                anyLayer.dismiss()
                            }
                            .show()
                }
                TypeDialog.SHOW_RIGHT -> {
                    AnyLayer.with(this)
                            .contentView(R.layout.dialog_2_layout)
                            .cancelableOnTouchOutside(true)
                            .cancelableOnClickKeyBack(true)
                            .backgroundColorRes(R.color._dialog_background)
                            .gravity(Gravity.START or Gravity.CENTER_VERTICAL)
                            .contentAnim(object : AnyLayer.IAnim {
                                override fun inAnim(target: View?): Long {
                                    AnimHelper.startLeftInAnim(target, 300)
                                    return 300
                                }

                                override fun outAnim(target: View?): Long {
                                    AnimHelper.startLeftOutAnim(target, 300)
                                    return 300
                                }
                            })
                            .onClick(R.id.tv_ok) { anyLayer, _ ->
                                anyLayer.dismiss()
                            }
                            .show()
                }
                TypeDialog.SHOW_LEFT -> {
                    AnyLayer.with(this)
                            .contentView(R.layout.dialog_2_layout)
                            .cancelableOnTouchOutside(true)
                            .cancelableOnClickKeyBack(true)
                            .backgroundColorRes(R.color._dialog_background)
                            .gravity(Gravity.END or Gravity.CENTER_VERTICAL)
                            .contentAnim(object : AnyLayer.IAnim {
                                override fun inAnim(target: View?): Long {
                                    AnimHelper.startRightInAnim(target, 300)
                                    return 300
                                }

                                override fun outAnim(target: View?): Long {
                                    AnimHelper.startRightOutAnim(target, 300)
                                    return 300
                                }
                            })
                            .onClick(R.id.tv_ok) { anyLayer, _ ->
                                anyLayer.dismiss()
                            }
                            .show()
                }
                TypeDialog.SHOW_BLUR -> {
                    AnyLayer.with(this)
                            .contentView(R.layout.dialog_2_layout)
                            .cancelableOnTouchOutside(true)
                            .cancelableOnClickKeyBack(true)
                            .backgroundBlurRadius(8F)
                            .backgroundBlurScale(8F)
                            .backgroundColorRes(R.color._FFFFFFFF)
                            .onClick(R.id.tv_ok) { anyLayer, _ ->
                                anyLayer.dismiss()
                            }
                            .show()
                }
                TypeDialog.SHOW_DARK -> {
                    AnyLayer.with(this)
                            .contentView(R.layout.dialog_2_layout)
                            .cancelableOnTouchOutside(true)
                            .cancelableOnClickKeyBack(true)
                            .backgroundColorRes(R.color._dialog_background)
                            .onClick(R.id.tv_ok) { anyLayer, _ ->
                                anyLayer.dismiss()
                            }
                            .show()
                }
                TypeDialog.SHOW_TRAN -> {
                    AnyLayer.with(this)
                            .contentView(R.layout.dialog_2_layout)
                            .cancelableOnTouchOutside(true)
                            .cancelableOnClickKeyBack(true)
                            .onClick(R.id.tv_ok) { anyLayer, _ ->
                                anyLayer.dismiss()
                            }
                            .show()
                }
                TypeDialog.SHOW_BOTTOM_BOTTOM -> {
                    AnyLayer.with(this)
                            .contentView(R.layout.dialog_2_layout)
                            .cancelableOnTouchOutside(true)
                            .cancelableOnClickKeyBack(true)
                            .backgroundColorRes(R.color._dialog_background)
                            .contentAnim(object : AnyLayer.IAnim {
                                override fun inAnim(target: View?): Long {
                                    AnimHelper.startBottomInAnim(target, 300)
                                    return 300
                                }

                                override fun outAnim(target: View?): Long {
                                    AnimHelper.startBottomOutAnim(target, 300)
                                    return 300
                                }
                            })
                            .onClick(R.id.tv_ok) { anyLayer, _ ->
                                anyLayer.dismiss()
                            }
                            .show()
                }
                TypeDialog.SHOW_BOTTOM_BOTTOM_ALPHA -> {
                    AnyLayer.with(this)
                            .contentView(R.layout.dialog_2_layout)
                            .cancelableOnTouchOutside(true)
                            .cancelableOnClickKeyBack(true)
                            .backgroundColorRes(R.color._dialog_background)
                            .contentAnim(object : AnyLayer.IAnim {
                                override fun inAnim(target: View?): Long {
                                    AnimHelper.startBottomAlphaInAnim(target, 300)
                                    return 300
                                }

                                override fun outAnim(target: View?): Long {
                                    AnimHelper.startBottomAlphaOutAnim(target, 300)
                                    return 300
                                }
                            })
                            .onClick(R.id.tv_ok) { anyLayer, _ ->
                                anyLayer.dismiss()
                            }
                            .show()
                }
                TypeDialog.SHOW_TOP_TOP -> {
                    AnyLayer.with(this)
                            .contentView(R.layout.dialog_2_layout)
                            .cancelableOnTouchOutside(true)
                            .cancelableOnClickKeyBack(true)
                            .backgroundColorRes(R.color._dialog_background)
                            .contentAnim(object : AnyLayer.IAnim {
                                override fun inAnim(target: View?): Long {
                                    AnimHelper.startTopInAnim(target, 300)
                                    return 300
                                }

                                override fun outAnim(target: View?): Long {
                                    AnimHelper.startTopOutAnim(target, 300)
                                    return 300
                                }
                            })
                            .onClick(R.id.tv_ok) { anyLayer, _ ->
                                anyLayer.dismiss()
                            }
                            .show()
                }
                TypeDialog.SHOW_TOP_TOP_ALPHA -> {
                    AnyLayer.with(this)
                            .contentView(R.layout.dialog_2_layout)
                            .cancelableOnTouchOutside(true)
                            .cancelableOnClickKeyBack(true)
                            .backgroundColorRes(R.color._dialog_background)
                            .contentAnim(object : AnyLayer.IAnim {
                                override fun inAnim(target: View?): Long {
                                    AnimHelper.startTopAlphaInAnim(target, 300)
                                    return 300
                                }

                                override fun outAnim(target: View?): Long {
                                    AnimHelper.startTopAlphaOutAnim(target, 300)
                                    return 300
                                }
                            })
                            .onClick(R.id.tv_ok) { anyLayer, _ ->
                                anyLayer.dismiss()
                            }
                            .show()
                }
                TypeDialog.SHOW_TOP_BOTTOM -> {
                    AnyLayer.with(this)
                            .contentView(R.layout.dialog_2_layout)
                            .cancelableOnTouchOutside(true)
                            .cancelableOnClickKeyBack(true)
                            .backgroundColorRes(R.color._dialog_background)
                            .contentAnim(object : AnyLayer.IAnim {
                                override fun inAnim(target: View?): Long {
                                    AnimHelper.startTopInAnim(target, 300)
                                    return 300
                                }

                                override fun outAnim(target: View?): Long {
                                    AnimHelper.startBottomOutAnim(target, 300)
                                    return 300
                                }
                            })
                            .onClick(R.id.tv_ok) { anyLayer, _ ->
                                anyLayer.dismiss()
                            }
                            .show()
                }
                TypeDialog.SHOW_TOP_BOTTOM_ALPHA -> {
                    AnyLayer.with(this)
                            .contentView(R.layout.dialog_2_layout)
                            .cancelableOnTouchOutside(true)
                            .cancelableOnClickKeyBack(true)
                            .backgroundColorRes(R.color._dialog_background)
                            .contentAnim(object : AnyLayer.IAnim {
                                override fun inAnim(target: View?): Long {
                                    AnimHelper.startTopAlphaInAnim(target, 300)
                                    return 300
                                }

                                override fun outAnim(target: View?): Long {
                                    AnimHelper.startBottomAlphaOutAnim(target, 300)
                                    return 300
                                }
                            })
                            .onClick(R.id.tv_ok) { anyLayer, _ ->
                                anyLayer.dismiss()
                            }
                            .show()
                }
                TypeDialog.SHOW_BOTTOM_TOP -> {
                    AnyLayer.with(this)
                            .contentView(R.layout.dialog_2_layout)
                            .cancelableOnTouchOutside(true)
                            .cancelableOnClickKeyBack(true)
                            .backgroundColorRes(R.color._dialog_background)
                            .contentAnim(object : AnyLayer.IAnim {
                                override fun inAnim(target: View?): Long {
                                    AnimHelper.startBottomInAnim(target, 300)
                                    return 300
                                }

                                override fun outAnim(target: View?): Long {
                                    AnimHelper.startTopOutAnim(target, 300)
                                    return 300
                                }
                            })
                            .onClick(R.id.tv_ok) { anyLayer, _ ->
                                anyLayer.dismiss()
                            }
                            .show()
                }
                TypeDialog.SHOW_BOTTOM_TOP_ALPHA -> {
                    AnyLayer.with(this)
                            .contentView(R.layout.dialog_2_layout)
                            .cancelableOnTouchOutside(true)
                            .cancelableOnClickKeyBack(true)
                            .backgroundColorRes(R.color._dialog_background)
                            .contentAnim(object : AnyLayer.IAnim {
                                override fun inAnim(target: View?): Long {
                                    AnimHelper.startBottomAlphaInAnim(target, 300)
                                    return 300
                                }

                                override fun outAnim(target: View?): Long {
                                    AnimHelper.startTopAlphaOutAnim(target, 300)
                                    return 300
                                }
                            })
                            .onClick(R.id.tv_ok) { anyLayer, _ ->
                                anyLayer.dismiss()
                            }
                            .show()
                }
                TypeDialog.SHOW_LEFT_LEFT -> {
                    AnyLayer.with(this)
                            .contentView(R.layout.dialog_2_layout)
                            .cancelableOnTouchOutside(true)
                            .cancelableOnClickKeyBack(true)
                            .backgroundColorRes(R.color._dialog_background)
                            .contentAnim(object : AnyLayer.IAnim {
                                override fun inAnim(target: View?): Long {
                                    AnimHelper.startLeftInAnim(target, 300)
                                    return 300
                                }

                                override fun outAnim(target: View?): Long {
                                    AnimHelper.startLeftOutAnim(target, 300)
                                    return 300
                                }
                            })
                            .onClick(R.id.tv_ok) { anyLayer, _ ->
                                anyLayer.dismiss()
                            }
                            .show()
                }
                TypeDialog.SHOW_LEFT_LEFT_ALPHA -> {
                    AnyLayer.with(this)
                            .contentView(R.layout.dialog_2_layout)
                            .cancelableOnTouchOutside(true)
                            .cancelableOnClickKeyBack(true)
                            .backgroundColorRes(R.color._dialog_background)
                            .contentAnim(object : AnyLayer.IAnim {
                                override fun inAnim(target: View?): Long {
                                    AnimHelper.startLeftAlphaInAnim(target, 300)
                                    return 300
                                }

                                override fun outAnim(target: View?): Long {
                                    AnimHelper.startLeftAlphaOutAnim(target, 300)
                                    return 300
                                }
                            })
                            .onClick(R.id.tv_ok) { anyLayer, _ ->
                                anyLayer.dismiss()
                            }
                            .show()
                }
                TypeDialog.SHOW_RIGHT_RIGHT -> {
                    AnyLayer.with(this)
                            .contentView(R.layout.dialog_2_layout)
                            .cancelableOnTouchOutside(true)
                            .cancelableOnClickKeyBack(true)
                            .backgroundColorRes(R.color._dialog_background)
                            .contentAnim(object : AnyLayer.IAnim {
                                override fun inAnim(target: View?): Long {
                                    AnimHelper.startRightInAnim(target, 300)
                                    return 300
                                }

                                override fun outAnim(target: View?): Long {
                                    AnimHelper.startRightOutAnim(target, 300)
                                    return 300
                                }
                            })
                            .onClick(R.id.tv_ok) { anyLayer, _ ->
                                anyLayer.dismiss()
                            }
                            .show()
                }
                TypeDialog.SHOW_RIGHT_RIGHT_ALPHA -> {
                    AnyLayer.with(this)
                            .contentView(R.layout.dialog_2_layout)
                            .cancelableOnTouchOutside(true)
                            .cancelableOnClickKeyBack(true)
                            .backgroundColorRes(R.color._dialog_background)
                            .contentAnim(object : AnyLayer.IAnim {
                                override fun inAnim(target: View?): Long {
                                    AnimHelper.startRightAlphaInAnim(target, 300)
                                    return 300
                                }

                                override fun outAnim(target: View?): Long {
                                    AnimHelper.startRightAlphaOutAnim(target, 300)
                                    return 300
                                }
                            })
                            .onClick(R.id.tv_ok) { anyLayer, _ ->
                                anyLayer.dismiss()
                            }
                            .show()
                }
                TypeDialog.SHOW_LEFT_RIGHT -> {
                    AnyLayer.with(this)
                            .contentView(R.layout.dialog_2_layout)
                            .cancelableOnTouchOutside(true)
                            .cancelableOnClickKeyBack(true)
                            .backgroundColorRes(R.color._dialog_background)
                            .contentAnim(object : AnyLayer.IAnim {
                                override fun inAnim(target: View?): Long {
                                    AnimHelper.startLeftInAnim(target, 300)
                                    return 300
                                }

                                override fun outAnim(target: View?): Long {
                                    AnimHelper.startRightOutAnim(target, 300)
                                    return 300
                                }
                            })
                            .onClick(R.id.tv_ok) { anyLayer, _ ->
                                anyLayer.dismiss()
                            }
                            .show()
                }
                TypeDialog.SHOW_LEFT_RIGHT_ALPHA -> {
                    AnyLayer.with(this)
                            .contentView(R.layout.dialog_2_layout)
                            .cancelableOnTouchOutside(true)
                            .cancelableOnClickKeyBack(true)
                            .backgroundColorRes(R.color._dialog_background)
                            .contentAnim(object : AnyLayer.IAnim {
                                override fun inAnim(target: View?): Long {
                                    AnimHelper.startLeftAlphaInAnim(target, 300)
                                    return 300
                                }

                                override fun outAnim(target: View?): Long {
                                    AnimHelper.startRightAlphaOutAnim(target, 300)
                                    return 300
                                }
                            })
                            .onClick(R.id.tv_ok) { anyLayer, _ ->
                                anyLayer.dismiss()
                            }
                            .show()
                }
                TypeDialog.SHOW_RIGHT_LEFT -> {
                    AnyLayer.with(this)
                            .contentView(R.layout.dialog_2_layout)
                            .cancelableOnTouchOutside(true)
                            .cancelableOnClickKeyBack(true)
                            .backgroundColorRes(R.color._dialog_background)
                            .contentAnim(object : AnyLayer.IAnim {
                                override fun inAnim(target: View?): Long {
                                    AnimHelper.startRightInAnim(target, 300)
                                    return 300
                                }

                                override fun outAnim(target: View?): Long {
                                    AnimHelper.startLeftOutAnim(target, 300)
                                    return 300
                                }
                            })
                            .onClick(R.id.tv_ok) { anyLayer, _ ->
                                anyLayer.dismiss()
                            }
                            .show()
                }
                TypeDialog.SHOW_RIGHT_LEFT_ALPHA -> {
                    AnyLayer.with(this)
                            .contentView(R.layout.dialog_2_layout)
                            .cancelableOnTouchOutside(true)
                            .cancelableOnClickKeyBack(true)
                            .backgroundColorRes(R.color._dialog_background)
                            .contentAnim(object : AnyLayer.IAnim {
                                override fun inAnim(target: View?): Long {
                                    AnimHelper.startRightAlphaInAnim(target, 300)
                                    return 300
                                }

                                override fun outAnim(target: View?): Long {
                                    AnimHelper.startLeftAlphaOutAnim(target, 300)
                                    return 300
                                }
                            })
                            .onClick(R.id.tv_ok) { anyLayer, _ ->
                                anyLayer.dismiss()
                            }
                            .show()
                }
                TypeDialog.SHOW_CIRCLE -> {
                    AnyLayer.with(this)
                            .contentView(R.layout.dialog_2_layout)
                            .cancelableOnTouchOutside(true)
                            .cancelableOnClickKeyBack(true)
                            .backgroundColorRes(R.color._dialog_background)
                            .contentAnim(object : AnyLayer.IAnim {
                                override fun inAnim(target: View): Long {
                                    AnimHelper.startCircularRevealInAnim(target, target.measuredWidth / 2, target.measuredHeight / 2, 300)
                                    return 300
                                }

                                override fun outAnim(target: View): Long {
                                    AnimHelper.startCircularRevealOutAnim(target, target.measuredWidth / 2, target.measuredHeight / 2, 300)
                                    return 300
                                }
                            })
                            .onClick(R.id.tv_ok) { anyLayer, _ ->
                                anyLayer.dismiss()
                            }
                            .show()
                }
            }
        }
    }

    private object TypeDialog {

        const val SHOW_FULL = 0
        const val SHOW_TOP = 1
        const val SHOW_BOTTOM = 2

        const val SHOW_RIGHT = 4
        const val SHOW_LEFT = 7

        const val SHOW_BLUR = 8
        const val SHOW_DARK = 9
        const val SHOW_TRAN = 10

        const val SHOW_BOTTOM_BOTTOM = 11
        const val SHOW_BOTTOM_BOTTOM_ALPHA = 12
        const val SHOW_TOP_TOP = 13
        const val SHOW_TOP_TOP_ALPHA = 14

        const val SHOW_TOP_BOTTOM = 15
        const val SHOW_TOP_BOTTOM_ALPHA = 16
        const val SHOW_BOTTOM_TOP = 17
        const val SHOW_BOTTOM_TOP_ALPHA = 18

        const val SHOW_LEFT_LEFT = 19
        const val SHOW_LEFT_LEFT_ALPHA = 20
        const val SHOW_RIGHT_RIGHT = 21
        const val SHOW_RIGHT_RIGHT_ALPHA = 22

        const val SHOW_LEFT_RIGHT = 23
        const val SHOW_LEFT_RIGHT_ALPHA = 24
        const val SHOW_RIGHT_LEFT = 25
        const val SHOW_RIGHT_LEFT_ALPHA = 26

        const val SHOW_CIRCLE = 27

    }
}
