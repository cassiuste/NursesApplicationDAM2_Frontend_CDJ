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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.core.content.ContextCompat
import com.example.frontendnursesapplication.components.ProfileTopBar
import com.example.frontendnursesapplication.components.TopBar
import com.example.frontendnursesapplication.entities.Nurse
import com.example.frontendnursesapplication.viewmodels.NurseViewModel
import kotlinx.coroutines.delay

@Composable
fun ProfileView(navController: NavController, nurseViewModel: NurseViewModel) {

    Column() {
        ProfileTopBar(onBack = {navController.popBackStack()})

        Spacer(modifier = Modifier.height(20.dp))

        NurseForm(onSave = {
            navController.navigate("listAll")
        })
    }
}

@Composable
fun NurseForm(
    modifier: Modifier = Modifier,
    onSave: (Nurse) -> Unit
) {
    var isEditable by remember { mutableStateOf(false) }

    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var user by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }

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

        OutlinedTextField(
            value = name,
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
            enabled = isEditable
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

