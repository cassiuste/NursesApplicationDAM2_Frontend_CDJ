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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.frontendnursesapplication.R
import com.example.frontendnursesapplication.entities.Nurse

@Composable
fun FindByName(navController: NavController){
    // Lista de nurses que actuaria como el backend
    val nurses = listOf(
        Nurse("Juan", "Perez", "juan@mail.com", "juan123", "1234"),
        Nurse("Pepe", "Lopez", "pepe@mail.com", "pepe45", "abcd"),
        Nurse("Maria", "Gomez", "maria@mail.com", "mariag", "pass")
    )


    var search by remember { mutableStateOf("") };
    val result = remember {mutableStateListOf<Nurse>()};
    var notFound by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp, end = 20.dp, start = 20.dp, bottom = 20.dp)
    ) {

        TopSection()

        Spacer(modifier = Modifier.height(33.dp))

        Row(horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.find_by_name),
                modifier = Modifier.padding(bottom = 16.dp, end = 10.dp),
                style = MaterialTheme.typography.headlineSmall
            )
            Image(painterResource(id = R.drawable.enfermero),
                contentDescription = null,
                modifier = Modifier.size(45.dp))
        }

        Spacer(modifier = Modifier.height(20.dp))

        // TextField donde el usuario escribe un nombre
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
                    if (search.isNotBlank()) {
                        // Se limpia la lista antes de seleccionarla
                        result.clear()
                        // Se itera sobre la lista de nurses para verificarla si el nombre es igual
                        val foundNurses = nurses.filter { it.name.equals(search, ignoreCase = true) }

                        if (foundNurses.isNotEmpty()) {
                            result.addAll(foundNurses)
                            notFound = false
                        } else {
                            notFound = true
                        }

                        // Se reinicia el buscador
                        search = ""
                    }
                }
            ),
            modifier = Modifier.fillMaxWidth()
        )
        if (notFound) {
            Text(
                text = stringResource(R.string.could_not_found_nurse),
                color = colorResource(id = R.color.redstucom),
                modifier = Modifier.padding(top = 12.dp)
            )
        }
        // Imprime los nurses encontrados
        LazyColumn(modifier = Modifier
            .weight(1f)
            .padding(8.dp))
        {
            items(result){ nurse ->
                PrintNurse(nurse)
                Button(onClick = {
                    result.clear()
                },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.pinkstucom),
                        contentColor = colorResource(id = R.color.whitestucom)
                    ),
                    modifier = Modifier.padding(top = 12.dp)
                )
                {
                    Text(text = stringResource(R.string.clear_response))
                }
            }
        }
        HomeButton(navController)
    }
}

@Composable
fun PrintNurse(nurse: Nurse){
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Row(modifier = Modifier.padding(10.dp)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = stringResource(R.string.Name));
            Text(text = stringResource(R.string.Surname));
            Text(text = stringResource(R.string.User));
            Text(text = stringResource(R.string.Email));
        }
        Row(modifier = Modifier.padding(10.dp)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            Text(text = nurse.name);
            Text(text = nurse.surname);
            Text(text = nurse.user);
            Text(text = nurse.email);
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

@Preview(showBackground = true)
@Composable
fun FindByNamePreview() {
  // FindByName()
}