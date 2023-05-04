package lr.projects.fashionmakeupapp.data.repositories

import lr.projects.fashionmakeupapp.data.remote.network.MakeUpService
import lr.projects.fashionmakeupapp.data.remote.network.NetworkResult
import lr.projects.fashionmakeupapp.model.Product
import lr.projects.fashionmakeupapp.data.remote.network.utils.handleApiRequest
import javax.inject.Inject

class MakeUpRepository @Inject constructor(
    private val api: MakeUpService
) {
    suspend fun getProducts(): NetworkResult<List<Product>> {
        return handleApiRequest{
            api.getProducts(PRODUCTS_LIMIT)
        }
    }

    suspend fun getProductById(id:String) : NetworkResult<Product> {
        return handleApiRequest {
            api.getProductById(id)
         }
    }
    companion object {
        const val PRODUCTS_LIMIT = "50"
    }
}


