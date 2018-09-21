package xyz.creeperdch.testme.net

import android.content.Context
import android.util.Log
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * Created by creeper on 2018/9/19
 * Hint:
 * Email: wwwwyn4240@gmail.com
 */
abstract class ApiUserObserver<T>(private var context: Context) : Observer<UserResult<T>> {

    companion object {
        private const val TAG = "creeperdch"
    }

    init {
        this.context = context.applicationContext
    }

    override fun onComplete() {
        Log.d(TAG, "--->>> onComplete <<<---")
    }

    override fun onSubscribe(d: Disposable) {
    }

    override fun onNext(t: UserResult<T>) {
        val data = t.data
        onHandleSuccess(data)
    }

    override fun onError(e: Throwable) {
        Log.d(TAG, "--->>> onError <<<--- $e")
    }

    protected abstract fun onHandleSuccess(t: T)
}