package com.cabify.challenge.infrastructure.factory

import com.cabify.challenge.core.domain.promos.MugNoPromoRule
import com.cabify.challenge.core.domain.promos.TShirt3OrMoreDiscountRule
import com.cabify.challenge.core.domain.promos.Voucher2x1Rule
import com.cabify.challenge.core.domain.services.PromosService
import com.cabify.challenge.core.domain.services.RulePromosService

object ServiceFactory {
    private val rules = listOf(
        Voucher2x1Rule(), TShirt3OrMoreDiscountRule(), MugNoPromoRule()
    )
    val promosService: PromosService by lazy { RulePromosService(rules) }
}