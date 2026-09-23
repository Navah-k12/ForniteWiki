package com.example.fornitewiki.ui.cosmetics

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fornitewiki.data.model.CosmeticItem
import com.example.fornitewiki.ui.components.FortniteHUDHeader
import com.example.fornitewiki.ui.theme.FortniteCardBg
import com.example.fornitewiki.ui.theme.FortniteCyan
import com.example.fornitewiki.ui.theme.FortniteDarkBg

@Composable
fun CosmeticsScreen(
    items: List<CosmeticItem>,
    isLoading: Boolean,
    errorMessage: String? = null,
    onRetry: () -> Unit = {},
    onItemClick: (CosmeticItem) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FortniteDarkBg)
    ) {
        // Encabezado HUD estilo Taquilla de Fortnite
        FortniteHUDHeader(
            tag = "TAQUILLA DE BATALLA",
            title = "Cosméticos de la Isla",
            subtitle = "Skins, picos, mochilas y planeadores",
            rightBadge = if (items.isNotEmpty()) "${items.size} ÍTEMS" else null,
            badgeColor = FortniteCyan
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 14.dp),
            contentAlignment = Alignment.Center
        ) {
            when {
                isLoading -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(
                            color = FortniteCyan,
                            strokeWidth = 3.dp
                        )
                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = "CARGANDO TAQUILLA...",
                            color = Color.White,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.2.sp
                        )
                    }
                }
                errorMessage != null -> {
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
                                text = "⚠️ ERROR AL CARGAR COSMÉTICOS",
                                color = Color.Red,
                                fontWeight = FontWeight.Black,
                                fontSize = 15.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = errorMessage,
                                color = Color.LightGray,
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = onRetry,
                                colors = ButtonDefaults.buttonColors(containerColor = FortniteCyan),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("REINTENTAR", color = Color.Black, fontWeight = FontWeight.Black)
                            }
                        }
                    }
                }
                items.isEmpty() -> {
                    Text(
                        text = "No hay cosméticos disponibles.",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
                else -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        contentPadding = PaddingValues(top = 4.dp, bottom = 110.dp)
                    ) {
                        items(items) { item ->
                            CosmeticItemCard(
                                item = item,
                                onClick = { onItemClick(item) }
                            )
                        }
                    }
                }
            }
        }
    }
}