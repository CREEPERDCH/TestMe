package xyz.creeperdch.testme.net

import android.util.Log
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by creeper on 2018/9/11
 * Hint:
 * Email: wwwwyn4240@gmail.com
 */
object ApiFactory {

    private const val API_URL = "http://gank.io/"

    private const val API_URL_USER = "http://hn1.api.okayapi.com/"

    private const val APP_KEY = "054DEE1EEC23CCD1A2143A1A8FA1B5EC"

    private const val TIME_OUT = 30L

    private val httpClient = OkHttpClient.Builder()
            //添加通用的header
            .addInterceptor { chain ->
                val builder = chain.request().newBuilder()
                //builder.addHeader("name", "value")
                chain.proceed(builder.build())
            }
            //添加httpLog日志拦截
            .addInterceptor(HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
                Log.i("RetrofitLog", "retrofitBack = $message")
            }).setLevel(HttpLoggingInterceptor.Level.BODY))
            //超时请求时间
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .build()

    private val userClient = OkHttpClient.Builder()
            //添加通用的header
            .addInterceptor { chain ->
                val builder = chain.request().newBuilder()
                builder.addHeader("app_key", APP_KEY)
                chain.proceed(builder.build())
            }
            //添加httpLog日志拦截
            .addInterceptor(HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
                Log.i("RetrofitLog", "retrofitBack = $message")
            }).setLevel(HttpLoggingInterceptor.Level.BODY))
            //超时请求时间
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .build()

    private val gankService = Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create(buildGson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient)
            .build()
            .create(ApiService::class.java)

    private val userService = Retrofit.Builder()
            .baseUrl(API_URL_USER)
            .addConverterFactory(GsonConverterFactory.create(buildGson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(userClient)
            .build()
            .create(ApiService::class.java)

    fun getInstanceForGank(): ApiService {
        return gankService
    }

    fun getInstanceForUser(): ApiService {
        return userService
    }

    private fun buildGson(): Gson {
        return GsonBuilder()
                .serializeNulls()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .create()
    }
}