package com.example.smsmanager.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.smsmanager.ui.screen.ReceiveSmsScreen
import com.example.smsmanager.ui.screen.SendSmsUI

@Composable
fun NavGraph(
    context: Context,
    modifier: Modifier,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.SendScreen.route,
        modifier = modifier
    ) {
        composable(route = Screen.SendScreen.route) {
            SendSmsUI(context = context)
        }

        composable(route = Screen.ReceiveScreen.route) {
            ReceiveSmsScreen()
        }
    }
}