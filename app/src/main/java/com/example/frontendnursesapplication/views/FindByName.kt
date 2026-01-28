package com.example.frontendnursesapplication.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.frontendnursesapplication.R
import com.example.frontendnursesapplication.components.TopBar
import com.example.frontendnursesapplication.entities.Nurse
import com.example.frontendnursesapplication.viewmodels.NurseViewModel

@Composable
fun FindByName(navController: NavController, nurseViewModel: NurseViewModel){
    val findByNameState by nurseViewModel.findByNameState.collectAsState()

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


        Column(
            modifier = Modifier.padding(20.dp, 10.dp)
        ) {
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


            findByNameState.error?.let { errorMsg ->
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = errorMsg,
                        color = colorResource(R.color.redstucom),
                        modifier = Modifier.padding(top = 10.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }

            Column (modifier = Modifier.weight(1f).padding(top = 20.dp)){
                if (findByNameState.nurses.isNotEmpty()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(colorResource(R.color.pinkstucom).copy(alpha = 0.15f))
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Name", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                        Text("Surname", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                        Text("User", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                        Text("Email", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                    }
                    LazyColumn(modifier = Modifier
                        .weight(1f)
                        .padding(5.dp))
                    {
                        items(findByNameState.nurses){ nurse ->
                            PrintNurse(nurse)
                        }
                    }

                    Button(
                        onClick = { nurseViewModel.clearResults() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.pinkstucom),
                            contentColor = colorResource(R.color.whitestucom)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, start = 8.dp, end = 8.dp)
                    ) {
                        Text(stringResource(R.string.clear_response))
                    }
                }
            }
            HomeButton(navController)
        }
    }
}

@Composable
fun PrintNurse(nurse: Nurse){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(nurse.name, modifier = Modifier.weight(1f))
        Text(nurse.surname, modifier = Modifier.weight(1f))
        Text(nurse.user, modifier = Modifier.weight(1f))
        Text(nurse.email, modifier = Modifier.weight(1f))
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