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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.core.content.ContextCompat
import com.example.frontendnursesapplication.components.ProfileAvatar
import com.example.frontendnursesapplication.components.ProfileTopBar
import com.example.frontendnursesapplication.components.TopBar
import com.example.frontendnursesapplication.entities.DeleteNurseUiState
import com.example.frontendnursesapplication.entities.GetNurseUiState
import com.example.frontendnursesapplication.entities.Nurse
import com.example.frontendnursesapplication.entities.SessionUiState
import com.example.frontendnursesapplication.entities.UpdateNurseUiState
import com.example.frontendnursesapplication.viewmodels.NurseViewModel
import kotlinx.coroutines.delay

@Composable
fun ProfileView(navController: NavController, nurseViewModel: NurseViewModel) {

    val sessionUiState by nurseViewModel.sessionState.collectAsState()
    val getNurseUiState by nurseViewModel.getNurseUiState.collectAsState()
    val deleteNurseUiState by nurseViewModel.deleteNurseUiState.collectAsState()

    var showDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current

    LaunchedEffect(sessionUiState.nurse?.id) {
        sessionUiState.nurse?.id?.let { id ->
            nurseViewModel.getNurse(id)
        }
    }

    LaunchedEffect(deleteNurseUiState) {
        when (deleteNurseUiState) {
            is DeleteNurseUiState.Success -> {

                nurseViewModel.resetLoginState()
                nurseViewModel.resetRegisterState()

                android.widget.Toast.makeText(
                    context,
                    "Cuenta eliminada",
                    android.widget.Toast.LENGTH_SHORT
                ).show()

                navController.navigate("register") {
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }

                nurseViewModel.resetDeleteState()
            }

            is DeleteNurseUiState.Error -> {
                android.widget.Toast.makeText(
                    context,
                    "Hubo un error al borrar",
                    android.widget.Toast.LENGTH_SHORT
                ).show()
                nurseViewModel.resetDeleteState()
            }

            else -> {}
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
                DeleteAccountButton(onClick = { showDialog = true })
                ProfileAvatar(state.nurse)
                NurseForm(nurse = state.nurse, nurseViewModel = nurseViewModel, onSave = {
                        updatedNurse ->
                            state.nurse.id?.let { id ->
                            nurseViewModel.updateNurse(id, updatedNurse)
                        }
                    }
                )
            }

        }
    }

    if (showDialog) {
        DeleteConfirmationDialog(
            onConfirm = {
                val nurseId = (getNurseUiState as? GetNurseUiState.Success)?.nurse?.id
                nurseId?.let { id ->
                    nurseViewModel.deleteNurse(id)
                }
                showDialog = false
            },
            onDismiss = { showDialog = false }
        )
    }
}

@Composable
fun NurseForm(
    modifier: Modifier = Modifier,
    nurseViewModel: NurseViewModel,
    onSave: (Nurse) -> Unit,
    nurse: Nurse
) {
    val context = LocalContext.current
    var isEditable by remember { mutableStateOf(false) }

    var name by remember { mutableStateOf(nurse.name) }
    var surname by remember { mutableStateOf(nurse.surname) }
    var email by remember { mutableStateOf(nurse.email) }
    var user by remember { mutableStateOf(nurse.user) }
    var pass by remember { mutableStateOf(nurse.pass) }


    val updateState = nurseViewModel._updateNurseState
    val successMessage = stringResource(R.string.toat_msg_succes)
    val errorMessage = stringResource(R.string.toat_msg_error)

    LaunchedEffect(updateState) {
        when (updateState) {
            is UpdateNurseUiState.Success -> {
                android.widget.Toast.makeText(
                    context,
                    successMessage,
                    android.widget.Toast.LENGTH_SHORT
                ).show()

                delay(2000)
                nurseViewModel.clearUpdateState()
            }
            is UpdateNurseUiState.Error -> {
                android.widget.Toast.makeText(
                    context,
                    errorMessage,
                    android.widget.Toast.LENGTH_SHORT
                ).show()
                delay(2000)
                nurseViewModel.clearUpdateState()
            }
            else -> {}
        }
    }

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

        val isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val areFieldsNotEmpty = name.isNotBlank() && surname.isNotBlank() && user.isNotBlank() && pass.isNotBlank()
        val canSave = isEmailValid && areFieldsNotEmpty

        OutlinedTextField(
            value = name.toString(),
            onValueChange = { name = it },
            label = { Text(stringResource(R.string.label_name)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = isEditable,
            isError = name.isBlank() && isEditable
        )

        OutlinedTextField(
            value = surname,
            onValueChange = { surname = it },
            label = { Text(stringResource(R.string.label_surname)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = isEditable,
            isError = surname.isBlank() && isEditable
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(stringResource(R.string.label_email)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = isEditable,
            isError = !isEmailValid && email.isNotEmpty() && isEditable,
        )

        OutlinedTextField(
            value = user,
            onValueChange = { user = it },
            label = { Text(stringResource(R.string.label_user)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = isEditable,
            isError = user.isBlank() && isEditable
        )

        OutlinedTextField(
            value = pass,
            onValueChange = { pass = it },
            label = { Text(stringResource(R.string.label_password)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = isEditable,
            isError = pass.isBlank() && isEditable,
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
                    onSave(nurse.copy(name = name, surname = surname, email = email, user = user, pass = pass))
                    isEditable = false
                },
                enabled = canSave && updateState !is UpdateNurseUiState.Loading,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 25.dp)
            ) {
                if (updateState is UpdateNurseUiState.Loading) {
                    CircularProgressIndicator(modifier = Modifier.size(16.dp), color = Color.White)
                } else {
                    Text(stringResource(R.string.btn_save_changes))
                }
            }
        }
    }
}

@Composable
fun DeleteAccountButton(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.End
    ) {
        IconButton(onClick = onClick) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Eliminar cuenta",
                tint = Color.Red,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Composable
fun DeleteConfirmationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Confirmar eliminación", fontWeight = FontWeight.Bold)
        },
        text = {
            Text(text = "¿Estás seguro de que quieres eliminar este usuario? Esta acción no se puede deshacer.")
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text("Eliminar", color = Color.White)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
