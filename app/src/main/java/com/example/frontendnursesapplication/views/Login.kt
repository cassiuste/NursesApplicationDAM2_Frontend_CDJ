package com.example.frontendnursesapplication.views

import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.frontendnursesapplication.R
import com.example.frontendnursesapplication.entities.Nurse
import com.example.frontendnursesapplication.ui.theme.Rubik
import com.example.frontendnursesapplication.viewmodels.LoginViewModel


@Composable
fun LoginScreen(navController: NavController,
                loginViewModel: LoginViewModel = LoginViewModel()) {

    Surface {
        Column(
            modifier = Modifier.Companion.fillMaxSize()
        ) {

            TopSection()
            Spacer(modifier = Modifier.Companion.height(100.dp))

            titleLogin()

            Spacer(modifier = Modifier.Companion.height(20.dp))

            Column(
                modifier = Modifier.Companion
                    .fillMaxSize()
                    .padding(horizontal = 60.dp)
            ) {
                LoginSection(loginViewModel)
                Spacer(modifier = Modifier.Companion.height(100.dp))

                Column(horizontalAlignment = Alignment.Companion.CenterHorizontally) {
                    Text(
                        text = "Or continue with",
                        style = MaterialTheme.typography.labelMedium.copy(color = Color(0xFF64748B))
                    )
                    Spacer(modifier = Modifier.Companion.height(10.dp))
                    Row(
                        modifier = Modifier.Companion.fillMaxWidth(),
                        verticalAlignment = Alignment.Companion.CenterVertically
                    ) {
                        SocialMediaLogin(
                            icon = R.drawable.google,
                            text = "Google",
                            modifier = Modifier.Companion.weight(1f)
                        ) {

                        }
                        Spacer(modifier = Modifier.Companion.width(20.dp))

                        SocialMediaLogin(
                            icon = R.drawable.facebook,
                            text = "Facebook",
                            modifier = Modifier.Companion.weight(1f)
                        ) {
                        }

                    }
                    HomeButton(navController)
                }

            }


        }

    }
}

@Composable
fun titleLogin() {

    val uiColor = if (isSystemInDarkTheme()) Color.Companion.White else Color.Companion.Black
    val title1 = stringResource(id = R.string.login_title1)
    val title2 = "your "
    val account = stringResource(id = R.string.onlyaccount)
    val signUpText = stringResource(id = R.string.account)
    val signUp = stringResource(id = R.string.SignUp)
    val softRed = colorResource(id = R.color.soft_red)


    Row(
        verticalAlignment = Alignment.Companion.CenterVertically,
        modifier = Modifier.Companion.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.Companion.wrapContentWidth()
        ) {

            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Companion.SemiBold)) {
                        append(title1)
                    }
                },
                fontSize = 27.sp,
                style = MaterialTheme.typography.headlineMedium,
                color = uiColor,
                modifier = Modifier.Companion.padding(start = 60.dp)
            )

            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Companion.SemiBold,
                            fontSize = 25.sp)) {
                        append(title2)
                    }
                    withStyle(
                        style = SpanStyle(
                            color = softRed,
                            fontWeight = FontWeight.Companion.SemiBold,
                            fontSize = 27.sp
                        )
                    ) {
                        append(account)
                    }
                },
                style = MaterialTheme.typography.headlineMedium,
                color = uiColor,
                modifier = Modifier.Companion.padding(start = 35.dp)
            )

            Text(
                text = buildAnnotatedString {
                    append(signUpText)
                    withStyle(
                        style = SpanStyle(
                            color = Color.Companion.Blue,
                            fontWeight = FontWeight.Companion.SemiBold
                        )
                    ) {  // azul y sin negrita
                        append(signUp)
                    }
                },
                style = MaterialTheme.typography.bodySmall,  // más pequeño
                color = uiColor,
                modifier = Modifier.Companion.padding(start = 35.dp, top = 8.dp)
            )
        }

        Image(
            painter = painterResource(id = R.drawable.icono_hospital),
            contentDescription = stringResource(id = R.string.app_logo),
            contentScale = ContentScale.Companion.Fit,
            modifier = Modifier.Companion
                .width(130.dp)
                .padding(start = 50.dp)  // separación mínima
        )
    }




}



@Composable
fun LoginSection(loginViewModel: LoginViewModel){

    val uiState = loginViewModel.loginState.collectAsState().value
    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }

    val context = LocalContext.current
    val forgot = stringResource(id = R.string.forgot)
    val softRed = colorResource(id = R.color.soft_red)
    val bluegray = colorResource(id = R.color.blue_gray)

    val correctoText = stringResource(id = R.string.correcto)
    val email = stringResource(id = R.string.Email)
    val password = stringResource(id = R.string.Password)


    Column() {
        LoginTextField(
            label = email,
            trailing = "",
            textState = emailState
        )

        Spacer(modifier = Modifier.Companion.height(10.dp))

        LoginTextField(
            label = password,
            trailing = "",
            isPassword = true,
            textState = passwordState
        )

        Spacer(modifier = Modifier.Companion.height(10.dp))

        Text(
            text = forgot,
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Companion.SemiBold),
            color = softRed,
            modifier = Modifier.Companion
                .align(Alignment.Companion.CenterHorizontally)
                .padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.Companion.height(15.dp))

        Button(
            modifier = Modifier.Companion
                .fillMaxWidth()
                .height(40.dp),
            onClick = {
                loginViewModel.onEmailChange(emailState.value)
                loginViewModel.onPasswordChange(passwordState.value)
                loginViewModel.login()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isSystemInDarkTheme()) bluegray else Color.Companion.Black,
                contentColor = Color.Companion.White
            ),
            shape = RoundedCornerShape(size = 4.dp)

        ) {
            Text(
                text = "Log in",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Companion.Medium)
            )
        }

        if (uiState.errorMessage) {
            Toast.makeText(context, stringResource(R.string.incorrecto), Toast.LENGTH_SHORT).show()
        }

        if (uiState.success) {
            Toast.makeText(context, stringResource(R.string.correcto), Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun SocialMediaLogin(
    @DrawableRes icon: Int,
    modifier: Modifier = Modifier.Companion,
    text: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .socialMedia()
            .clip(androidx.compose.foundation.shape.RoundedCornerShape(4.dp))
            .clickable { onClick() }
            .height(40.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Companion.CenterVertically,

        ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.Companion.size(20.dp)
        )
        Spacer(modifier = Modifier.Companion.width(16.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium.copy(color = Color.Companion.Black)
        )
    }
}


fun Modifier.socialMedia(): Modifier = composed {
    val bluegray = colorResource(id = R.color.blue_gray)
    val lightbluewhite = colorResource(id = R.color.light_blue_white)

    if (isSystemInDarkTheme()){
        background(Color.Companion.Transparent).border(
            width = 1.dp,
            color = bluegray,
            shape = androidx.compose.foundation.shape.RoundedCornerShape(4.dp)
        )
    }else{
        background(lightbluewhite)

    }
}

@Composable
fun TopSection() {
           val uiColor =
                if (isSystemInDarkTheme()) Color.Companion.White else Color.Companion.Black //Para que tu texto, icono o logo siempre tenga buen contraste dependiendo del tema.

    Box(
        modifier = Modifier.Companion.fillMaxWidth(),
        contentAlignment = Alignment.Companion.TopCenter
    ) {
        Column(
            modifier = Modifier.Companion.padding(top = 80.dp),
            horizontalAlignment = Alignment.Companion.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = stringResource(id = R.string.app_logo),
                modifier = Modifier.Companion.size(42.dp)
            )
            Spacer(modifier = Modifier.Companion.width(20.dp))
            Column (
                modifier = Modifier.padding(start = 5.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.hospital),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Companion.Bold,
                    fontFamily = Rubik,
                    color = uiColor
                )
            }
            Column (
                modifier = Modifier.padding(start = 5.dp)
            ){

                Text(
                    text = stringResource(id = R.string.slogan),
                    style = MaterialTheme.typography.titleMedium,
                    color = uiColor
                )
            }

        }
    }
}

@Composable
fun LoginTextField(
    modifier: Modifier = Modifier.Companion,
    label: String,
    trailing: String,
    isPassword: Boolean = false,
    textState: MutableState<String>
) {
    val uiColor = if (isSystemInDarkTheme()) Color.Companion.White else Color.Companion.Black
    var passwordVisible by remember { mutableStateOf(false) }

    TextField(
        value = textState.value,
        onValueChange = { newText -> textState.value = newText },
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = uiColor
            )
        },
        visualTransformation = if (isPassword && !passwordVisible) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.Companion.None
        },

        trailingIcon = {
            if (isPassword) {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        painter = painterResource(
                            id = if (passwordVisible) R.drawable.ic_visibility
                            else R.drawable.ic_visibility_off
                        ),
                        contentDescription = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña",
                        tint = uiColor.copy(alpha = 0.6f),
                        modifier = Modifier.Companion.size(18.dp)
                    )
                }
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
}


