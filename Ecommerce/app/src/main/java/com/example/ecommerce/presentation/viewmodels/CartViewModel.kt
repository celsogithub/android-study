package com.example.ecommerce.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.ecommerce.domain.models.CartItem
import com.example.ecommerce.domain.usecases.CalculateTotalUseCase
import com.example.ecommerce.domain.usecases.DecrementItemUseCase
import com.example.ecommerce.domain.usecases.DecrementUseCase
import com.example.ecommerce.domain.usecases.IncrementItemUseCase
import com.example.ecommerce.domain.usecases.IncrementUseCase
import com.example.ecommerce.util.update

class CartViewModel(
    private val incrementUseCase: IncrementItemUseCase,
    private val decrementUseCase: DecrementItemUseCase,
    private val calculateTotalUseCase: CalculateTotalUseCase
) : ViewModel() {

    private val _items = MutableLiveData<List<CartItem>>()
    val items: LiveData<List<CartItem>>
        get() = _items

    val total: LiveData<Double> = _items.map { items -> calculateTotalUseCase(items) }

    fun fetchCartItems() {
        val cartItems = listOf(
            CartItem(
                id = 1,
                name = "Tênis",
                price = 400.0,
                quantity = 1,
            ),
            CartItem(
                id = 2,
                name = "Camiseta",
                price = 100.0,
                quantity = 1,
            )
        )

        _items.postValue(cartItems)
    }

    fun increment(cartItem: CartItem) {
//        val incrementedCartItem = incrementUseCase(cartItem)
//        updateItem(incrementedCartItem)

        val cartItems = _items.value ?: emptyList()
        val updatedItems = incrementUseCase(cartItems, cartItem)
        _items.postValue(updatedItems)
    }

    fun decrement(cartItem: CartItem) {
//        val decrementedItem = decrementUseCase(cartItem)
//        updateItem(decrementedItem)

        val cartItems = _items.value ?: emptyList()
        val updatedItems = decrementUseCase(cartItems, cartItem)
        _items.postValue(updatedItems)
    }

    private fun updateItem(cartItem: CartItem) {
        val cartItems = _items.value
        val index = cartItems?.indexOfFirst { it.id == cartItem.id } ?: INDEX_NOT_FOUND
        val updatedCartItems = cartItems?.update(index, cartItem)

        _items.postValue(updatedCartItems)
    }

    companion object {
        private const val INDEX_NOT_FOUND = -1
    }

    class Factory(
        private val incrementUseCase: IncrementItemUseCase,
        private val decrementUseCase: DecrementItemUseCase,
        private val calculateTotalUseCase: CalculateTotalUseCase
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
            return CartViewModel(incrementUseCase, decrementUseCase, calculateTotalUseCase) as T
        }
    }
}