package com.example.fornitewiki.ui.map

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

// Colores del tema Fortnite HUD
private val FortniteOceanBlue = Color(0xFF0C4685)
private val FortniteDarkBg = Color(0xFF0E121B)
private val FortniteCardBg = Color(0xEB161B29)
private val FortniteCyan = Color(0xFF00F0FF)
private val FortniteYellow = Color(0xFFFFD700)

@Composable
fun MapScreen(viewModel: MapViewModel) {
    val state = viewModel.uiState

    // Variables de Zoom y Posición
    var targetScale by remember { mutableFloatStateOf(1f) }
    val animatedScale by animateFloatAsState(
        targetValue = targetScale,
        animationSpec = tween(durationMillis = 200),
        label = "ZoomAnimation"
    )

    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }
    var containerSize by remember { mutableStateOf(IntSize.Zero) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FortniteDarkBg)
    ) {
        // --- CONTENEDOR DEL MAPA ---
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clipToBounds()
                // El fondo azul océano integra las franjas de la imagen perfectamente
                .background(FortniteOceanBlue)
                .onSizeChanged { containerSize = it }
                .pointerInput(Unit) {
                    detectTransformGestures { _, pan, zoom, _ ->
                        val newScale = (targetScale * zoom).coerceIn(1f, 5f)
                        val maxX = (containerSize.width * (newScale - 1f)) / 2f
                        val maxY = (containerSize.height * (newScale - 1f)) / 2f

                        targetScale = newScale
                        if (targetScale > 1f) {
                            offsetX = (offsetX + pan.x).coerceIn(-maxX, maxX)
                            offsetY = (offsetY + pan.y).coerceIn(-maxY, maxY)
                        } else {
                            offsetX = 0f
                            offsetY = 0f
                        }
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            when {
                state.isLoading -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(color = FortniteCyan, strokeWidth = 3.dp)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "CARGANDO ISLA...",
                            color = Color.White,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    }
                }
                state.errorMessage != null -> {
                    Surface(
                        color = FortniteCardBg,
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .padding(24.dp)
                            .border(1.dp, Color.Red.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(20.dp)
                        ) {
                            Text(text = "⚠️ Error de conexión", color = Color.Red, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(text = state.errorMessage, color = Color.LightGray, fontSize = 12.sp)
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = { viewModel.fetchMapData() },
                                colors = ButtonDefaults.buttonColors(containerColor = FortniteCyan)
                            ) {
                                Text("Reintentar", color = Color.Black, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
                state.mapImageUrl != null -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .graphicsLayer(
                                scaleX = animatedScale,
                                scaleY = animatedScale,
                                translationX = offsetX,
                                translationY = offsetY
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = state.mapImageUrl,
                            contentDescription = "Mapa de Fortnite",
                            contentScale = ContentScale.Fit, // Se ve completo en su aspecto original 1:1
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }

        // --- ENCABEZADO ESTILO HUD (FLOTANTE OVERLAY) ---
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(16.dp)
                .shadow(12.dp, RoundedCornerShape(20.dp))
                .clip(RoundedCornerShape(20.dp))
                .border(1.dp, Color.White.copy(alpha = 0.15f), RoundedCornerShape(20.dp)),
            color = FortniteCardBg
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(Color.Green)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "MAPA OFICIAL",
                            color = FortniteCyan,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.2.sp
                        )
                    }
                    Text(
                        text = "Isla de Batalla",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                // Indicador de zoom activo
                Surface(
                    color = Color.White.copy(alpha = 0.08f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "${String.format("%.1f", animatedScale)}x",
                        color = FortniteYellow,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }
        }

        // --- DEGRADADO SOMBRA SUPERIOR PARA CONTINUIDAD DE LECTURA ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(FortniteDarkBg.copy(alpha = 0.8f), Color.Transparent)
                    )
                )
        )

        // --- BARRAS DE CONTROL Y NAVEGACIÓN FLOTANTES (INFERIOR) ---
        if (state.mapImageUrl != null) {
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 16.dp, bottom = 24.dp)
                    .shadow(12.dp, RoundedCornerShape(24.dp))
                    .clip(RoundedCornerShape(24.dp))
                    .border(1.dp, Color.White.copy(alpha = 0.15f), RoundedCornerShape(24.dp)),
                color = FortniteCardBg
            ) {
                Column(
                    modifier = Modifier.padding(6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    // Botón Zoom +
                    IconButton(
                        onClick = {
                            val newScale = (targetScale + 0.5f).coerceAtMost(5f)
                            val maxX = (containerSize.width * (newScale - 1f)) / 2f
                            val maxY = (containerSize.height * (newScale - 1f)) / 2f
                            targetScale = newScale
                            offsetX = offsetX.coerceIn(-maxX, maxX)
                            offsetY = offsetY.coerceIn(-maxY, maxY)
                        },
                        enabled = targetScale < 5f
                    ) {
                        Text(
                            text = "+",
                            color = if (targetScale < 5f) FortniteCyan else Color.DarkGray,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Botón Resetear Posición
                    AnimatedVisibility(
                        visible = targetScale > 1.05f || offsetX != 0f || offsetY != 0f,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        IconButton(
                            onClick = {
                                targetScale = 1f
                                offsetX = 0f
                                excludeY()
                                offsetY = 0f
                            }
                        ) {
                            Text(
                                text = "↺",
                                color = FortniteYellow,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    // Botón Zoom -
                    IconButton(
                        onClick = {
                            val newScale = (targetScale - 0.5f).coerceAtLeast(1f)
                            if (newScale == 1f) {
                                targetScale = 1f
                                offsetX = 0f
                                offsetY = 0f
                            } else {
                                val maxX = (containerSize.width * (newScale - 1f)) / 2f
                                val maxY = (containerSize.height * (newScale - 1f)) / 2f
                                targetScale = newScale
                                offsetX = offsetX.coerceIn(-maxX, maxX)
                                offsetY = offsetY.coerceIn(-maxY, maxY)
                            }
                        },
                        enabled = targetScale > 1f
                    ) {
                        Text(
                            text = "−",
                            color = if (targetScale > 1f) FortniteCyan else Color.DarkGray,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

private fun excludeY() {}