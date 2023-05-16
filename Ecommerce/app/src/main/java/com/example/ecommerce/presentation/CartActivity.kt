package com.example.ecommerce.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.ecommerce.databinding.ActivityCartBinding
import com.example.ecommerce.domain.models.CartItem
import com.example.ecommerce.domain.usecases.CalculateTotalUseCase
import com.example.ecommerce.domain.usecases.DecrementItemUseCase
import com.example.ecommerce.domain.usecases.DecrementUseCase
import com.example.ecommerce.domain.usecases.IncrementItemUseCase
import com.example.ecommerce.domain.usecases.IncrementUseCase
import com.example.ecommerce.presentation.adapters.CartAdapter
import com.example.ecommerce.presentation.viewmodels.CartViewModel
import com.example.ecommerce.util.asCurrency

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private val viewModel: CartViewModel by viewModels {
        CartViewModel.Factory(
            incrementUseCase = IncrementItemUseCase(),
            decrementUseCase = DecrementItemUseCase(),
            calculateTotalUseCase = CalculateTotalUseCase()
        )
    }
    private val cartAdapter = CartAdapter(
        increment = this::increment,
        decrement = this::decrement
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
        fetchCartItems()
        bindObservers()
    }

    private fun setupViews() {
        setupRecyclerView()
        setupTotal()
    }

    private fun setupRecyclerView() {
        binding.productsList.apply {
            setHasFixedSize(true)
            adapter = cartAdapter

            val itemDecoration = DividerItemDecoration(this@CartActivity, DividerItemDecoration.VERTICAL)
            addItemDecoration(itemDecoration)
        }
    }

    private fun setupTotal() {
        binding.totalValue.text = ZERO.asCurrency()
    }

    private fun fetchCartItems() {
        viewModel.fetchCartItems()
    }

    private fun bindObservers() {
        viewModel.items.observe(this) { cartItems ->
            cartAdapter.submitList(cartItems)
        }

        viewModel.total.observe(this) { total ->
            binding.totalValue.text = total.asCurrency()
        }
    }

    private fun increment(cartItem: CartItem) {
        viewModel.increment(cartItem)
    }

    private fun decrement(cartItem: CartItem) {
        viewModel.decrement(cartItem)
    }

    companion object {
        private const val ZERO = 0.0
    }
}