package com.example.fornitewiki.data.model

import com.google.gson.annotations.SerializedName

/**
 * Project: ForniteWiki
 * From: com.example.fornitewiki.data.model
 * Created by: navah
 * On: 21/9/26
 * All rights reserved: 2026
 */
data class MapResponse(
    @SerializedName("status") val status : Int,
    @SerializedName("data") val data: MapData
)

data class MapData(
    @SerializedName("images") val images: String?,
    @SerializedName("pois") val pois: List<PoiItem>?
)

data class PoiItem(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("locations") val location: List<MapLocation>
)

data class MapLocation(
    @SerializedName("x") val x : Int,
    @SerializedName("y") val y : Int,
    @SerializedName("z") val z : Int
)
