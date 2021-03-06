package xyz.creeperdch.testme.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.launcher.ARouter
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.components.support.RxFragment
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import xyz.creeperdch.testme.widget.view.FloatToast
import xyz.creeperdch.testme.widget.view.TestToast

/**
 * Created by creeper on 2018/9/8
 * Hint:
 * Email: wwwwyn4240@gmail.com
 */
abstract class BaseFragment : RxFragment() {

    private var isDestory: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        isDestory = false
        return inflater.inflate(loadLayout(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ARouter.getInstance().inject(activity)
        FloatToast.getInstance().init(activity!!)
        TestToast.getInstance().init(activity!!)
        this.initView(view)
        this.initData(view)
        this.initListener(view)
    }

    abstract fun loadLayout(): Int

    abstract fun initView(view: View)

    abstract fun initData(view: View)

    abstract fun initListener(view: View)

    abstract fun onTaskDestroy()

    fun toast(string: String?) {
        TestToast.getInstance().buildToast(string)
    }

    fun toast(resId: Int) {
        TestToast.getInstance().buildToast(getString(resId))
    }

    fun floatToast(title: String, content: String) {
        FloatToast.getInstance().createToast(title, content)
    }

    /**
     * 线程调度
     */
    protected fun <T> compose(lifecycle: LifecycleTransformer<T>): ObservableTransformer<T, T> {
        return ObservableTransformer { observable ->
            observable
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe {
                        // 可添加网络连接判断等
                    }
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(lifecycle)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        onTaskDestroy()
        isDestory = true
    }
}