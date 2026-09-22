package com.example.fornitewiki.ui.shop

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.fornitewiki.data.model.ShopEntry
import com.example.fornitewiki.ui.components.FortniteHUDHeader
import com.example.fornitewiki.ui.components.FortniteRarityBadge
import com.example.fornitewiki.ui.components.fortniteClickable
import com.example.fornitewiki.ui.theme.*

@Composable
fun ShopScreen(viewModel: ShopViewModel) {
    val state = viewModel.uiState
    var selectedEntry by remember { mutableStateOf<ShopEntry?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FortniteDarkBg)
    ) {
        if (selectedEntry != null) {
            ShopDetailContent(
                entry = selectedEntry!!,
                onBack = { selectedEntry = null }
            )
        } else {
            Column(modifier = Modifier.fillMaxSize()) {
                // Encabezado HUD oficial de la Tienda
                FortniteHUDHeader(
                    tag = "TIENDA DE OBJETOS",
                    title = "Rotación Diaria",
                    subtitle = "Objetos, lotes y cosméticos destacados",
                    rightBadge = "🪙 V-BUCKS",
                    badgeColor = FortniteYellow
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 14.dp),
                    contentAlignment = Alignment.Center
                ) {
                    when {
                        state.isLoading -> {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                CircularProgressIndicator(
                                    color = FortniteYellow,
                                    strokeWidth = 3.dp
                                )
                                Spacer(modifier = Modifier.height(14.dp))
                                Text(
                                    text = "ACTUALIZANDO TIENDA...",
                                    color = Color.White,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Black,
                                    letterSpacing = 1.2.sp
                                )
                            }
                        }
                        state.errorMessage != null -> {
                            Surface(
                                color = FortniteCardBg,
                                shape = RoundedCornerShape(18.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(1.dp, Color.Red.copy(alpha = 0.5f), RoundedCornerShape(18.dp))
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.padding(24.dp)
                                ) {
                                    Text(
                                        text = "⚠️ ERROR EN LA TIENDA",
                                        color = Color.Red,
                                        fontWeight = FontWeight.Black,
                                        fontSize = 15.sp
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = state.errorMessage,
                                        color = Color.LightGray,
                                        fontSize = 12.sp,
                                        textAlign = TextAlign.Center
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Surface(
                                        color = FortniteCyan,
                                        shape = RoundedCornerShape(12.dp),
                                        modifier = Modifier
                                            .fortniteClickable { viewModel.fetchShopData() }
                                            .padding(horizontal = 16.dp, vertical = 8.dp)
                                    ) {
                                        Text(
                                            "REINTENTAR",
                                            color = Color.Black,
                                            fontWeight = FontWeight.Black,
                                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                                        )
                                    }
                                }
                            }
                        }
                        state.shopEntries.isEmpty() -> {
                            Text(text = "No hay objetos disponibles en este momento.", color = Color.Gray, fontSize = 14.sp)
                        }
                        else -> {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                modifier = Modifier.fillMaxSize(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp),
                                contentPadding = PaddingValues(top = 4.dp, bottom = 110.dp)
                            ) {
                                items(state.shopEntries) { entry ->
                                    ShopEntryCard(
                                        entry = entry,
                                        onClick = { selectedEntry = entry }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ShopEntryCard(entry: ShopEntry, onClick: () -> Unit) {
    val itemName = getEntryName(entry)
    val imageUrl = getEntryImage(entry)
    val rarityText = getEntryRarity(entry)
    val rarityColor = getRarityColor(rarityText)
    val price = entry.finalPrice ?: entry.regularPrice ?: 0

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(245.dp)
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(18.dp),
                ambientColor = rarityColor.copy(alpha = 0.35f),
                spotColor = rarityColor.copy(alpha = 0.7f)
            )
            .clip(RoundedCornerShape(18.dp))
            .border(
                border = BorderStroke(
                    width = 1.5.dp,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            rarityColor,
                            rarityColor.copy(alpha = 0.4f),
                            Color.White.copy(alpha = 0.08f)
                        )
                    )
                ),
                shape = RoundedCornerShape(18.dp)
            )
            // Animación táctil elástica
            .fortniteClickable(
                scaleDown = 0.92f,
                onClick = onClick
            ),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = FortniteCardBg)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Fondo degradado por rareza
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(getRarityBrush(rarityColor))
            )

            // Resplandor central
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(rarityColor.copy(alpha = 0.3f), Color.Transparent),
                            radius = 240f
                        )
                    )
            )

            // Imagen del objeto
            if (imageUrl.isNotBlank()) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = itemName,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 10.dp, start = 12.dp, end = 12.dp, bottom = 65.dp)
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 65.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "🎯", fontSize = 38.sp)
                }
            }

            // Chip superior de Rareza angular
            FortniteRarityBadge(
                rarityText = rarityText,
                rarityColor = rarityColor,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
            )

            // Degradado inferior para el texto
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(95.dp)
                    .align(Alignment.BottomCenter)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, FortniteDarkBg.copy(alpha = 0.98f))
                        )
                    )
            )

            // Información inferior: Nombre y V-Bucks
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 10.dp, vertical = 10.dp)
            ) {
                Text(
                    text = itemName,
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = rarityText.uppercase(),
                        color = rarityColor,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 0.5.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )

                    // Badge de V-Bucks
                    Surface(
                        color = FortniteYellow.copy(alpha = 0.18f),
                        shape = CutCornerShape(topStart = 6.dp, bottomEnd = 6.dp),
                        border = BorderStroke(1.dp, FortniteYellow.copy(alpha = 0.6f))
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "🪙 $price",
                                color = FortniteYellow,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Black
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ShopDetailContent(entry: ShopEntry, onBack: () -> Unit) {
    val itemName = getEntryName(entry)
    val imageUrl = getEntryImage(entry)
    val rarityText = getEntryRarity(entry)
    val rarityColor = getRarityColor(rarityText)
    val description = entry.brItems?.firstOrNull()?.description
        ?: entry.items?.firstOrNull()?.description
        ?: entry.bundle?.info
        ?: "Disponible en la Tienda de Objetos de la isla."
    val price = entry.finalPrice ?: entry.regularPrice ?: 0
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .verticalScroll(scrollState)
            .padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Marco de héroe 3D grande
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(310.dp)
                .shadow(
                    elevation = 20.dp,
                    shape = RoundedCornerShape(26.dp),
                    ambientColor = rarityColor.copy(alpha = 0.4f),
                    spotColor = rarityColor
                )
                .clip(RoundedCornerShape(26.dp))
                .border(
                    2.dp,
                    Brush.verticalGradient(
                        listOf(rarityColor, rarityColor.copy(alpha = 0.3f), Color.White.copy(alpha = 0.1f))
                    ),
                    RoundedCornerShape(26.dp)
                ),
            color = FortniteCardBg
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                // Resplandor radiante
                Box(
                    modifier = Modifier
                        .size(240.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(rarityColor.copy(alpha = 0.4f), Color.Transparent),
                                radius = 320f
                            )
                        )
                )

                if (imageUrl.isNotBlank()) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = itemName,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(22.dp)
                    )
                } else {
                    Text(text = "🎯", fontSize = 64.sp)
                }

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

        // Título del Objeto
        Text(
            text = itemName.uppercase(),
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 0.8.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(6.dp))

        // Rareza y Precio
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = rarityText.uppercase(),
                color = rarityColor,
                fontSize = 13.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.width(8.dp))
            Surface(
                color = FortniteYellow.copy(alpha = 0.2f),
                shape = CutCornerShape(topStart = 6.dp, bottomEnd = 6.dp),
                border = BorderStroke(1.dp, FortniteYellow.copy(alpha = 0.7f))
            ) {
                Text(
                    text = "🪙 $price V-BUCKS",
                    color = FortniteYellow,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Descripción
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(16.dp)),
            color = FortniteCardBg.copy(alpha = 0.7f),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "DETALLES",
                    color = FortniteCyan,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.2.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = description,
                    color = Color.LightGray,
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(26.dp))

        // Botón Regresar con animación táctil
        Surface(
            modifier = Modifier
                .width(200.dp)
                .height(48.dp)
                .shadow(12.dp, RoundedCornerShape(14.dp))
                .clip(RoundedCornerShape(14.dp))
                .border(1.dp, FortniteYellow.copy(alpha = 0.8f), RoundedCornerShape(14.dp))
                .fortniteClickable(scaleDown = 0.90f, onClick = onBack),
            color = FortniteYellow
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "◀ REGRESAR",
                    color = Color.Black,
                    fontWeight = FontWeight.Black,
                    fontSize = 14.sp,
                    letterSpacing = 1.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}

// Métodos helper estáticos
private fun getEntryName(entry: ShopEntry): String {
    return entry.bundle?.name
        ?: entry.brItems?.firstOrNull()?.name
        ?: entry.items?.firstOrNull()?.name
        ?: entry.tracks?.firstOrNull()?.title
        ?: entry.devName
        ?: "Objeto de Tienda"
}

private fun getEntryImage(entry: ShopEntry): String {
    return entry.bundle?.image
        ?: entry.newDisplayAsset?.renderImages?.firstOrNull()?.image
        ?: entry.brItems?.firstOrNull()?.images?.featured
        ?: entry.brItems?.firstOrNull()?.images?.icon
        ?: entry.items?.firstOrNull()?.images?.featured
        ?: entry.items?.firstOrNull()?.images?.icon
        ?: entry.tracks?.firstOrNull()?.albumArt
        ?: ""
}

private fun getEntryRarity(entry: ShopEntry): String {
    return entry.brItems?.firstOrNull()?.rarity?.displayValue
        ?: entry.items?.firstOrNull()?.rarity?.displayValue
        ?: entry.brItems?.firstOrNull()?.rarity?.value
        ?: "Común"
}