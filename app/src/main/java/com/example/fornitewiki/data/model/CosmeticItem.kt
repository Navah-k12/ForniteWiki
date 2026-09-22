package com.example.fornitewiki.data.model

import com.google.gson.annotations.SerializedName


/**
 * Project: ForniteWiki
 * From: com.example.fornitewiki.data.model
 * Created by: navah
 * On: 20/9/26
 * All rights reserved: 2026
 */

data class CosmeticResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("data") val data: List<CosmeticItem>
)


data class CosmeticItem(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String?,
    @SerializedName("type") val type: CosmeticsType?,
    @SerializedName("rarity") val rarity: Rarity?,
    @SerializedName("series") val series: Series?,
    @SerializedName("set") val set: CosmeticsSet?,
    @SerializedName("introduction") val introduction: Introduction?,
    @SerializedName("image") val image: CosmeticsImage?,
    @SerializedName("variants") val variants: List<Variants>?,
    @SerializedName("searchTags") val search: List<String>?,
    @SerializedName("metaTag") val metaTag: List<String>?
)

data class CosmeticsType(
    @SerializedName("value") val value: String,
    @SerializedName("displayValue") val displayValue: String,
    @SerializedName("backendValue") val backendValue: String
)

data class Rarity(
    @SerializedName("value") val value : String,
    @SerializedName("displayValue") val displayValue: String,
    @SerializedName("backendValue") val backendValue: String
)

data class Series(
    @SerializedName("value") val values: String,
    @SerializedName("colors") val colors: List<String>?,
    @SerializedName("backendValue") val backendValue: String
)

data class CosmeticsSet(
   @SerializedName("value") val value: String,
   @SerializedName("text") val text: String,
   @SerializedName("backendValue") val backendValue: String
)

data class Introduction(
    @SerializedName("chapter") val chapter: String,
    @SerializedName("text") val text: String,
    @SerializedName("backendValue") val backendValue: String
)

data class CosmeticsImage(
    @SerializedName("smallIcon") val smalIcon: String,
    @SerializedName("icon") val icon: String,
    @SerializedName("featured") val featured: String
)

data class Variants(
    @SerializedName("channel") val channel: String,
    @SerializedName("type") val type: String,
    @SerializedName("options") val options: List<VariantsOptions>
)

data class VariantsOptions(
    @SerializedName("tag") val tag: String,
    @SerializedName("name") val name: String,
    @SerializedName("image") val image: String
)
