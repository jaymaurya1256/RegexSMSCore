package com.example.kuchbhi.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kuchbhi.MainViewModel
import com.example.kuchbhi.ui.screens.HomeScreen
import com.example.kuchbhi.ui.screens.KuchBhiScreen
import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
object KuchBhi

@Composable
fun AppNavigation(modifier: Modifier = Modifier, viewModel: MainViewModel) {
    val navController = rememberNavController()
    val isPermissionGranted = viewModel.isPermissionGranted.collectAsState()


    NavHost(navController, startDestination = KuchBhi) {
        composable<Home> {
            HomeScreen(navController)
        }
        composable<KuchBhi> {
            KuchBhiScreen(navController)
        }
    }


    if (isPermissionGranted.value) {
        navController.navigate(Home) {
            popUpTo(0)
            launchSingleTop = true
        }
    }


}
