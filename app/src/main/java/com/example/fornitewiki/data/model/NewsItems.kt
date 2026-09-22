package com.example.fornitewiki.data.model

/**
 * Project: ForniteWiki
 * From: com.example.fornitewiki.data.model
 * Created by: navah
 * On: 19/9/26
 * All rights reserved: 2026
 */

data class NewsItem (
    val id: String,
    val title: String,
    val tabTitle: String?=null,
    val body: String?=null,
    val image: String,
    val tileImage: String?=null,
    val sortingPriority: Int,
    val hidden: Boolean
)

data class NewsResponse(
    val status: Int,
    val data: NewsData
)

data class NewsData(
    val br: NewsBr
)

data class  NewsBr(
    val hash: String,
    val date: String,
    val image: String,
    val motds: List<NewsItem>
)
