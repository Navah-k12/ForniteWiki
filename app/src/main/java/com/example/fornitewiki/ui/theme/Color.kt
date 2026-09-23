package com.example.fornitewiki.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Colores por defecto de Compose
val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

// --- PALETA BATTLE ROYALE FORTNITE ---
val FortniteDarkBg = Color(0xFF080B12)
val FortniteSurface = Color(0xFF0F1422)
val FortniteCardBg = Color(0xFF161C2E)
val FortniteCardBorder = Color(0x33FFFFFF)
val FortniteCyan = Color(0xFF00F0FF)
val FortniteYellow = Color(0xFFFFD700)
val FortniteGold = Color(0xFFFFA000)
val FortniteNeonPink = Color(0xFFFF007F)

// Rarezas oficiales de Fortnite
val FortniteMico = Color(0xFFFFD700)        // Mítico / Exótico
val FortniteLegendario = Color(0xFFFF7605)   // Legendario
val FortniteEpico = Color(0xFFB534E8)        // Épico
val FortniteRaro = Color(0xFF00B0FF)         // Raro
val FortnitePocoComun = Color(0xFF43A047)    // Poco Común
val FortniteComun = Color(0xFF78909C)        // Común
val FortniteMarvel = Color(0xFFD32F2F)       // Serie Marvel
val FortniteDC = Color(0xFF1565C0)           // Serie DC
val FortniteIcon = Color(0xFF00E5FF)         // Serie de Ídolos

fun getRarityColor(rarityValue: String?): Color {
    val r = rarityValue?.lowercase() ?: ""
    return when {
        r.contains("mythic") || r.contains("mitico") || r.contains("exotic") -> FortniteMico
        r.contains("legendary") || r.contains("legendario") -> FortniteLegendario
        r.contains("epic") || r.contains("epico") -> FortniteEpico
        r.contains("rare") || r.contains("raro") -> FortniteRaro
        r.contains("uncommon") || r.contains("poco_comun") || r.contains("poco común") -> FortnitePocoComun
        r.contains("marvel") -> FortniteMarvel
        r.contains("dc") -> FortniteDC
        r.contains("icon") || r.contains("idolo") -> FortniteIcon
        else -> FortniteComun
    }
}

fun getRarityBrush(rarityColor: Color): Brush {
    return Brush.verticalGradient(
        colors = listOf(
            rarityColor.copy(alpha = 0.45f),
            rarityColor.copy(alpha = 0.15f),
            FortniteCardBg.copy(alpha = 0.95f)
        )
    )
}