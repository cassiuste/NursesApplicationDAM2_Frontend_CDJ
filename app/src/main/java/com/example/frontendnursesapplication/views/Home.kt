package com.example.frontendnursesapplication.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.frontendnursesapplication.R

@Composable
fun InitialView(navController: NavHostController) {
    Box(modifier = Modifier.statusBarsPadding()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.title_init),
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(40.dp))

            homeBody(navController)
        }
    }

}

private data class MenuItem(
    val title: String,
    val route: String,
)

@Composable
fun homeBody(navController: NavHostController){

    val menuItems = listOf(
        MenuItem("Login Enfermer@", "login"),
        MenuItem("Listado Completo", "listAll"),
        MenuItem("Buscar Enfermer@", "findByName"),
    )

    LazyVerticalGrid(
        modifier = Modifier,
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(30.dp)
    ) {
        items(menuItems) { item ->
            navigationCard(
                titleCard = item.title,
                navigationRoute = item.route,
                navController = navController
            )
        }
    }

}

@Composable
fun navigationCard(titleCard: String, navigationRoute: String, navController: NavHostController){

    Card(
        modifier = Modifier.aspectRatio(1f),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        onClick = {
            navController.navigate(navigationRoute)
        }
    ) {
        Text(
            text = titleCard,
            fontSize = 19.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )
    }

}