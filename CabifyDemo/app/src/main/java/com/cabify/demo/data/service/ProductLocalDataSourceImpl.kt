package com.cabify.demo.data.service

import com.cabify.demo.data.model.Product
import com.cabify.demo.data.model.ProductDiscount
import com.cabify.demo.data.model.ResponseApi
import java.math.BigDecimal
import java.util.*

class ProductLocalDataSourceImpl : ProductLocalDataSource {

    override fun getProducts(): ResponseApi {

        val response = ResponseApi(
            listOf(
                Product(
                    productId = UUID.randomUUID(),
                    code = ProductDiscount.VOUCHER.name,
                    name = "Cabify Voucher",
                    price = BigDecimal.valueOf(5)
                ),
                Product(
                    productId = UUID.randomUUID(),
                    code = ProductDiscount.MUG.name,
                    name = "Cabify Coffee Mug",
                    price = BigDecimal.valueOf(7.5)
                ),
                Product(
                    productId = UUID.randomUUID(),
                    code = ProductDiscount.TSHIRT.name,
                    name = "Cabify T-shirt",
                    price = BigDecimal.valueOf(20)
                ),
            )
        )

        return response
    }
}