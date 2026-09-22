package com.example.fornitewiki.ui.shop

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fornitewiki.domain.repository.ShopRepository
import kotlinx.coroutines.launch

class ShopViewModel(
    private val shopRepository: ShopRepository
) : ViewModel() {

    var uiState by mutableStateOf(ShopUiState())
        private set

    init {
        fetchShopData()
    }

    fun fetchShopData() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, errorMessage = null)
            try {
                val shopData = shopRepository.getShop()
                val entries = shopData?.entries ?: emptyList()

                uiState = uiState.copy(
                    isLoading = false,
                    shopEntries = entries
                )
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    errorMessage = e.localizedMessage ?: "Error al conectar con la API"
                )
            }
        }
    }
}