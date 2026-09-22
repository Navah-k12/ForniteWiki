package com.example.fornitewiki.domain.usecase

import com.example.fornitewiki.data.model.CosmeticItem
import com.example.fornitewiki.domain.repository.CosmeticsRepository

/**
 * Project: ForniteWiki
 * From: com.example.fornitewiki.domain.usecase
 * Created by: navah
 * On: 21/9/26
 * All rights reserved: 2026
 */
class GetCosmeticsUseCase (
    private val repository: CosmeticsRepository
){
    suspend operator fun invoke(): List<CosmeticItem>{
        return repository.getCosmetics()
    }
}