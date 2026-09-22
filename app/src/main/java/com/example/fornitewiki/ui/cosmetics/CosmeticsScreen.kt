package com.example.fornitewiki.ui.cosmetics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.fornitewiki.data.model.CosmeticItem
import com.example.fornitewiki.ui.theme.FortniteSurface

@Composable
fun CosmeticsScreen(
    items: List<CosmeticItem>,
    isLoading: Boolean,
    onItemClick: (CosmeticItem) -> Unit = {} // <-- Añadimos este parámetro para manejar el clic
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FortniteSurface)
            .padding(8.dp)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Color.Cyan
            )
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items) { item ->
                    CosmeticItemCard(
                        item = item,
                        onClick = { onItemClick(item) } // <-- Le pasamos la acción a la tarjeta
                    )
                }
            }
        }
    }
}