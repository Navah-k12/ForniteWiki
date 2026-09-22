package com.example.fornitewiki.ui.cosmetics

import com.example.fornitewiki.data.model.CosmeticItem

data class CosmeticsUiState(
    val isLoading: Boolean = false,
    val items: List<CosmeticItem> = emptyList(),
    val error: String? = null
)