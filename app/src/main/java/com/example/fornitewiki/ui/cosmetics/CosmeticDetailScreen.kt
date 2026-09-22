package com.example.fornitewiki.ui.cosmetics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.fornitewiki.data.model.CosmeticItem
import com.example.fornitewiki.ui.theme.FortniteCardBg
import com.example.fornitewiki.ui.theme.FortniteSurface
import com.example.fornitewiki.ui.theme.getRarityColor

@Composable
fun CosmeticDetailScreen(
    item: CosmeticItem,
    onBackClick: () -> Unit
) {
    val rarityColor = getRarityColor(item.rarity?.value)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FortniteSurface)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Tarjeta grande para la imagen con el color de rareza
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = FortniteCardBg),
            border = androidx.compose.foundation.BorderStroke(2.dp, rarityColor)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = item.images?.icon ?: item.images?.smallIcon,
                    contentDescription = item.name,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Nombre del cosmético
        Text(
            text = item.name ?: "Desconocido",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Tipo / Rareza
        Text(
            text = "${item.type?.value?.uppercase() ?: ""} • ${item.rarity?.displayValue ?: "Común"}",
            color = rarityColor,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Descripción
        Text(
            text = item.description ?: "Sin descripción disponible para este objeto en la wiki.",
            color = Color.LightGray,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botón de regresar centrado y estético
        Button(
            onClick = onBackClick,
            modifier = Modifier
                .width(180.dp)
                .height(45.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = rarityColor)
        ) {
            Text(
                text = "Regresar",
                color = Color.Black,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}