package com.cabify.shared.product.di

import com.cabify.core.network.service.ServiceBuilder
import com.cabify.shared.product.data.mapper.GetPromotionsResponseToDomainMapper
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
    single<com.cabify.shared.product.data.datasource.ProductsNetworkDataSource> {
        com.cabify.shared.product.data.datasource.ProductsNetworkDataSourceImpl(
            get(),
            get<com.cabify.shared.product.data.mapper.GetProductResponseToDomainMapper>()
        )
    }
    single<com.cabify.shared.product.data.datasource.PromotionsNetworkDataSource> {
        com.cabify.shared.product.data.datasource.PromotionsNetworkDataSourceImpl(
            get(),
            get<GetPromotionsResponseToDomainMapper>()
        )
    }
    single { com.cabify.shared.product.data.mapper.GetProductResponseToDomainMapper() }
    single { GetPromotionsResponseToDomainMapper() }
    single { get<ServiceBuilder>().build<ProductsService>() }
    single { get<ServiceBuilder>().build<PromotionsService>() }
}