package com.example.frontendnursesapplication

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.frontendnursesapplication.viewmodels.NurseViewModel
import com.example.frontendnursesapplication.views.AllNursesView
import com.example.frontendnursesapplication.views.FindByName
import com.example.frontendnursesapplication.views.InitialView
import com.example.frontendnursesapplication.views.LoginScreen
import com.example.frontendnursesapplication.views.RegisterScreen

@Composable
fun AppNavigation(modifier: Modifier) {
    val navController: NavHostController = rememberNavController()
    val nurseViewModel: NurseViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "register"
    ) {
        composable(route = "home") {
            InitialView(navController = navController)
        }

        composable(route = "login") {
            LoginScreen(navController = navController, nurseViewModel)
        }

        composable(route = "register") {
            RegisterScreen(navController = navController, nurseViewModel)
        }

        composable(route = "findByName") {
            FindByName(navController = navController, nurseViewModel)
        }

        composable(route = "listAll") {
            AllNursesView(navController = navController, nurseViewModel)
        }

    }
}