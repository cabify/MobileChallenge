package com.cabify.shared.product.data.service

import com.cabify.shared.product.data.model.GetPromotionsResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

internal interface PromotionsService {
    @GET(GET_PROMOTIONS_PATH)
    fun getPromotions(): Single<GetPromotionsResponse>

    companion object {
        private const val GET_PROMOTIONS_PATH =
            "ivan-carrasco-dev/71fb2f4198c692352909205f9a36e8e4/raw/4bce94fa7b13aecd3bf3a21b10f46acd8fff200a/promotions.json"
    }
}