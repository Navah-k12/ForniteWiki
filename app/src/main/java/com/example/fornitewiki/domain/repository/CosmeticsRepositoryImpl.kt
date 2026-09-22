package com.example.fornitewiki.domain.repository

import com.example.fornitewiki.data.model.CosmeticItem
import com.example.fornitewiki.data.network.Api

/**
 * Project: ForniteWiki
 * From: com.example.fornitewiki.domain.repository
 * Created by: navah
 * On: 21/9/26
 * All rights reserved: 2026
 */
class CosmeticsRepositoryImpl(
    private val api: Api
): CosmeticsRepository{
    override suspend fun getCosmetics(): List<CosmeticItem>{
        val response = api.getCosmetics()
        return response.data.br
    }
}