package com.example.praktikum12.uicontroller

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.praktikum12.uicontroller.route.DestinasiDetail
import com.example.praktikum12.uicontroller.route.DestinasiEdit
import com.example.praktikum12.uicontroller.route.DestinasiEntry
import com.example.praktikum12.uicontroller.route.DestinasiHome
import com.example.praktikum12.view.DetailSiswaScreen
import com.example.praktikum12.view.EditScreen
import com.example.praktikum12.view.EntrySiswaScreen
import com.example.praktikum12.view.HomeScreen

@Composable
fun DataSiswaApp(navController: NavHostController = rememberNavController(),
                 modifier: Modifier = Modifier) {
    HostNavigasi(navController = navController, modifier = modifier)
}

@Composable
fun HostNavigasi(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = modifier
    ) {
        // --- HALAMAN HOME ---
        composable(DestinasiHome.route) {
            HomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntry.route) },
                // FIX: Mengirim ID dengan format yang benar
                onDetailClick = { itemId ->
                    navController.navigate("${DestinasiDetail.route}/$itemId")
                }
            )
        }

        // --- HALAMAN ENTRY ---
        composable(DestinasiEntry.route) {
            EntrySiswaScreen(navigateBack = {
                navController.popBackStack()
            })
        }

        // --- HALAMAN DETAIL ---
        composable(
            route = DestinasiDetail.routeWithArgs,
            arguments = listOf(navArgument(DestinasiDetail.siswaIdArg) {
                type = NavType.IntType
            })
        ) {
            DetailSiswaScreen(
                navigateBack = { navController.navigateUp() },
                navigateToEditItem = { itemId ->
                    navController.navigate("${DestinasiEdit.route}/$itemId")
                }
            )
        }

        // --- HALAMAN EDIT ---
        composable(
            route = DestinasiEdit.routeWithArgs,
            arguments = listOf(navArgument(DestinasiEdit.siswaIdArg) {
                type = NavType.IntType
            })
        ) {
            EditScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}