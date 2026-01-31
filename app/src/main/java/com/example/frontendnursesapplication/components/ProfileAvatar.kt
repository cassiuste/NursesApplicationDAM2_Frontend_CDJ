package com.example.frontendnursesapplication.components

import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.frontendnursesapplication.R
import com.example.frontendnursesapplication.entities.Nurse

@Composable
fun ProfileAvatar(nurse: Nurse?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Color(0xFFE0E0E0)),
            contentAlignment = Alignment.Center
        ) {
            // Aquí puedes poner una imagen real si tienes la URL
            // Por ahora, usamos un icono o una imagen de placeholder
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = "Avatar de perfil",
                modifier = Modifier.size(70.dp),
                tint = Color.Gray // Color del icono
            )
            // Si tuvieras una URL, sería algo así:
            // AsyncImage(
            //    model = nurse?.profileImageUrl ?: R.drawable.default_avatar,
            //    contentDescription = "Avatar de perfil",
            //    modifier = Modifier.fillMaxSize(),
            //    contentScale = ContentScale.Crop
            // )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Nombre del usuario debajo del avatar
        Text(
            text = nurse?.name ?: "Usuario",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = nurse?.user ?: "no user",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}