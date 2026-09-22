package com.example.fornitewiki.domain.usecase

import com.example.fornitewiki.data.model.MapData
import com.example.fornitewiki.domain.repository.MapRepository

/**
 * Project: ForniteWiki
 * From: com.example.fornitewiki.domain.usecase
 * Created by: navah
 * On: 21/9/26
 * All rights reserved: 2026
 */

class GetMapUseCase(
    private val repository: MapRepository
){
    suspend operator fun invoke(): MapData?{
        return repository.getMap()
    }
}