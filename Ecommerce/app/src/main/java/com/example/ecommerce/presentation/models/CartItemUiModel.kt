package com.example.ecommerce.presentation.models

import com.example.ecommerce.domain.models.CartItem
import com.example.ecommerce.util.asCurrency

class CartItemUiModel(item: CartItem) {
    val name = item.name
    val price = item.price.asCurrency()
    val quantity = item.quantity.toString()
}