package com.example.smsmanager.navigation

sealed class Screen(val route: String) {
    data object SendScreen : Screen(route = "send_screen")
    data object ReceiveScreen : Screen(route = "receive_screen")
}