package com.example.fornitewiki.ui.shop

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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

private val FortniteDarkBg = Color(0xFF0B0E14)
private val FortniteCardBg = Color(0xFF151924)
private val FortniteCyan = Color(0xFF00F0FF)
private val FortniteYellow = Color(0xFFFFD700)

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
            // --- DETALLE DEL OBJETO SELECCIONADO ---
            ShopDetailContent(
                entry = selectedEntry!!,
                onBack = { selectedEntry = null }
            )
        } else {
            // --- LISTA GENERAL DE LA TIENDA ---
            Column(modifier = Modifier.fillMaxSize()) {
                // HUD Header
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .shadow(12.dp, RoundedCornerShape(20.dp))
                        .clip(RoundedCornerShape(20.dp))
                        .border(1.dp, Color.White.copy(alpha = 0.12f), RoundedCornerShape(20.dp)),
                    color = FortniteCardBg
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .clip(CircleShape)
                                        .background(FortniteYellow)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "TIENDA DE OBJETOS",
                                    color = FortniteYellow,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Black,
                                    letterSpacing = 1.2.sp
                                )
                            }
                            Text(
                                text = "Diaria y Destacados",
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }

                        Surface(
                            color = FortniteYellow.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, FortniteYellow.copy(alpha = 0.4f))
                        ) {
                            Text(
                                text = "🪙 V-BUCKS",
                                color = FortniteYellow,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }
                }

                // Grid
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    when {
                        state.isLoading -> {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                CircularProgressIndicator(color = FortniteCyan, strokeWidth = 3.dp)
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = "CARGANDO TIENDA...",
                                    color = Color.White,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        state.errorMessage != null -> {
                            Surface(
                                color = FortniteCardBg,
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(1.dp, Color.Red.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.padding(20.dp)
                                ) {
                                    Text(text = "⚠️ Error al cargar la tienda", color = Color.Red, fontWeight = FontWeight.Bold)
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(text = state.errorMessage ?: "", color = Color.LightGray, fontSize = 12.sp)
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Button(
                                        onClick = { viewModel.fetchShopData() },
                                        colors = ButtonDefaults.buttonColors(containerColor = FortniteCyan)
                                    ) {
                                        Text("Reintentar", color = Color.Black, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                        state.shopEntries.isEmpty() -> {
                            Text(text = "No hay objetos disponibles.", color = Color.Gray, fontSize = 14.sp)
                        }
                        else -> {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                modifier = Modifier.fillMaxSize(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                                contentPadding = PaddingValues(bottom = 110.dp)
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
    val price = entry.finalPrice ?: entry.regularPrice ?: 0

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(230.dp)
            .shadow(8.dp, RoundedCornerShape(18.dp))
            .clip(RoundedCornerShape(18.dp))
            .border(1.dp, Color.White.copy(alpha = 0.12f), RoundedCornerShape(18.dp))
            .clickable { onClick() },
        color = FortniteCardBg
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.04f),
                                Color.Transparent,
                                FortniteDarkBg.copy(alpha = 0.95f)
                            )
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
                        .padding(top = 12.dp, start = 12.dp, end = 12.dp, bottom = 55.dp)
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 55.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "🎯", fontSize = 36.sp)
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .align(Alignment.BottomCenter)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, FortniteDarkBg)
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 12.dp, vertical = 10.dp)
            ) {
                Text(
                    text = itemName,
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
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
                        color = FortniteCyan,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )

                    Surface(
                        color = FortniteYellow.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(0.5.dp, FortniteYellow.copy(alpha = 0.35f))
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
    val description = entry.brItems?.firstOrNull()?.description
        ?: entry.items?.firstOrNull()?.description
        ?: entry.bundle?.info
        ?: "Disponible en la Tienda de Objetos."
    val price = entry.finalPrice ?: entry.regularPrice ?: 0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Marco de la imagen en grande
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .shadow(16.dp, RoundedCornerShape(24.dp))
                .clip(RoundedCornerShape(24.dp))
                .border(2.dp, FortniteCyan, RoundedCornerShape(24.dp)),
            color = FortniteCardBg
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (imageUrl.isNotBlank()) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = itemName,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp)
                    )
                } else {
                    Text(text = "🎯", fontSize = 64.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Título del Objeto
        Text(
            text = itemName,
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(6.dp))

        // Rareza y Precio
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = rarityText.uppercase(),
                color = FortniteCyan,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = " • 🪙 $price V-BUCKS",
                color = FortniteYellow,
                fontSize = 12.sp,
                fontWeight = FontWeight.Black
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Descripción
        Text(
            text = description,
            color = Color.LightGray,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(28.dp))

        // Botón Regresar
        Button(
            onClick = onBack,
            colors = ButtonDefaults.buttonColors(containerColor = FortniteCyan),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .width(160.dp)
                .height(44.dp)
        ) {
            Text(
                text = "Regresar",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
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