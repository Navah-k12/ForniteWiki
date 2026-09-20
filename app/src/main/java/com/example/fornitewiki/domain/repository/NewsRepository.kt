package com.example.fornitewiki.domain.repository

import com.example.fornitewiki.data.model.NewsItem

/**
 * Project: ForniteWiki
 * From: com.example.fornitewiki.domain.repository
 * Created by: navah
 * On: 20/9/26
 * All rights reserved: 2026
 */
interface NewsRepository {
    suspend fun getNews(): List<NewsItem>
}