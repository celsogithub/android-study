package com.example.ecommerce.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.domain.models.CartItem
import com.example.ecommerce.databinding.ListItemCartBinding
import com.example.ecommerce.presentation.models.CartItemUiModel

class CartAdapter(
    private val increment: (CartItem) -> Unit,
    private val decrement: (CartItem) -> Unit
) : ListAdapter<CartItem, CartAdapter.CartItemViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemCartBinding.inflate(layoutInflater, parent, false)
        return CartItemViewHolder(
            binding = binding,
            decrement = decrement,
            increment = increment,
        )
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<CartItem>() {
        override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem): Boolean =
            (oldItem.name == newItem.name) &&
                    (oldItem.price == newItem.price) &&
                    (oldItem.quantity == newItem.quantity)
    }

    inner class CartItemViewHolder(
        private val binding: ListItemCartBinding,
        private val increment: (CartItem) -> Unit,
        private val decrement: (CartItem) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(cartItem: CartItem) = with(binding) {
            uiModel = CartItemUiModel(cartItem)

            incrementButton.setOnClickListener {
                increment(cartItem)
            }

            decrementButton.setOnClickListener {
                decrement(cartItem)
            }

            executePendingBindings()
        }
    }
}