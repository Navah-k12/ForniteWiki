package com.example.fornitewiki.ui.cosmetics

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fornitewiki.domain.usecase.GetCosmeticsUseCase
import kotlinx.coroutines.launch

class CosmeticsViewModel(
    private val getCosmeticsUseCase: GetCosmeticsUseCase
) : ViewModel() {

    var uiState by mutableStateOf(CosmeticsUiState())
        private set

    init {
        fetchCosmetics()
    }

    fun fetchCosmetics() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            try {
                val cosmeticsList = getCosmeticsUseCase()
                uiState = uiState.copy(
                    isLoading = false,
                    items = cosmeticsList
                )
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    error = e.localizedMessage ?: "Error desconocido"
                )
            }
        }
    }
}