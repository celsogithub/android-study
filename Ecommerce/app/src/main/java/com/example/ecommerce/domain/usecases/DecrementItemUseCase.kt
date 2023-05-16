package com.example.ecommerce.domain.usecases

import com.example.ecommerce.domain.models.CartItem
import com.example.ecommerce.util.update

class DecrementItemUseCase {

    operator fun invoke(items: List<CartItem>, item: CartItem): List<CartItem> {
        if (items.isEmpty()) return items

        val quantity = if (item.quantity > 0) item.quantity - 1 else 0
        val decrementedItem = item.copy(quantity = quantity)

        val index = items.indexOfFirst { item.id == it.id }
        return if (index > INDEX_NOT_FOUND) {
            items.update(index, decrementedItem)
        } else {
            items
        }
    }

    companion object {
        private const val INDEX_NOT_FOUND = -1
    }
}