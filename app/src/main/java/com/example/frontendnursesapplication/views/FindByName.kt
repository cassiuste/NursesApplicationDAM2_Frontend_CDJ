package com.example.frontendnursesapplication.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.frontendnursesapplication.R
import com.example.frontendnursesapplication.components.TopBar
import com.example.frontendnursesapplication.entities.FindByNameUiSate
import com.example.frontendnursesapplication.entities.Nurse
import com.example.frontendnursesapplication.viewmodels.NurseViewModel

@Composable
fun FindByName(navController: NavController, nurseViewModel: NurseViewModel){
    val FinfByName = nurseViewModel._findByNameState
    var search by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        ) {

        TopBar(onBack = {
            navController.navigate("Profile")
        })

        Spacer(modifier = Modifier.height(33.dp))

        Row(horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.find_by_name),
                fontWeight = FontWeight.Bold, fontSize = 25.sp,
                modifier = Modifier.padding(bottom = 16.dp, end = 10.dp),
                style = MaterialTheme.typography.headlineSmall
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Column(modifier = Modifier.padding(20.dp),) {
            TextField(
                value = search,
                onValueChange = { search = it },
                label = { Text(
                    stringResource(R.string.write_name)
                ) },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (search.trim().isNotEmpty()) {
                            nurseViewModel.findByName(search)
                        }
                    }
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Column(modifier = Modifier.padding(top = 20.dp).fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,) {
                when (FinfByName) {
                    is FindByNameUiSate.Idle -> {
                        Text(stringResource(R.string.search_by_name_info),
                            textAlign = TextAlign.Center)
                    }

                    is FindByNameUiSate.Loading -> {
                        CircularProgressIndicator()
                    }

                    is FindByNameUiSate.Error -> {
                        Text(
                            stringResource(R.string.generic_error),
                            color = colorResource(R.color.redstucom)
                        )
                    }

                    is FindByNameUiSate.NotFound -> {
                        Text(
                            stringResource(R.string.not_found_error),
                            color = colorResource(R.color.redstucom)
                        )
                    }

                    is FindByNameUiSate.Success -> {
                        val nurse =
                            (FinfByName as FindByNameUiSate.Success).nurse

                        Column {
                            Spacer(modifier = Modifier.height(10.dp))
                            NurseCard(nurse)

                            Button(
                                onClick = { nurseViewModel.clearResults() },
                                modifier = Modifier.fillMaxWidth().padding(top = 20.dp)
                            ) {
                                Text(stringResource(R.string.clear_response))
                            }
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        HomeButton(navController)
    }
}

@Composable
fun NurseCard(nurse: Nurse) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.white))
    ) {

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${nurse.name} ${nurse.surname}",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium,
                    color = colorResource(R.color.black)

                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {


                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = null,
                            tint = colorResource(R.color.purple_500),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = nurse.email,
                            style = MaterialTheme.typography.bodyMedium,
                            color = colorResource(R.color.blue_gray)
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = Color(0xFF6200EE),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = nurse.user,
                            style = MaterialTheme.typography.bodyMedium,
                            color = colorResource(R.color.blue_gray)
                        )
                    }
                }
                Image(
                    painter = painterResource(id = R.drawable.equipomedico),
                    contentDescription = null,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                )
            }
        }
    }
}

@Composable
fun HomeButton(navController: NavController){
    Button(onClick = {
        navController.popBackStack()
    },
        modifier = Modifier.fillMaxWidth().padding(20.dp).padding(bottom = 20.dp)) {
        Text(text = stringResource(R.string.button_info_return_home))

    }
}
