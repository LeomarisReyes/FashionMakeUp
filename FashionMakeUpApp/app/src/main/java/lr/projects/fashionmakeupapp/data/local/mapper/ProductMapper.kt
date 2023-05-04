package lr.projects.fashionmakeupapp.data.local.mapper

import lr.projects.fashionmakeupapp.data.local.ProductEntity
import lr.projects.fashionmakeupapp.model.Product


fun ProductEntity.toDomain() : Product {

    return Product(
        id = this.id,
        brand = this.brand,
        name = this.name,
        price = this.price,
        price_sign = this.price_sign,
        currency = this.currency,
        image_link = this.image_link,
        description = this.description,
        tag_list = this.tag_list,
        product_colors = this.product_colors
    )
}

fun Product.toEntity() : ProductEntity {

    return ProductEntity(
        id = this.id,
        brand = this.brand,
        name = this.name,
        price = this.price,
        price_sign = this.price_sign,
        currency = this.currency,
        image_link = this.image_link,
        description = this.description,
        tag_list = this.tag_list,
        product_colors = this.product_colors
    )
}


