package com.example.frontendnursesapplication.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.frontendnursesapplication.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.frontendnursesapplication.entities.Nurse
import com.example.frontendnursesapplication.ui.theme.Rubik

@Composable
fun AllNursesView (navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        TopSection()

        Text(
            text = stringResource(R.string.title_allnurses_view),
            fontWeight = FontWeight.Bold, fontSize = 25.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.statusBarsPadding().padding(top = 20.dp),
            color = colorResource(R.color.black)
        )

        Spacer(modifier = Modifier.height(20.dp))

        NursesTable(modifier = Modifier.weight(1f))

        Button(onClick = {
            navController.popBackStack()
        },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.bluestucom),
                contentColor = colorResource(id = R.color.whitestucom)),
            modifier = Modifier.fillMaxWidth().padding(20.dp).padding(bottom = 20.dp)) {

            Text(text = stringResource(R.string.button_info_return_home))
        }
    }
}

@Composable
fun NursesTable(modifier: Modifier) {
    var nurses by remember { mutableStateOf(getHardcodedNurses()) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(20.dp)
    ) {
        items(nurses) { nurse ->
            NurseCard(nurse = nurse)
        }
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
    }
}

private fun getHardcodedNurses() = listOf(
    Nurse("Juan", "Perez", "juan@mail.com", "juan123", "1234"),
    Nurse("Pepe", "Lopez", "pepe@mail.com", "pepe45", "abcd"),
    Nurse("Maria", "Gomez", "maria@mail.com", "mariag", "pass"),
    Nurse("Maria", "Gomez", "maria@mail.com", "mariag", "pass"),
    Nurse("Maria", "Gomez", "maria@mail.com", "mariag", "pass"),
    Nurse("Maria", "Gomez", "maria@mail.com", "mariag", "pass"),
    Nurse("Maria", "Gomez", "maria@mail.com", "mariag", "pass"),
)