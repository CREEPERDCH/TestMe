package xyz.creeperdch.testme.net

import android.content.Context
import android.util.Log
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * Created by creeper on 2018/9/11
 * Hint:
 * Email: wwwwyn4240@gmail.com
 */
abstract class ApiObserver<T>(private var context: Context) : Observer<Result<T>> {

    companion object {
        private const val TAG = "creeperdch"
    }

    init {
        this.context = context.applicationContext
    }

    override fun onSubscribe(d: Disposable) {

    }

    override fun onNext(t: Result<T>) {
        val results = t.results
        onHandleSuccess(results)
    }

    override fun onError(e: Throwable) {
        Log.d(TAG, "--->>> onError <<<--- $e")
    }

    override fun onComplete() {
        Log.d(TAG, "--->>> onComplete <<<---")
    }

    protected abstract fun onHandleSuccess(t: T)
}