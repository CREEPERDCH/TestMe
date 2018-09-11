package xyz.creeperdch.testme.net

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import xyz.creeperdch.testme.bean.BenefitBean

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

}