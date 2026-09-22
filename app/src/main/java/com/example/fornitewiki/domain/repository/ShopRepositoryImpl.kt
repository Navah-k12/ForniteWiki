package com.example.fornitewiki.domain.repository

import com.example.fornitewiki.data.model.ShopData
import com.example.fornitewiki.data.network.Api

/**
 * Project: ForniteWiki
 * From: com.example.fornitewiki.domain.repository
 * Created by: navah
 * On: 22/9/26
 * All rights reserved: 2026
 */

class ShopRepositoryImpl(
    private val api : Api
): ShopRepository{
    override suspend fun getShop(): ShopData? {
        val response = api.getShop()
        return response.data
    }
}