package lr.projects.fashionmakeupapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ProductEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<ProductEntity>)

    @Query("SELECT * FROM ProductEntity")
    fun getProducts() : Flow<List<ProductEntity>>

    @Query("SELECT * FROM ProductEntity where id = :idProduct")
    fun getProductById(idProduct: String) : Flow<ProductEntity>

}
