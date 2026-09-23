package com.example.fornitewiki

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fornitewiki.data.model.CosmeticItem
import com.example.fornitewiki.data.network.RetrofitClient
import com.example.fornitewiki.domain.repository.CosmeticsRepositoryImpl
import com.example.fornitewiki.domain.repository.MapRepositoryImpl
import com.example.fornitewiki.domain.repository.ShopRepositoryImpl
import com.example.fornitewiki.domain.usecase.GetCosmeticsUseCase
import com.example.fornitewiki.domain.usecase.GetMapUseCase
import com.example.fornitewiki.ui.cosmetics.CosmeticDetailScreen
import com.example.fornitewiki.ui.cosmetics.CosmeticsScreen
import com.example.fornitewiki.ui.cosmetics.CosmeticsViewModel
import com.example.fornitewiki.ui.home.HomeScreen
import com.example.fornitewiki.ui.home.NewsViewModel
import com.example.fornitewiki.ui.map.MapScreen
import com.example.fornitewiki.ui.map.MapViewModel
import com.example.fornitewiki.ui.shop.ShopScreen
import com.example.fornitewiki.ui.shop.ShopViewModel
import com.example.fornitewiki.ui.navigation.Screen
import com.example.fornitewiki.ui.theme.ForniteWikiTheme
import com.example.fornitewiki.ui.theme.FortniteSurface

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicialización de Cosméticos
        val apiService = RetrofitClient.apiService
        val cosmeticsRepository = CosmeticsRepositoryImpl(apiService)
        val cosmeticsUseCase = GetCosmeticsUseCase(cosmeticsRepository)
        val cosmeticsViewModel = CosmeticsViewModel(cosmeticsUseCase)

        // Inicialización de Mapa
        val mapRepository = MapRepositoryImpl(apiService)
        val mapUseCase = GetMapUseCase(mapRepository)
        val mapViewModel = MapViewModel(mapUseCase)

        // Inicialización de Tienda
        val shopRepository = ShopRepositoryImpl(apiService)
        val shopViewModel = ShopViewModel(shopRepository)

        val newsViewModel = NewsViewModel()

        enableEdgeToEdge()
        setContent {
            ForniteWikiTheme {
                var currentScreen by remember { mutableStateOf<Screen>(Screen.Cosmetics) }
                var selectedCosmetic by remember { mutableStateOf<CosmeticItem?>(null) }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = com.example.fornitewiki.ui.theme.FortniteDarkBg,
                    bottomBar = {
                        if (selectedCosmetic == null) {
                            Surface(
                                modifier = Modifier
                                    .padding(start = 16.dp, end = 16.dp, bottom = 28.dp)
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(26.dp))
                                    .border(
                                        1.5.dp,
                                        androidx.compose.ui.graphics.Brush.horizontalGradient(
                                            listOf(
                                                com.example.fornitewiki.ui.theme.FortniteCyan.copy(alpha = 0.6f),
                                                com.example.fornitewiki.ui.theme.FortniteYellow.copy(alpha = 0.4f),
                                                com.example.fornitewiki.ui.theme.FortniteCyan.copy(alpha = 0.6f)
                                            )
                                        ),
                                        RoundedCornerShape(26.dp)
                                    ),
                                color = com.example.fornitewiki.ui.theme.FortniteCardBg.copy(alpha = 0.96f),
                                tonalElevation = 12.dp,
                                shadowElevation = 16.dp
                            ) {
                                NavigationBar(
                                    containerColor = com.example.fornitewiki.ui.theme.FortniteCardBg,
                                    contentColor = Color.White
                                ) {
                                    NavigationBarItem(
                                        selected = currentScreen == Screen.Cosmetics,
                                        onClick = { currentScreen = Screen.Cosmetics },
                                        icon = { Text("👕", fontSize = 18.sp) },
                                        label = { Text("TAQUILLA", fontWeight = FontWeight.Black, fontSize = 10.sp, letterSpacing = 0.5.sp) },
                                        colors = NavigationBarItemDefaults.colors(
                                            selectedIconColor = com.example.fornitewiki.ui.theme.FortniteCyan,
                                            unselectedIconColor = Color.Gray,
                                            selectedTextColor = com.example.fornitewiki.ui.theme.FortniteCyan,
                                            unselectedTextColor = Color.Gray,
                                            indicatorColor = com.example.fornitewiki.ui.theme.FortniteCyan.copy(alpha = 0.18f)
                                        )
                                    )
                                    NavigationBarItem(
                                        selected = currentScreen == Screen.Map,
                                        onClick = { currentScreen = Screen.Map },
                                        icon = { Text("🗺️", fontSize = 18.sp) },
                                        label = { Text("MAPA", fontWeight = FontWeight.Black, fontSize = 10.sp, letterSpacing = 0.5.sp) },
                                        colors = NavigationBarItemDefaults.colors(
                                            selectedIconColor = com.example.fornitewiki.ui.theme.FortniteCyan,
                                            unselectedIconColor = Color.Gray,
                                            selectedTextColor = com.example.fornitewiki.ui.theme.FortniteCyan,
                                            unselectedTextColor = Color.Gray,
                                            indicatorColor = com.example.fornitewiki.ui.theme.FortniteCyan.copy(alpha = 0.18f)
                                        )
                                    )
                                    NavigationBarItem(
                                        selected = currentScreen == Screen.Home,
                                        onClick = { currentScreen = Screen.Home },
                                        icon = { Text("📰", fontSize = 18.sp) },
                                        label = { Text("NOTICIAS", fontWeight = FontWeight.Black, fontSize = 10.sp, letterSpacing = 0.5.sp) },
                                        colors = NavigationBarItemDefaults.colors(
                                            selectedIconColor = com.example.fornitewiki.ui.theme.FortniteCyan,
                                            unselectedIconColor = Color.Gray,
                                            selectedTextColor = com.example.fornitewiki.ui.theme.FortniteCyan,
                                            unselectedTextColor = Color.Gray,
                                            indicatorColor = com.example.fornitewiki.ui.theme.FortniteCyan.copy(alpha = 0.18f)
                                        )
                                    )
                                    NavigationBarItem(
                                        selected = currentScreen == Screen.Shop,
                                        onClick = { currentScreen = Screen.Shop },
                                        icon = { Text("🪙", fontSize = 18.sp) },
                                        label = { Text("TIENDA", fontWeight = FontWeight.Black, fontSize = 10.sp, letterSpacing = 0.5.sp) },
                                        colors = NavigationBarItemDefaults.colors(
                                            selectedIconColor = com.example.fornitewiki.ui.theme.FortniteYellow,
                                            unselectedIconColor = Color.Gray,
                                            selectedTextColor = com.example.fornitewiki.ui.theme.FortniteYellow,
                                            unselectedTextColor = Color.Gray,
                                            indicatorColor = com.example.fornitewiki.ui.theme.FortniteYellow.copy(alpha = 0.18f)
                                        )
                                    )
                                }
                            }
                        }
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        if (selectedCosmetic != null) {
                            CosmeticDetailScreen(
                                item = selectedCosmetic!!,
                                onBackClick = { selectedCosmetic = null }
                            )
                        } else {
                            when (currentScreen) {
                                is Screen.Cosmetics -> {
                                    CosmeticsScreen(
                                        items = cosmeticsViewModel.uiState.items,
                                        isLoading = cosmeticsViewModel.uiState.isLoading,
                                        onItemClick = { clickedItem ->
                                            selectedCosmetic = clickedItem
                                        }
                                    )
                                }
                                is Screen.Map -> {
                                    MapScreen(viewModel = mapViewModel)
                                }
                                is Screen.Home -> {
                                    HomeScreen()
                                }
                                is Screen.Shop -> {
                                    // Pantalla de la tienda conectada
                                    ShopScreen(viewModel = shopViewModel)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}