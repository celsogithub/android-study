package com.example.ecommerce.domain.usecases

import com.example.ecommerce.domain.models.CartItem

class DecrementUseCase {
    operator fun invoke(cartItem: CartItem): CartItem {
        return cartItem.copy(quantity = cartItem.quantity - 1)
    }
}