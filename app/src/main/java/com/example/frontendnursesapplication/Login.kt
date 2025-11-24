package com.example.frontendnursesapplication

import android.R.attr.text
import android.content.Context
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
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
import com.example.frontendnursesapplication.ui.theme.SoftRed
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.frontendnursesapplication.ui.theme.BlueGray
import com.example.frontendnursesapplication.ui.theme.Rubik
import com.example.frontendnursesapplication.ui.theme.focusedTextFieldText
import com.example.frontendnursesapplication.ui.theme.textFieldContainer
import com.example.frontendnursesapplication.ui.theme.unfocusedTextFieldText
import androidx.compose.foundation.background
import androidx.compose.foundation.border

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.frontendnursesapplication.ui.theme.LightBlueWhite


@Composable
fun LoginScreen() {

    Surface {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopSection()
            Spacer(modifier = Modifier.height(100.dp))

            titleLogin()

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 60.dp)
            ) {
                LoginSection()
                Spacer(modifier = Modifier.height(120.dp))

                Column(horizontalAlignment =  Alignment.CenterHorizontally) {
                    Text(text = "Or continue with",
                        style = MaterialTheme.typography.labelMedium.copy(color = Color(0xFF64748B))
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        SocialMediaLogin(
                            icon = R.drawable.google,
                            text = "Google",
                            modifier = Modifier.weight(1f)
                        ) {

                        }
                        Spacer(modifier = Modifier.width(20.dp))

                        SocialMediaLogin(
                            icon = R.drawable.facebook,
                            text = "Facebook",
                            modifier = Modifier.weight(1f)
                        ) {
                        }
                    }
                }

            }


        }

    }
}
@Composable
fun titleLogin() {

    val uiColor = if (isSystemInDarkTheme()) Color.White else Black
    val title1 = stringResource(id = R.string.login_title1)
    val title2 = "your "
    val account = "account"
    val signUpText = "Don’t have an account? "
    val signUp = "Sign up"

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.wrapContentWidth()
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                        append(title1)
                    }
                },
                fontSize = 27.sp,
                style = MaterialTheme.typography.headlineMedium,
                color = uiColor,
                modifier = Modifier.padding(start = 60.dp)
            )

            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                        append(title2)
                    }
                    withStyle(style = SpanStyle(color = SoftRed, fontWeight = FontWeight.SemiBold)) {
                        append(account)
                    }
                },
                style = MaterialTheme.typography.headlineMedium,
                color = uiColor,
                modifier = Modifier.padding(start = 35.dp)
            )

            Text(
                text = buildAnnotatedString {
                    append(signUpText)
                    withStyle(style = SpanStyle(color = Color.Blue, fontWeight = FontWeight.SemiBold)) {  // azul y sin negrita
                        append(signUp)
                    }
                },
                style = MaterialTheme.typography.bodySmall,  // más pequeño
                color = uiColor,
                modifier = Modifier.padding(start = 35.dp, top = 8.dp)
            )
        }

        Image(
            painter = painterResource(id = R.drawable.icono_hospital),
            contentDescription = stringResource(id = R.string.app_logo),
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .width(130.dp)
                .padding(start = 50.dp)  // separación mínima
        )
        }




    }
@Composable
fun LoginSection(){

    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val context = LocalContext.current
    val forgot = stringResource(id = R.string.forgot)

    Column() {
        LoginTextField(label = "Email",
            trailing = "",
            textState = emailState
        )

        Spacer(modifier = Modifier.height(10.dp))

        LoginTextField(label = "Password",
            trailing = "",
            isPassword = true,
            textState = passwordState
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = forgot,
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
            color = SoftRed,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(15.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            onClick = {
                if (emailState.value.isEmpty() || passwordState.value.isEmpty()) {
                    Toast.makeText(context, "Email o contraseña vacíos", Toast.LENGTH_SHORT).show()
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isSystemInDarkTheme()) BlueGray else Black,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(size = 4.dp)

        ) {
            Text(
                text = "Log in",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium)
            )

        }
    }
}
@Composable
fun SocialMediaLogin(
    @DrawableRes icon: Int,
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .socialMedia()
            .clip(RoundedCornerShape(4.dp))
            .clickable { onClick() }
            .height(40.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,

    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = text, style = MaterialTheme.typography.labelMedium.copy(color = Black))
    }
}


fun Modifier.socialMedia(): Modifier = composed {
    if (isSystemInDarkTheme()){
        background(Color.Transparent).border(
            width = 1.dp,
            color = BlueGray,
            shape = RoundedCornerShape(4.dp)
        )
    }else{
        background(LightBlueWhite)

    }
}

@Composable
fun TopSection() {
           val uiColor =
                if (isSystemInDarkTheme()) Color.White else Black //Para que tu texto, icono o logo siempre tenga buen contraste dependiendo del tema.

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopCenter
            ) {
                /*Image(
                    modifier = Modifier.fillMaxWidth()
                        .fillMaxHeight(fraction = 0.80f),
                    painter = painterResource(id = R.drawable.shape2),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds
                )*/

                //Row
                Column(
                    modifier = Modifier.padding(top = 80.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = stringResource(id = R.string.app_logo),
                        modifier = Modifier.size(42.dp)
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                    Column {
                        Text(
                            text = stringResource(id = R.string.hospital),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = Rubik,
                            color = uiColor
                        )

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
    modifier: Modifier = Modifier,
    label: String,
    trailing: String,
    isPassword: Boolean = false,
    textState: MutableState<String>
) {
    val uiColor = if (isSystemInDarkTheme()) Color.White else Color.Black
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
            PasswordVisualTransformation()  // oculta contraseña
        } else {
            VisualTransformation.None      // muestra texto normal
        },
        colors = TextFieldDefaults.colors(
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.unfocusedTextFieldText,
            focusedPlaceholderColor = MaterialTheme.colorScheme.focusedTextFieldText,
            unfocusedContainerColor = MaterialTheme.colorScheme.textFieldContainer,
        ),
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
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
}



@Preview(showBackground = true)
@Composable
fun LoginPreview(){
    LoginScreen();

}

