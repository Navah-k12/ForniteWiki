package com.example.fornitewiki.domain.repository

import com.example.fornitewiki.data.model.NewsItem
import com.example.fornitewiki.data.network.Api

/**
* Project: ForniteWiki
* From: com.example.fornitewiki.domain.repository
* Created by: navah
* On: 20/9/26
* All rights reserved: 2026
 */class NewsRepositoryImpl(
     private val api: Api
 ) : NewsRepository{
    override suspend fun getNews(): List<NewsItem> {
        val response = api.getNews()
        return response.data.br.motds
    }
}