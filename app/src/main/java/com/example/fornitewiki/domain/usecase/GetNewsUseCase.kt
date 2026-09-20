package com.example.fornitewiki.domain.usecase

import com.example.fornitewiki.data.model.NewsItem
import com.example.fornitewiki.domain.repository.NewsRepository

/**
 * Project: ForniteWiki
 * From: com.example.fornitewiki.domain.usecase
 * Created by: navah
 * On: 20/9/26
 * All rights reserved: 2026
 */
class GetNewsUseCase(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(): List<NewsItem>{
        return repository.getNews()
    }
}