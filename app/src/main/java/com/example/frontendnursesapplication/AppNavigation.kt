package com.example.frontendnursesapplication

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.frontendnursesapplication.views.AllNursesView
import com.example.frontendnursesapplication.views.FindByName
import com.example.frontendnursesapplication.views.InitialView
import com.example.frontendnursesapplication.views.LoginScreen

@Composable
fun AppNavigation(modifier: Modifier) {
    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "start"
    ) {
        composable(route = "start") {
            InitialView(navController = navController)
        }

        composable(route = "login") {
            LoginScreen(navController = navController)
        }

        composable(route = "findByName") {
            FindByName(navController = navController)
        }

        composable(route = "listAll") {
            AllNursesView(navController = navController)
        }
    }
}