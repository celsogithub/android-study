package com.example.ecommerce.util

import java.text.NumberFormat
import java.util.Locale

fun Double.asCurrency(): String {
    val locale = Locale("pt", "BR")
    val numberFormat = NumberFormat.getCurrencyInstance(locale).apply {
        maximumFractionDigits = 2
        minimumFractionDigits = 2
    }
    return numberFormat.format(this)
}

fun <T> List<T>.update(index: Int, item: T): List<T> = toMutableList().apply { this[index] = item }