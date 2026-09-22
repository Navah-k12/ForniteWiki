package com.example.fornitewiki.ui.shop

import com.example.fornitewiki.data.model.ShopEntry

data class ShopUiState(
    val isLoading: Boolean = false,
    val shopEntries: List<ShopEntry> = emptyList(),
    val errorMessage: String? = null
)