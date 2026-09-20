package com.example.fornitewiki.data.network

import com.example.fornitewiki.data.model.NewsItem
import com.example.fornitewiki.data.model.NewsResponse
import org.intellij.lang.annotations.Language
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Project: ForniteWiki
 * From: com.example.fornitewiki.data.network
 * Created by: navah
 * On: 19/9/26
 * All rights reserved: 2026
 */

interface Api{
    @GET("v2/news")
    suspend fun getNews(): NewsResponse
}


