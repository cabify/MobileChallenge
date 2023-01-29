package com.cabify.shared.product.di

import com.cabify.core.network.service.ServiceBuilder
import com.cabify.shared.product.data.datasource.ProductsNetworkDataSource
import com.cabify.shared.product.data.datasource.ProductsNetworkDataSourceImpl
import com.cabify.shared.product.data.datasource.PromotionsNetworkDataSource
import com.cabify.shared.product.data.datasource.PromotionsNetworkDataSourceImpl
import com.cabify.shared.product.data.mapper.BulkyPromotionResponseMapper
import com.cabify.shared.product.data.mapper.BuyXGetYPromotionResponseMapper
import com.cabify.shared.product.data.mapper.BuyXGetYPromotionResponseMapper.Companion.BUY_X_GET_Y_FREE_DATA_ID
import com.cabify.shared.product.data.mapper.GetProductResponseToDomainMapper
import com.cabify.shared.product.data.mapper.GetPromotionsResponseToDomainMapper
import com.cabify.shared.product.data.mapper.BulkyPromotionResponseMapper.Companion.BULKY_ITEM_DATA_ID
import com.cabify.shared.product.data.repository.ProductsRepositoryImpl
import com.cabify.shared.product.data.repository.PromotionsRepositoryImpl
import com.cabify.shared.product.data.service.ProductsService
import com.cabify.shared.product.data.service.PromotionsService
import com.cabify.shared.product.domain.repository.ProductsRepository
import com.cabify.shared.product.domain.repository.PromotionsRepository
import com.cabify.shared.product.domain.usecase.GetProductsInteractor
import com.cabify.shared.product.domain.usecase.GetProductsUseCase
import com.cabify.shared.product.domain.usecase.GetPromotionsInteractor
import com.cabify.shared.product.domain.usecase.GetPromotionsUseCase
import org.koin.dsl.module

val coreProductModule = module {
    single<GetProductsUseCase> { GetProductsInteractor(get()) }
    single<GetPromotionsUseCase> { GetPromotionsInteractor(get()) }
    single<ProductsRepository> { ProductsRepositoryImpl(get()) }
    single<PromotionsRepository> { PromotionsRepositoryImpl(get()) }
    single<ProductsNetworkDataSource> {
        ProductsNetworkDataSourceImpl(
            get(),
            get<GetProductResponseToDomainMapper>()
        )
    }
    single<PromotionsNetworkDataSource> {
        PromotionsNetworkDataSourceImpl(
            get(),
            get<GetPromotionsResponseToDomainMapper>()
        )
    }
    single { GetProductResponseToDomainMapper() }
    single {
        //In order to apply SOLID open close principle we are injecting the different promotion data mappers
        //of this way the code is open for extension(adding more promotion to data mappers) and closed
        //for modification you don't have to change the logic in GetPromotionsResponseToDomainMapper
        GetPromotionsResponseToDomainMapper(
            mapOf(
                BUY_X_GET_Y_FREE_DATA_ID to BuyXGetYPromotionResponseMapper(),
                BULKY_ITEM_DATA_ID to BulkyPromotionResponseMapper()
            )
        )
    }
    single { get<ServiceBuilder>().build<ProductsService>() }
    single { get<ServiceBuilder>().build<PromotionsService>() }
}