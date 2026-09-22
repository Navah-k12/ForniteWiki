package com.example.fornitewiki.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage


/**
 * Project: ForniteWiki
 * From: com.example.fornitewiki.ui.home
 * Created by: navah
 * On: 20/9/26
 * All rights reserved: 2026
 */
@Composable
fun HomeScreen(viewModel: NewsViewModel = viewModel()) {
    // Escuchamos el uiState del ViewModel en tiempo real
    val uiState by viewModel.uiState.collectAsState()

    when (val state = uiState) {
        is NewsUiState.Loading -> {
            // 1. Estado de carga: Muestra un círculo girando en el centro
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is NewsUiState.Error -> {
            // 2. Estado de error: Muestra el mensaje en pantalla
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Error: ${state.message}")
            }
        }
        is NewsUiState.Success -> {
            // 3. Estado de éxito: Lista de tarjetas de noticias scrollable
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(state.news) { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            // Carga la imagen de la URL mediante Coil
                            AsyncImage(
                                model = item.image,
                                contentDescription = item.title,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(180.dp),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = item.title,
                                style = MaterialTheme.typography.titleMedium
                            )
                            item.body?.let { bodyText ->
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = bodyText,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}