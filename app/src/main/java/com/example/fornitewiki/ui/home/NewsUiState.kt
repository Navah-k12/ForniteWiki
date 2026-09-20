package com.example.fornitewiki.ui.home

import android.os.Message
import com.example.fornitewiki.data.model.NewsItem

/**
 * Project: ForniteWiki
 * From: com.example.fornitewiki.ui.home
 * Created by: navah
 * On: 19/9/26
 * All rights reserved: 2026
 */

// COmporbación de error
sealed interface NewsUiState{
    object Loading : NewsUiState
    data class Success(val news: List<NewsItem>) : NewsUiState
    data class Error(val message: String): NewsUiState
}