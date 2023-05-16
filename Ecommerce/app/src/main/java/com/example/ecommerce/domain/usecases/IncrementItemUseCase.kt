package com.example.ecommerce.domain.usecases

import com.example.ecommerce.domain.models.CartItem
import com.example.ecommerce.util.update

class IncrementItemUseCase {

    operator fun invoke(items: List<CartItem>, item: CartItem): List<CartItem> {
        if (items.isEmpty()) return items

        val incrementedItem = item.copy(quantity = item.quantity + 1)

        val index = items.indexOfFirst { item.id == it.id }
        return if (index > INDEX_NOT_FOUND) {
            items.update(index, incrementedItem)
        } else {
            items
        }
    }

    companion object {
        private const val INDEX_NOT_FOUND = -1
    }
}