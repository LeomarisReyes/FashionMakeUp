package lr.projects.fashionmakeupapp.data.remote.network

import lr.projects.fashionmakeupapp.model.Product
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MakeUpService {

    @GET("Makeup/limit/{limit}")
    suspend fun getProducts(@Path("limit") limit:String
    ) : Response<List<Product>>

    @GET("MakeUp/{id}")
    suspend fun getProductById(@Path("id") id:String) : Response<Product>
}

