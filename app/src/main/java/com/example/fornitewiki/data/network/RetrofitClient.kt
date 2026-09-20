package com.example.fornitewiki.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

/**
 * Project: ForniteWiki
 * From: com.example.fornitewiki.data.network
 * Created by: navah
 * On: 19/9/26
 * All rights reserved: 2026
 */

object RetrofitClient {
    private const val BD_URL = "https://fortnite-api.com/"

    val apiService: Api by lazy {
        Retrofit.Builder()
            .baseUrl(BD_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }
}