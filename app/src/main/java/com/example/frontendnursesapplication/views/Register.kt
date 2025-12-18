package com.example.frontendnursesapplication.views

import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.composable
import com.example.frontendnursesapplication.R
import com.example.frontendnursesapplication.entities.Nurse
import com.example.frontendnursesapplication.viewmodels.NurseViewModel
import kotlinx.coroutines.delay

@Composable
fun RegisterScreen(
    navController: NavController,
    nurseViewModel: NurseViewModel
) {
    Surface() {
        Column(modifier = Modifier.fillMaxSize()) {

            TopSection()
            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = stringResource(id = R.string.CreateAccount),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(start = 60.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 60.dp)
            ) {
                RegisterSection(navController,nurseViewModel)
            }
        }
    }
}

@Composable
fun RegisterSection(navController: NavController,
                    nurseViewModel: NurseViewModel) {

    val context = LocalContext.current
    val registerState = nurseViewModel.registerState.collectAsState().value

    val nameState = remember { mutableStateOf("") }
    val surnameState = remember { mutableStateOf("") }
    val emailState = remember { mutableStateOf("") }
    val userState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }

    val bluegray = colorResource(id = R.color.blue_gray)

    Column {

        LoginTextField(label = stringResource(id = R.string.Name), trailing = "", textState = nameState)
        LoginTextField(label = stringResource(id = R.string.Surname), trailing = "", textState = surnameState)
        LoginTextField(label = stringResource(id = R.string.Email), trailing = "", textState = emailState)
        LoginTextField(label = stringResource(id = R.string.User), trailing = "", textState = userState)
        LoginTextField(
            label = stringResource(id = R.string.Password),
            trailing = "",
            isPassword = true,
            textState = passwordState
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            onClick = {
                nurseViewModel.register(
                    Nurse(
                        name = nameState.value,
                        surname = surnameState.value,
                        email = emailState.value,
                        user = userState.value,
                        password = passwordState.value
                    )
                )
            },
            shape = RoundedCornerShape(4.dp)
        ) {
            Text(stringResource(id = R.string.register),
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Companion.Medium))
        }

        Spacer(Modifier.padding(top = 20.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            onClick = {
                navController.navigate("login") {}
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isSystemInDarkTheme()) bluegray else Color.Companion.Black,
                contentColor = Color.Companion.White
            ),
            shape = RoundedCornerShape(4.dp)
        ) {
            Text(stringResource(id = R.string.SignIn),
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Companion.Medium))
        }

        if (registerState.success) {
            Toast.makeText(context, stringResource(id = R.string.NurseCreated), Toast.LENGTH_SHORT).show()

            LaunchedEffect(Unit) {
                delay(3000)
                navController.navigate("home") {
                    popUpTo("register") { inclusive = true }
                }
            }
        }
        if (registerState.error) {
            Toast.makeText(context, stringResource(id = R.string.EmailExists), Toast.LENGTH_SHORT).show()
        }
    }
}

