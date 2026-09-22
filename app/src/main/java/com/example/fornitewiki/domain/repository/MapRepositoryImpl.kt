package com.example.fornitewiki.domain.repository

import android.util.Log
import com.example.fornitewiki.data.model.MapData
import com.example.fornitewiki.data.model.PoiItem
import com.example.fornitewiki.data.network.Api

class MapRepositoryImpl(
    private val api: Api
) : MapRepository {
    override suspend fun getMap(): MapData {
        val response = api.getMap()
        Log.d("MapRepository", "Respuesta completa de la API del mapa: $response")

        // Si response.data es nulo, intentamos retornar un objeto seguro o mapearlo directamente
        return response.data ?: throw IllegalStateException("La propiedad 'data' del mapa llegó nula desde la API")
    }
}