package com.cabify.shared.product.data.service

import com.cabify.shared.product.data.model.GetPromotionsResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

internal interface PromotionsService {
    @GET(GET_PROMOTIONS_PATH)
    fun getPromotions(): Single<GetPromotionsResponse>

    companion object {
        private const val GET_PROMOTIONS_PATH =
            "ivan-carrasco-dev/71fb2f4198c692352909205f9a36e8e4/raw/3045f5586a52f9d70dd5b9d6f77751d9b60bf93f/promotions.json"
    }
}