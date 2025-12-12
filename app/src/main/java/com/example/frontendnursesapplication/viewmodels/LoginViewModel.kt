package com.example.frontendnursesapplication.viewmodels

import androidx.compose.ui.res.stringResource
import com.example.frontendnursesapplication.R
import androidx.lifecycle.ViewModel
import com.example.frontendnursesapplication.entities.LoginUiState
import com.example.frontendnursesapplication.entities.Nurse
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel: ViewModel() {

    private val _loginState = MutableStateFlow(LoginUiState())
    val loginState: StateFlow<LoginUiState> get() = _loginState.asStateFlow()

    init {
        _loginState.value = LoginUiState("","")
    }

    private fun getHardcodedNurses() = listOf(
        Nurse("Juan", "Perez", "juan@mail.com", "juan123", "1234"),
        Nurse("Pepe", "Lopez", "pepe@mail.com", "pepe45", "abcd"),
        Nurse("Maria", "Gomez", "mariag@mail.com", "mariag", "pass"),
    )


    fun onEmailChange(email: String){
        _loginState.update { it.copy(email = email) }
    }

    fun onPasswordChange(password: String){

        _loginState.update { it.copy(password = password) }
    }

    fun login(){
        val nurses = getHardcodedNurses()
        val email = loginState.value.email
        val password = loginState.value.password

        val nurse = nurses.find { it.email == email && it.password == password }

        if (nurse != null){
            _loginState.update { it.copy(errorMessage = false, success = true) }
        }
        else{
            _loginState.update { it.copy(errorMessage = true) }
        }
    }
}