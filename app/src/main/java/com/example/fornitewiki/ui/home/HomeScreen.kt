package com.example.fornitewiki.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.fornitewiki.ui.components.FortniteHUDHeader
import com.example.fornitewiki.ui.components.fortniteClickable
import com.example.fornitewiki.ui.theme.FortniteCardBg
import com.example.fornitewiki.ui.theme.FortniteCyan
import com.example.fornitewiki.ui.theme.FortniteDarkBg
import com.example.fornitewiki.ui.theme.FortniteYellow

@Composable
fun HomeScreen(viewModel: NewsViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FortniteDarkBg)
    ) {
        // Encabezado HUD estilo Tablón de Noticias de Fortnite
        FortniteHUDHeader(
            tag = "TABLÓN DE NOTICIAS",
            title = "Novedades de la Isla",
            subtitle = "Actualizaciones, parches y eventos de temporada",
            rightBadge = "EN VIVO 🔴",
            badgeColor = FortniteCyan
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 14.dp),
            contentAlignment = Alignment.Center
        ) {
            when (val state = uiState) {
                is NewsUiState.Loading -> {
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
                            text = "DESCARGANDO NOTICIAS...",
                            color = Color.White,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.2.sp
                        )
                    }
                }
                is NewsUiState.Error -> {
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
                                text = "⚠️ ERROR AL CARGAR NOTICIAS",
                                color = Color.Red,
                                fontWeight = FontWeight.Black,
                                fontSize = 15.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = state.message,
                                color = Color.LightGray,
                                fontSize = 12.sp
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = { viewModel.fetchNews() },
                                colors = ButtonDefaults.buttonColors(containerColor = FortniteCyan),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("REINTENTAR", color = Color.Black, fontWeight = FontWeight.Black)
                            }
                        }
                    }
                }
                is NewsUiState.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(14.dp),
                        contentPadding = PaddingValues(top = 4.dp, bottom = 110.dp)
                    ) {
                        items(state.news) { item ->
                            // Tarjeta de Noticias temática con animación táctil
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .shadow(12.dp, RoundedCornerShape(20.dp))
                                    .clip(RoundedCornerShape(20.dp))
                                    .border(
                                        1.2.dp,
                                        Brush.verticalGradient(
                                            listOf(
                                                FortniteCyan.copy(alpha = 0.6f),
                                                Color.White.copy(alpha = 0.1f)
                                            )
                                        ),
                                        RoundedCornerShape(20.dp)
                                    )
                                    .fortniteClickable(scaleDown = 0.94f) {
                                        // Animación táctil interactiva
                                    },
                                color = FortniteCardBg
                            ) {
                                Column(modifier = Modifier.fillMaxWidth()) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(190.dp)
                                    ) {
                                        AsyncImage(
                                            model = item.image,
                                            contentDescription = item.title,
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )

                                        // Degradado sobre la imagen para contraste
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .background(
                                                    Brush.verticalGradient(
                                                        listOf(
                                                            Color.Transparent,
                                                            FortniteCardBg.copy(alpha = 0.9f)
                                                        )
                                                    )
                                                )
                                        )

                                        // Badge de categoría "OFICIAL"
                                        Surface(
                                            color = FortniteYellow.copy(alpha = 0.25f),
                                            shape = CutCornerShape(topStart = 6.dp, bottomEnd = 6.dp),
                                            border = BorderStroke(1.dp, FortniteYellow.copy(alpha = 0.7f)),
                                            modifier = Modifier
                                                .align(Alignment.TopStart)
                                                .padding(12.dp)
                                        ) {
                                            Text(
                                                text = "NOTICIA OFICIAL",
                                                color = FortniteYellow,
                                                fontSize = 9.sp,
                                                fontWeight = FontWeight.Black,
                                                letterSpacing = 0.8.sp,
                                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                            )
                                        }
                                    }

                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp)
                                    ) {
                                        Text(
                                            text = item.title.uppercase(),
                                            color = Color.White,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Black,
                                            letterSpacing = 0.5.sp
                                        )

                                        item.body?.let { bodyText ->
                                            if (bodyText.isNotBlank()) {
                                                Spacer(modifier = Modifier.height(6.dp))
                                                Text(
                                                    text = bodyText,
                                                    color = Color.LightGray,
                                                    fontSize = 13.sp,
                                                    lineHeight = 18.sp
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
        }
    }
}