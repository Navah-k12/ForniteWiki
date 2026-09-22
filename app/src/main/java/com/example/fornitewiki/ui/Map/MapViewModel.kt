package com.example.fornitewiki.ui.map

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fornitewiki.domain.usecase.GetMapUseCase
import kotlinx.coroutines.launch

data class MapUiState(
    val mapImageUrl: String? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class MapViewModel(
    private val getMapUseCase: GetMapUseCase
) : ViewModel() {

    var uiState by mutableStateOf(MapUiState())
        private set

    init {
        fetchMapData()
    }

    fun fetchMapData() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, errorMessage = null)
            try {
                val mapData = getMapUseCase()
                // Accedemos a la URL dentro del objeto MapImages (puedes usar .pois o .blank)
                val imageUrl = mapData?.images?.pois ?: mapData?.images?.blank

                Log.d("MapDebug", "URL final del mapa obtenida: $imageUrl")

                uiState = uiState.copy(
                    isLoading = false,
                    mapImageUrl = imageUrl
                )
            } catch (e: Exception) {
                Log.e("MapDebug", "Error al cargar el mapa", e)
                uiState = uiState.copy(
                    isLoading = false,
                    errorMessage = e.localizedMessage ?: "Error al cargar el mapa"
                )
            }
        }
    }
}