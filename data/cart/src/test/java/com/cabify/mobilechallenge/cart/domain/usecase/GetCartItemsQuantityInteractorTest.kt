package com.cabify.mobilechallenge.cart.domain.usecase

import com.cabify.mobilechallenge.cart.domain.entity.CartEntity
import com.cabify.mobilechallenge.cart.domain.repository.CartRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class GetCartItemsQuantityInteractorTest {

    @Mock
    private lateinit var cartRepository: CartRepository

    private lateinit var getCartItemsQuantityInteractor: GetCartItemsQuantityInteractor

    @Before
    fun setup() {
        setupInteractor()
    }

    private fun setupInteractor() {
        getCartItemsQuantityInteractor = GetCartItemsQuantityInteractor(cartRepository)
    }

    private val cartItem = CartEntity.Item(
        productId = "", quantity = QUANTITY_2
    )

    @Test
    fun `GIVEN there is a cart change WHEN get cart items quantity THEN emit items quantity sum`() {

        whenever(cartRepository.changes()) doReturn Observable.just(
            CartEntity(
                items = listOf(
                    cartItem.copy(quantity = QUANTITY_1),
                    cartItem.copy(quantity = QUANTITY_2)
                )
            )
        )

        getCartItemsQuantityInteractor()
            .test()
            .assertNoErrors()
            .assertValues(QUANTITY_1 + QUANTITY_2)
    }

    companion object {
        private const val QUANTITY_1 = 1
        private const val QUANTITY_2 = 2
    }
}