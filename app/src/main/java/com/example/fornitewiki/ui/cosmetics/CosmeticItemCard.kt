package com.example.fornitewiki.ui.cosmetics

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.fornitewiki.data.model.CosmeticItem
import com.example.fornitewiki.ui.components.FortniteRarityBadge
import com.example.fornitewiki.ui.components.fortniteClickable
import com.example.fornitewiki.ui.theme.FortniteCardBg
import com.example.fornitewiki.ui.theme.FortniteDarkBg
import com.example.fornitewiki.ui.theme.getRarityBrush
import com.example.fornitewiki.ui.theme.getRarityColor

@Composable
fun CosmeticItemCard(
    item: CosmeticItem,
    onClick: () -> Unit
) {
    val rarityColor = getRarityColor(item.rarity?.value)
    val rarityText = item.rarity?.displayValue ?: item.rarity?.value ?: "Común"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(235.dp)
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(18.dp),
                ambientColor = rarityColor.copy(alpha = 0.4f),
                spotColor = rarityColor.copy(alpha = 0.8f)
            )
            .clip(RoundedCornerShape(18.dp))
            .border(
                border = BorderStroke(
                    width = 1.5.dp,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            rarityColor,
                            rarityColor.copy(alpha = 0.5f),
                            Color.White.copy(alpha = 0.08f)
                        )
                    )
                ),
                shape = RoundedCornerShape(18.dp)
            )
            .fortniteClickable(
                scaleDown = 0.92f,
                onClick = onClick
            ),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = FortniteCardBg)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Fondo con degradado reactivo al color de la rareza (Estilo Taquilla de Fortnite)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(getRarityBrush(rarityColor))
            )

            // Resplandor de fondo adicional
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(rarityColor.copy(alpha = 0.35f), Color.Transparent),
                            radius = 260f
                        )
                    )
            )

            // Contenido del Cosmético (Icono 3D)
            AsyncImage(
                model = item.images?.icon ?: item.images?.smallIcon,
                contentDescription = item.name,
                modifier = Modifier
                    .size(130.dp)
                    .align(Alignment.Center)
                    .padding(bottom = 35.dp),
                contentScale = ContentScale.Fit
            )

            // Chip superior de Rareza angular
            FortniteRarityBadge(
                rarityText = rarityText,
                rarityColor = rarityColor,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
            )

            // Degradado inferior para legibilidad del texto
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(85.dp)
                    .align(Alignment.BottomCenter)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, FortniteDarkBg.copy(alpha = 0.95f))
                        )
                    )
            )

            // Nombre y Tipo del Ítem
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .padding(horizontal = 12.dp, vertical = 10.dp)
            ) {
                Text(
                    text = item.name ?: "Cosmético",
                    color = Color.White,
                    fontWeight = FontWeight.Black,
                    fontSize = 13.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    letterSpacing = 0.3.sp
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = (item.type?.displayValue ?: "OBJETO").uppercase(),
                    color = rarityColor.copy(alpha = 0.9f),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.8.sp,
                    maxLines = 1
                )
            }
        }
    }
}