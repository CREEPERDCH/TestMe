package xyz.creeperdch.testme.net

import io.reactivex.Observable
import retrofit2.http.*
import xyz.creeperdch.testme.bean.BenefitBean
import xyz.creeperdch.testme.bean.RegisterBean

/**
 * Created by creeper on 2018/9/10
 * Hint:
 * Email: wwwwyn4240@gmail.com
 */
interface ApiService {

    @GET("/api/data/福利/{quantity}/{page}")
    fun requestBenefit(
            @Path("quantity") quantity: Int,
            @Path("page") page: Int
    ): Observable<Result<List<BenefitBean>>>

    @FormUrlEncoded
    @POST("?s=App.User.Register/}")
    fun register(
            @Field("username") username: String,
            @Field("password") password: String
    ): Observable<UserResult<RegisterBean>>
}