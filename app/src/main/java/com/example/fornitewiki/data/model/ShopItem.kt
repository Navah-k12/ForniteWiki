package com.example.fornitewiki.data.model

import com.google.gson.annotations.SerializedName

/**
 * Project: ForniteWiki
 * From: com.example.fornitewiki.data.model
 * Created by: navah
 * On: 21/9/26
 * All rights reserved: 2026
 */

data class ShopResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("data") val data: ShopData?
)

data class ShopData(
    @SerializedName("hash") val hash: String?,
    @SerializedName("date") val date: String?,
    @SerializedName("vbuckIcon") val vbuckIcon: String?,
    @SerializedName("entries") val entries: List<ShopEntry>?
)


data class ShopEntry(
    @SerializedName("regularPrice") val regularPrice: Int?,
    @SerializedName("finalPrice") val finalPrice: Int?,
    @SerializedName("isBundle") val isBundle: Boolean?,
    @SerializedName("isGiftable") val isGiftable: Boolean?,
    @SerializedName("banner") val banner: ShopBanner?,
    @SerializedName("bundle") val bundle: ShopBundle?,
    @SerializedName("items") val items: List<CosmeticItem>?
)


data class ShopBanner(
    @SerializedName("value") val value: String?,
    @SerializedName("intensity") val intensity: String?,
    @SerializedName("backendValue") val backendValue: String?
)


data class ShopBundle(
    @SerializedName("name") val name: String?,
    @SerializedName("info") val info: String?,
    @SerializedName("image") val image: String?
)



