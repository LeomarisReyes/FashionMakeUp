package lr.projects.fashionmakeupapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import lr.projects.fashionmakeupapp.model.ProductColor

@Entity
data class ProductEntity(
    @PrimaryKey
    var id: String = "",
    var brand: String?= "",
    var name: String= "",
    var price: String?= "",
    var price_sign: String? = null,
    var currency: String?= null,
    var image_link: String?= "",
    var description: String?= "",
    var tag_list: List<String>? = emptyList(),
    var product_colors: List<ProductColor>? = emptyList(),
)