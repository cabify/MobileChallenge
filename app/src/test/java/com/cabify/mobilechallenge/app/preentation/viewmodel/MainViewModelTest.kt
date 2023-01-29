package com.cabify.mobilechallenge.app.preentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.cabify.mobilechallenge.app.presentation.viewmodel.MainViewModel
import com.cabify.mobilechallenge.cart.domain.usecase.GetCartItemsQuantityUseCase
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var mainViewModel: MainViewModel

    @Mock
    private lateinit var getCartItemsQuantityUseCase: GetCartItemsQuantityUseCase

    @Mock
    private lateinit var cartItemsQuantityObserver: Observer<Int>
    private val testScheduler: Scheduler = Schedulers.trampoline()
    private lateinit var behaviorSubject: BehaviorSubject<Int>

    @Before
    fun setup() {
        setupSubject()
        setupViewModel()
        setupObservers()
    }

    private fun setupSubject() {
        behaviorSubject = BehaviorSubject.create()
        whenever(getCartItemsQuantityUseCase()) doReturn behaviorSubject
    }

    private fun setupObservers() {
        mainViewModel.cartItemsQuantity.observeForever(cartItemsQuantityObserver)
    }

    private fun setupViewModel() {
        mainViewModel = MainViewModel(
            getCartItemsQuantityUseCase,
            testScheduler,
            testScheduler
        )
    }

    @Test
    fun `GIVEN getCartItemsQuantity emission succeed WHEN there was a change in the cart THEN set CartItemsQuantityState`() {
        whenQuantityEmission(QUANTITY_1)
        thenSetCartItemsQuantityStates(QUANTITY_1)
    }

    @Test
    fun `GIVEN getCartItemsQuantity emission failed WHEN there was a change in the cart THEN set CartItemsQuantityState as zero`() {
        whenQuantityFail()
        thenSetCartItemsQuantityStates(QUANTITY_0)
    }


    @Test
    fun `GIVEN getCartItemsQuantity emitted two equals quantities WHEN there were changes in the cart THEN set CartItemsQuantityState once`() {
        whenQuantityEmission(QUANTITY_1)
        whenQuantityEmission(QUANTITY_1)
        thenSetCartItemsQuantityStates(QUANTITY_1)
    }

    @Test
    fun `GIVEN getCartItemsQuantity emitted two different quantities WHEN there were changes in the cart THEN set CartItemsQuantityState twice`() {
        whenQuantityEmission(QUANTITY_1)
        whenQuantityEmission(QUANTITY_2)
        thenSetCartItemsQuantityStates(QUANTITY_1, QUANTITY_2)
    }

    private fun thenSetCartItemsQuantityStates(vararg quantities: Int) {
        argumentCaptor {
            verify(cartItemsQuantityObserver, times(quantities.size)).onChanged(capture())
            Assert.assertArrayEquals(quantities.toTypedArray(), allValues.toTypedArray())
        }
    }

    private fun whenQuantityEmission(quantity1: Int) {
        behaviorSubject.onNext(quantity1)
    }

    private fun whenQuantityFail() {
        behaviorSubject.onError(Throwable())
    }


    companion object {
        private const val QUANTITY_0 = 0
        private const val QUANTITY_1 = 1
        private const val QUANTITY_2 = 2
    }
}