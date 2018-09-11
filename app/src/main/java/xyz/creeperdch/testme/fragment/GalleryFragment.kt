package xyz.creeperdch.testme.fragment

import android.annotation.SuppressLint
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.fragment_gallery.*
import xyz.creeperdch.testme.R
import xyz.creeperdch.testme.adapter.GalleryAdapter
import xyz.creeperdch.testme.base.BaseFragment
import xyz.creeperdch.testme.bean.BenefitBean
import xyz.creeperdch.testme.net.ApiFactory
import xyz.creeperdch.testme.net.ApiObserver
import xyz.creeperdch.testme.net.Result

/**
 * Created by creeper on 2018/9/8
 * Hint:
 * Email: wwwwyn4240@gmail.com
 */
class GalleryFragment : BaseFragment() {

    private var galleryAdapter: GalleryAdapter? = null

    private var quantity = 10
    private var page = 1

    override fun loadLayout(): Int {
        return R.layout.fragment_gallery
    }

    override fun initView(view: View) {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        galleryAdapter = GalleryAdapter(null)
        galleryAdapter?.bindToRecyclerView(recyclerView)
        galleryAdapter?.disableLoadMoreIfNotFullPage()
        galleryAdapter?.openLoadAnimation(BaseQuickAdapter.ALPHAIN)
        recyclerView.adapter = galleryAdapter
    }

    override fun initData(view: View) {
        requestBenefit(0, quantity, page)
    }

    /**
     * 福利
     */
    private fun requestBenefit(_tag: Int, quantity: Int, page: Int) {
        val observable = ApiFactory.getInstance().requestBenefit(quantity, page)
        observable.compose(compose(this.bindToLifecycle<Result<List<BenefitBean>>>()))
                .subscribe(object : ApiObserver<List<BenefitBean>>(activity!!) {
                    override fun onHandleSuccess(t: List<BenefitBean>) {
                        when (_tag) {
                            0 -> galleryAdapter?.setNewData(t)
                            1 -> galleryAdapter?.addData(t)
                        }
                    }
                })
    }

    override fun initListener(view: View) {
        floatingActionButton.setOnClickListener {
            recyclerView.smoothScrollToPosition(0)
        }
        smartRefreshLayout.setOnRefreshListener {
            smartRefreshLayout.finishRefresh(3000)
            page = 1
            requestBenefit(0, quantity, page)
        }
        smartRefreshLayout.setOnLoadMoreListener {
            smartRefreshLayout.finishLoadMore(3000)
            page += 1
            requestBenefit(1, quantity, page)
        }
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            @SuppressLint("RestrictedApi")
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val findFirstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                when (newState) {
                    RecyclerView.SCROLL_STATE_IDLE -> {
                        if (findFirstVisibleItemPosition != 0) {
                            floatingActionButton.visibility = View.VISIBLE
                            titleBar.setBackgroundColor(ContextCompat.getColor(activity!!, R.color._1aFFFFFF))
                        } else {
                            floatingActionButton.visibility = View.GONE
                            titleBar.setBackgroundColor(ContextCompat.getColor(activity!!, R.color._FFFFFFFF))
                        }
                    }
                    RecyclerView.SCROLL_STATE_DRAGGING, RecyclerView.SCROLL_STATE_SETTLING -> {
                        titleBar.setBackgroundColor(ContextCompat.getColor(activity!!, R.color._1aFFFFFF))
                    }
                }
            }
        })
    }

    override fun onTaskDestroy() {
        page = 1
    }
}