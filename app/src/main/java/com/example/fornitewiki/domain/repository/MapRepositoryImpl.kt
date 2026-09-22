package com.example.fornitewiki.domain.repository

import com.example.fornitewiki.data.model.MapData
import com.example.fornitewiki.data.model.PoiItem
import com.example.fornitewiki.data.network.Api

/**
 * Project: ForniteWiki
 * From: com.example.fornitewiki.domain.repository
 * Created by: navah
 * On: 21/9/26
 * All rights reserved: 2026
 */
class MapRepositoryImpl (
    private val api: Api
): MapRepository{
    override suspend fun getMap(): MapData{
        val response = api.getMap()
        return response.data
    }
}