package com.example.ecommerce.domain.usecases

import com.example.ecommerce.domain.models.CartItem

class CalculateTotalUseCase {
    operator fun invoke(items: List<CartItem>): Double {
        if (items.isEmpty()) return ZERO

        return items
            .map { it.price * it.quantity }
            .toList()
            .reduce { sum, item -> sum + item }
    }

    companion object {
        private const val ZERO = 0.0
    }
}