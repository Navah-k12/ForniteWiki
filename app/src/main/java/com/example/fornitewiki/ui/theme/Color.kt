package com.example.fornitewiki.ui.theme

import androidx.compose.ui.graphics.Color

// Colores por defecto de Compose (necesarios para Theme.kt)
val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

// Colores personalizados de Fortnite
val FortniteLegendario = Color(0xFFFF9800)
val FortniteEpico = Color(0xFFAB47BC)
val FortniteRaro = Color(0xFF29B6F6)
val FortnitePocoComun = Color(0xFF66BB6A)
val FortniteComun = Color(0xFFB0BEC5)
val FortniteCardBg = Color(0xFF1F1E33)
val FortniteSurface = Color(0xFF12111A)

fun getRarityColor(rarityValue: String?): Color {
    return when (rarityValue?.lowercase()) {
        "legendary", "legendario" -> FortniteLegendario
        "epic", "epico" -> FortniteEpico
        "rare", "raro" -> FortniteRaro
        "uncommon", "poco_comun" -> FortnitePocoComun
        else -> FortniteComun
    }
}