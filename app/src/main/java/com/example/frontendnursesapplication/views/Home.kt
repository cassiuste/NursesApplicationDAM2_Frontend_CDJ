package com.example.frontendnursesapplication.views

import androidx.compose.foundation.Image
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
            TopSection()
            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = stringResource(R.string.title_init),
                style = MaterialTheme.typography.headlineMedium,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(40.dp))

            HomeBody(navController)
        }
    }

}

private data class MenuItem(
    val title: String,
    val route: String,
    val imageRes: Int
)

@Composable
fun HomeBody(navController: NavHostController){

    val menuItems = listOf(
        MenuItem("Login Enfermer@", "login", R.drawable.enfermeros),
        MenuItem("Listado Completo", "listAll", R.drawable.enfermeros2),
        MenuItem("Buscar Enfermer@", "findByName", R.drawable.searchenfermeros)
    )

    LazyVerticalGrid(
        modifier = Modifier,
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(30.dp)
    ) {
        items(menuItems) { item ->
            NavigationCard(
                titleCard = item.title,
                navigationRoute = item.route,
                imageRes = item.imageRes,
                navController = navController
            )
        }
    }

}

@Composable
fun NavigationCard(titleCard: String, navigationRoute: String, navController: NavHostController,imageRes: Int){

    Card(
        modifier = Modifier.aspectRatio(1f),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        onClick = {
            navController.navigate(navigationRoute)
        }

    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = titleCard,
                fontSize = 19.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )


            Spacer(modifier = Modifier.Companion.height(8.dp))

            Image(
                painter = painterResource(id = imageRes),
                contentDescription = titleCard,
                modifier = Modifier.size(100.dp),
                contentScale = ContentScale.Crop
            )
        }
    }

}