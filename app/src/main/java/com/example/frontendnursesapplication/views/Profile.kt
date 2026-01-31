package com.example.frontendnursesapplication.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.core.content.ContextCompat
import com.example.frontendnursesapplication.components.ProfileAvatar
import com.example.frontendnursesapplication.components.ProfileTopBar
import com.example.frontendnursesapplication.components.TopBar
import com.example.frontendnursesapplication.entities.GetNurseUiState
import com.example.frontendnursesapplication.entities.Nurse
import com.example.frontendnursesapplication.entities.SessionUiState
import com.example.frontendnursesapplication.viewmodels.NurseViewModel
import kotlinx.coroutines.delay

@Composable
fun ProfileView(navController: NavController, nurseViewModel: NurseViewModel) {

    val sessionUiState by nurseViewModel.sessionState.collectAsState()
    val getNurseUiState by nurseViewModel.getNurseUiState.collectAsState()

    LaunchedEffect(sessionUiState.nurse?.id) {
        sessionUiState.nurse?.id?.let { id ->
            nurseViewModel.getNurse(id)
        }
    }

    Column() {
        ProfileTopBar(onBack = {navController.popBackStack()})

        Spacer(modifier = Modifier.height(20.dp))

        when(val state = getNurseUiState){

            is GetNurseUiState.Idle -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(modifier = Modifier.padding(top = 250.dp))
                }
            }

            is GetNurseUiState.Loading -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(modifier = Modifier.padding(top = 250.dp))
                }
            }

            is GetNurseUiState.NotFound-> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = stringResource(
                        R.string.NotFound),
                        modifier = Modifier.padding(top = 250.dp),
                        color = colorResource(R.color.redstucom))
                }
            }

            is GetNurseUiState.Error -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = stringResource(
                        R.string.Error),
                        modifier = Modifier.padding(top = 250.dp),
                        color = colorResource(R.color.redstucom))
                }
            }

            is GetNurseUiState.Success -> {
                ProfileAvatar(state.nurse)
                NurseForm(nurse = state.nurse, onSave = {
                        navController.navigate("home")
                    }
                )
            }

        }
    }
}

@Composable
fun NurseForm(
    modifier: Modifier = Modifier,
    onSave: (Nurse) -> Unit,
    nurse: Nurse
) {
    var isEditable by remember { mutableStateOf(false) }

    var name by remember { mutableStateOf(nurse.name) }
    var surname by remember { mutableStateOf(nurse.surname) }
    var email by remember { mutableStateOf(nurse.email) }
    var user by remember { mutableStateOf(nurse.user) }
    var pass by remember { mutableStateOf(nurse.pass) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(R.string.pers_info_title), fontWeight = FontWeight.Bold, fontSize = 18.sp)

            Button(
                onClick = { isEditable = !isEditable },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isEditable) Color.Gray else colorResource(R.color.purple_btn)
                )
            ) {
                Text(if (isEditable) stringResource(R.string.btn_cancel) else stringResource(R.string.btn_modify))
            }
        }

        var passwordVisible by remember { mutableStateOf(false) }

        OutlinedTextField(
            value = name.toString(),
            onValueChange = { name = it },
            label = { Text(stringResource(R.string.label_name)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = isEditable
        )

        OutlinedTextField(
            value = surname,
            onValueChange = { surname = it },
            label = { Text(stringResource(R.string.label_surname)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = isEditable
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(stringResource(R.string.label_email)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = isEditable
        )

        OutlinedTextField(
            value = user,
            onValueChange = { user = it },
            label = { Text(stringResource(R.string.label_user)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = isEditable
        )

        OutlinedTextField(
            value = pass,
            onValueChange = { pass = it },
            label = { Text(stringResource(R.string.label_password)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = isEditable,

            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (passwordVisible)
                    painterResource(id = R.drawable.ic_visibility)
                else
                    painterResource(id = R.drawable.ic_visibility_off)

                val description = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(painter = image, contentDescription = description, modifier = Modifier.size(20.dp))
                }
            }
        )

        if (isEditable) {
            Button(
                onClick = {
                    onSave(Nurse(name=name, surname = surname, email = email, user = user, pass = pass))
                    isEditable = false
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(stringResource(R.string.btn_save_changes))
            }
        }
    }
}

