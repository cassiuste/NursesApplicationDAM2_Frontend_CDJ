package com.example.frontendnursesapplication.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.frontendnursesapplication.R
import com.example.frontendnursesapplication.viewmodels.NurseViewModel
import kotlinx.coroutines.delay

@Composable
fun TopBar(navController: NavController, nurseViewModel: NurseViewModel) {

    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .background(color = colorResource(R.color.light_blue))
            .padding(horizontal = 25.dp, vertical = 25.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = stringResource(id = R.string.app_logo),
                modifier = Modifier.size(42.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column (modifier = Modifier) {
                Text(
                    text = stringResource(R.string.we_are),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                TypewriterText(
                    texts = listOf(stringResource(id = R.string.dentist),
                        stringResource(R.string.specialist),
                        stringResource(R.string.hospital)),
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.black)
                    )
                )
            }
        }

        Box {
            IconButton(
                onClick = { expanded = true },
                modifier = Modifier
                    .background(
                        color = colorResource(R.color.purple_500).copy(alpha = 0.1f),
                        shape = CircleShape
                    )
                    .size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Opciones",
                    tint = colorResource(R.color.purple_500),
                    modifier = Modifier.size(20.dp)
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                // Opción 1: Mi Perfil
                DropdownMenuItem(
                    text = { Text("Mi Perfil") },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                    },
                    onClick = {
                        expanded = false
                        navController.navigate("profile")
                    }
                )

                // Opción 2: Log Out
                DropdownMenuItem(
                    text = { Text("Log out", color = Color.Red) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = null,
                            tint = Color.Red,
                            modifier = Modifier.size(18.dp)
                        )
                    },
                    onClick = {
                        expanded = false
                        nurseViewModel.clearSession()
                        nurseViewModel.resetLoginState()
                        nurseViewModel.resetRegisterState()


                        navController.navigate("login") {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun TypewriterText(
    texts: List<String>,
    typingSpeedMillis: Long = 150,
    deletingSpeedMillis: Long = 100,
    pauseBetweenTexts: Long = 2000,
    style: TextStyle = LocalTextStyle.current
) {
    var textToDisplay by remember { mutableStateOf("") }
    var currentTextIndex by remember { mutableStateOf(0) }
    var cursorVisible by remember { mutableStateOf(true) }

    LaunchedEffect(texts) {
        while (true) {
            val fullText = texts[currentTextIndex]

            for (i in 1..fullText.length) {
                textToDisplay = fullText.substring(0, i)
                delay(typingSpeedMillis)
            }

            delay(pauseBetweenTexts)

            for (i in fullText.length downTo 0) {
                textToDisplay = fullText.substring(0, i)
                delay(deletingSpeedMillis)
            }

            delay(500)

            currentTextIndex = (currentTextIndex + 1) % texts.size
        }
    }

    LaunchedEffect(Unit) {
        while (true) {
            cursorVisible = !cursorVisible
            delay(500)
        }
    }

    Text(
        text = "$textToDisplay${if (cursorVisible) "|" else " "}",
        style = style
    )
}

@Composable
fun ProfileTopBar(
    onBack: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .background(color = colorResource(R.color.light_blue))
            .padding(horizontal = 16.dp, vertical = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBack) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Volver",
                tint = colorResource(R.color.black)
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = stringResource(R.string.my_profile),
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.black)
            )
        )
    }
}

@Composable
fun HomeTopBar(navController: NavController, nurseViewModel: NurseViewModel) {

    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 25.dp, vertical = 25.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {


            Spacer(modifier = Modifier.width(12.dp))

        }

        Box {
            IconButton(
                onClick = { expanded = true },
                modifier = Modifier
                    .background(
                        color = colorResource(R.color.purple_500).copy(alpha = 0.1f),
                        shape = CircleShape
                    )
                    .size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Opciones",
                    tint = colorResource(R.color.purple_500),
                    modifier = Modifier.size(20.dp)
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Mi Perfil") },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                    },
                    onClick = {
                        expanded = false
                        navController.navigate("profile")
                    }
                )

                DropdownMenuItem(
                    text = { Text("Log out", color = Color.Red) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = null,
                            tint = Color.Red,
                            modifier = Modifier.size(18.dp)
                        )
                    },
                    onClick = {
                        expanded = false
                        nurseViewModel.clearSession()
                        nurseViewModel.resetLoginState()
                        nurseViewModel.resetRegisterState()


                        navController.navigate("login") {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}