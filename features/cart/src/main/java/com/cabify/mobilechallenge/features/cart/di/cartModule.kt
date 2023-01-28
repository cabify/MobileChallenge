package com.cabify.mobilechallenge.features.cart.di

import com.cabify.mobilechallenge.core.base.di.OBSERVE_SCHEDULER
import com.cabify.mobilechallenge.core.base.di.SUBSCRIBE_SCHEDULER
import com.cabify.mobilechallenge.features.cart.domain.factory.OrderFactory
import com.cabify.mobilechallenge.features.cart.domain.factory.OrderFactoryImpl
import com.cabify.mobilechallenge.features.cart.domain.processor.BulkyItemsPromotionProcessor
import com.cabify.mobilechallenge.features.cart.domain.processor.BuyXGetYFreePromotionProcessor
import com.cabify.mobilechallenge.features.cart.domain.usecase.CheckoutOrderInteractor
import com.cabify.mobilechallenge.features.cart.domain.usecase.CheckoutOrderUseCase
import com.cabify.mobilechallenge.features.cart.domain.usecase.GetOrderChangesInteractor
import com.cabify.mobilechallenge.features.cart.domain.usecase.GetOrderChangesUseCase
import com.cabify.mobilechallenge.features.cart.presentation.mapper.BulkyItemsOrderToPromotionPresentationMapper
import com.cabify.mobilechallenge.features.cart.presentation.mapper.BuyXGetYOrderToPromotionPresentationMapper
import com.cabify.mobilechallenge.features.cart.presentation.mapper.OrderEntityToPresentationMapper
import com.cabify.mobilechallenge.features.cart.presentation.viewmodel.CartViewModel
import com.cabify.shared.product.domain.entities.BulkyItemsPromotionEntity
import com.cabify.shared.product.domain.entities.BuyXGetYFreePromotionEntity
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val cartModule = module {
    viewModel {
        CartViewModel(
            get(),
            get(),
            get(),
            subscribeScheduler = get(SUBSCRIBE_SCHEDULER),
            observerScheduler = get(OBSERVE_SCHEDULER)
        )
    }
    single<GetOrderChangesUseCase> {
        GetOrderChangesInteractor(get(), get(), get(), get())
    }
    single {
        BuyXGetYFreePromotionProcessor()
    }
    single {
        BulkyItemsPromotionProcessor()
    }

    single<OrderFactory> {
        OrderFactoryImpl(
            mapOf(
                BulkyItemsPromotionEntity.APP_INTERNAL_ID to BulkyItemsPromotionProcessor(),
                BuyXGetYFreePromotionEntity.APP_INTERNAL_ID to BuyXGetYFreePromotionProcessor()
            )
        )
    }
    single {
        OrderEntityToPresentationMapper(
            get(),
            mapOf(
                BulkyItemsPromotionEntity.APP_INTERNAL_ID to BulkyItemsOrderToPromotionPresentationMapper(
                    get()
                ),
                BuyXGetYFreePromotionEntity.APP_INTERNAL_ID to BuyXGetYOrderToPromotionPresentationMapper(
                    get()
                )
            )
        )
    }
    single<CheckoutOrderUseCase> {
        CheckoutOrderInteractor(get())
    }
}