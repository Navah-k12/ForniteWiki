package com.example.fornitewiki.domain.repository

import com.example.fornitewiki.data.model.ShopData

/**
 * Project: ForniteWiki
 * From: com.example.fornitewiki.domain.repository
 * Created by: navah
 * On: 22/9/26
 * All rights reserved: 2026
 */
interface ShopRepository {
    suspend fun getShop(): ShopData?
}