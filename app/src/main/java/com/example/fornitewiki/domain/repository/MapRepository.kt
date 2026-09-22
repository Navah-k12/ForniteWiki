package com.example.fornitewiki.domain.repository

import com.example.fornitewiki.data.model.MapData
import com.example.fornitewiki.data.model.PoiItem

/**
 * Project: ForniteWiki
 * From: com.example.fornitewiki.domain.repository
 * Created by: navah
 * On: 21/9/26
 * All rights reserved: 2026
 */
interface MapRepository {
    suspend fun getMap(): MapData?

}