package com.example.fornitewiki.ui.navigation

sealed class Screen(val route: String, val title: String) {
    object Cosmetics : Screen("cosmetics", "Cosméticos")
    object Map : Screen("map", "Mapa")
    object Home : Screen("home", "Noticias")
    object Shop : Screen("shop", "Tienda") // <-- Agrega esta línea para la tienda
}