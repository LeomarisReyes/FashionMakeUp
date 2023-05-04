package lr.projects.fashionmakeupapp.model

data class Product (
    var id: String = "",
    var brand: String?= "",
    var name: String= "",
    var price: String?= "",
    var price_sign: String? = null,
    var currency: String?= null,
    var image_link: String?= "",
    var product_link: String?= "",
    var website_link: String?= "",
    var description: String?= "",
    var rating: Double?= 0.0,
    var category: String?= "",
    var product_type: String?= null,
    var tag_list: List<String>?=null,
    var created_at: String?= "",
    var updated_at: String?= "",
    var product_api_url: String?=null,
    var api_featured_image: String?=null,
    var product_colors: List<ProductColor>?=null
)
