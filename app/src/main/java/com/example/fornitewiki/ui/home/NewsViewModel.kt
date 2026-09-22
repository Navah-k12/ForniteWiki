package com.example.fornitewiki.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fornitewiki.data.network.RetrofitClient
import com.example.fornitewiki.domain.repository.NewsRepositoryImpl
import com.example.fornitewiki.domain.usecase.GetNewsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Project: ForniteWiki
 * From: com.example.fornitewiki.ui.home
 * Created by: navah
 * On: 19/9/26
 * All rights reserved: 2026
 */

class NewsViewModel : ViewModel(){
    private val repository = NewsRepositoryImpl(RetrofitClient.apiService)
    private val getNewsUseCase = GetNewsUseCase(repository)

    private val _uiState = MutableStateFlow<NewsUiState>(NewsUiState.Loading)
    val uiState: StateFlow<NewsUiState> = _uiState.asStateFlow()

    init {
        fetchNews()
    }

    fun fetchNews(){
        viewModelScope.launch {
            _uiState.value = NewsUiState.Loading
            try {
                val newsList = getNewsUseCase()
                _uiState.value = NewsUiState.Success(newsList)
            }catch (e: Exception){
                _uiState.value = NewsUiState.Error(message = e.message ?: "Error al cargar las noticias")
            }
        }
    }
}