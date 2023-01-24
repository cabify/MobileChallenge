package com.cabify.mobilechallenge.features.home.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cabify.mobilechallenge.core.base.presentation.BaseViewModel
import com.cabify.shared.product.domain.usecase.GetProductsUseCase
import com.cabify.shared.product.domain.usecase.GetPromotionsUseCase
import io.reactivex.rxjava3.core.Scheduler

class HomeViewModel(
    private val getPromotionsUseCase: GetPromotionsUseCase,
    private val getProductsUseCase: GetProductsUseCase,
    private val subscribeScheduler: Scheduler,
    private val observerScheduler: Scheduler
) : BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    init {

        //TODO remove testing purposes
        addToDisposable(
            getProductsUseCase()
                .subscribeOn(subscribeScheduler)
                .observeOn(observerScheduler)
                .subscribe({
                    it
                }, {
                    it
                })
        )

        addToDisposable(
            getPromotionsUseCase()
                .subscribeOn(subscribeScheduler)
                .observeOn(observerScheduler)
                .subscribe({
                    it
                }, {
                    it
                })
        )
    }
}