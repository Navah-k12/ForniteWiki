package com.example.fornitewiki.ui.cosmetics

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.fornitewiki.data.model.CosmeticItem
import com.example.fornitewiki.ui.components.FortniteRarityBadge
import com.example.fornitewiki.ui.components.fortniteClickable
import com.example.fornitewiki.ui.theme.*

@Composable
fun CosmeticDetailScreen(
    item: CosmeticItem,
    onBackClick: () -> Unit
) {
    val rarityColor = getRarityColor(item.rarity?.value)
    val rarityText = item.rarity?.displayValue ?: item.rarity?.value ?: "Común"
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FortniteDarkBg)
            .statusBarsPadding()
            .verticalScroll(scrollState)
            .padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Marco de héroe 3D con aura reactiva a la rareza
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
                .shadow(
                    elevation = 20.dp,
                    shape = RoundedCornerShape(26.dp),
                    ambientColor = rarityColor.copy(alpha = 0.5f),
                    spotColor = rarityColor
                )
                .clip(RoundedCornerShape(26.dp))
                .border(
                    width = 2.dp,
                    brush = Brush.verticalGradient(
                        colors = listOf(rarityColor, rarityColor.copy(alpha = 0.3f), Color.White.copy(alpha = 0.1f))
                    ),
                    shape = RoundedCornerShape(26.dp)
                ),
            color = FortniteCardBg
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                // Aura de fondo circular radiante
                Box(
                    modifier = Modifier
                        .size(240.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(rarityColor.copy(alpha = 0.45f), Color.Transparent),
                                radius = 320f
                            )
                        )
                )

                // Imagen de Skin / Cosmético
                AsyncImage(
                    model = item.images?.icon ?: item.images?.smallIcon,
                    contentDescription = item.name,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentScale = ContentScale.Fit
                )

                // Badge de rareza en esquina superior
                FortniteRarityBadge(
                    rarityText = rarityText,
                    rarityColor = rarityColor,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(14.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Nombre del Objeto en tipografía agresiva Fortnite
        Text(
            text = (item.name ?: "Cosmético Desconocido").uppercase(),
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 0.8.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(6.dp))

        // Subtítulo de tipo
        Text(
            text = (item.type?.displayValue ?: item.type?.value ?: "OBJETO").uppercase(),
            color = rarityColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 1.4.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Tarjeta de Descripción estilo Locker
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(16.dp)),
            color = FortniteCardBg.copy(alpha = 0.7f),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "DESCRIPCIÓN DEL OBJETO",
                    color = FortniteCyan,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.2.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = item.description?.ifBlank { "Sin descripción oficial disponible en los archivos de la isla." }
                        ?: "Sin descripción oficial disponible en los archivos de la isla.",
                    color = Color.LightGray,
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(26.dp))

        // Botón interactivo animado de Regresar
        Surface(
            modifier = Modifier
                .width(200.dp)
                .height(48.dp)
                .shadow(12.dp, RoundedCornerShape(14.dp))
                .clip(RoundedCornerShape(14.dp))
                .border(1.dp, rarityColor.copy(alpha = 0.8f), RoundedCornerShape(14.dp))
                .fortniteClickable(scaleDown = 0.90f, onClick = onBackClick),
            color = rarityColor
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "◀ REGRESAR",
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}