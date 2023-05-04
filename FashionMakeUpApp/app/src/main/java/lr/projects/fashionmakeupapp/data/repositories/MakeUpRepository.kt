package lr.projects.fashionmakeupapp.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import lr.projects.fashionmakeupapp.data.local.ProductDao
import lr.projects.fashionmakeupapp.data.local.ProductEntity
import lr.projects.fashionmakeupapp.data.local.mapper.toDomain
import lr.projects.fashionmakeupapp.data.local.mapper.toEntity
import lr.projects.fashionmakeupapp.data.remote.network.MakeUpService
import lr.projects.fashionmakeupapp.data.remote.network.NetworkResult
import lr.projects.fashionmakeupapp.model.Product
import lr.projects.fashionmakeupapp.data.remote.network.utils.handleApiRequest
import javax.inject.Inject

class MakeUpRepository @Inject constructor(
    private val api: MakeUpService,
    private val productDao: ProductDao
) {

    fun getProducts(): Flow<NetworkResult<List<Product>>> {
        return productDao.getProducts()
            .map { products ->
                if (products.isEmpty()) {
                    when(val result = fetchProductsRemote()) {
                        is NetworkResult.ApiError -> result
                        is NetworkResult.ApiException -> result
                        is NetworkResult.Success -> NetworkResult.Success(result.data)
                    }
                } else {
                    NetworkResult.Success(products.map { it.toDomain() })
                }
            }
    }

    private suspend fun fetchProductsRemote(): NetworkResult<List<Product>> {
        return when (val result = handleApiRequest { api.getProducts(PRODUCTS_LIMIT) }) {
            is NetworkResult.ApiError,
            is NetworkResult.ApiException -> result
            is NetworkResult.Success -> {
                productDao.insertProducts(result.data.map { it.toEntity() })
                result
            }
        }
    }

    fun getProductsById(idProduct: String): Flow<NetworkResult<Product>> { // FLOW es COLD

        return productDao.getProductById(idProduct = idProduct)
            .map {  product ->
                if (product.id.isEmpty()) {
                    when (val result = fetchProductsRemoteById(idProduct)) {
                        is NetworkResult.ApiError -> result
                        is NetworkResult.ApiException -> result
                        is NetworkResult.Success -> NetworkResult.Success(result.data)
                    }
                } else {
                    NetworkResult.Success(product.toDomain())
                }
            }
    }

    private suspend fun fetchProductsRemoteById(idProduct: String): NetworkResult<Product> {
        return when (val result = handleApiRequest { api.getProductById(idProduct) }) {
            is NetworkResult.ApiError,
            is NetworkResult.ApiException -> result
            is NetworkResult.Success -> {
                productDao.getProductById(result.data.id)
                result
            }
        }
    }

    companion object {
        const val PRODUCTS_LIMIT = "50"
    }
}


