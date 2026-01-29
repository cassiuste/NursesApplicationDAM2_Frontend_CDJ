package com.example.frontendnursesapplication.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontendnursesapplication.entities.FindNameUiState
import com.example.frontendnursesapplication.entities.LoginUiState
import com.example.frontendnursesapplication.entities.Nurse
import com.example.frontendnursesapplication.entities.NurseUiState
import com.example.frontendnursesapplication.entities.RegisterUiState
import com.example.frontendnursesapplication.entities.SessionUiState
import com.example.frontendnursesapplication.network.RetrofitClient
import com.example.frontendnursesapplication.repository.NurseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NurseViewModel: ViewModel() {

    private val repository = NurseRepository(RetrofitClient.instance)

    private val _sessionState = MutableStateFlow(SessionUiState())
    val sessionState: StateFlow<SessionUiState> = _sessionState.asStateFlow()

    private val _uiState = MutableStateFlow(NurseUiState())
    val uiState: StateFlow<NurseUiState> get() = _uiState.asStateFlow()


    var _findByNameState: FindNameUiState
            by mutableStateOf(FindNameUiState.Idle)
        private set



    private val _loginState = MutableStateFlow(LoginUiState())
    val loginState: StateFlow<LoginUiState> get() = _loginState.asStateFlow()

    
    private val _registerState = MutableStateFlow(RegisterUiState())
    val registerState: StateFlow<RegisterUiState> get() = _registerState.asStateFlow()

    init {
        _uiState.value = NurseUiState(nurses = emptyList())
        _loginState.value = LoginUiState("", "")
        _registerState.value = RegisterUiState()

    }

    fun getAllNurses(){
        viewModelScope.launch {
            try {
                val response = repository.getAll()
                _uiState.value = _uiState.value.copy(
                    nurses = response,
                    error = null
                )
            } catch (e: Exception) {
                Log.d("example", "response ERROR ${e.message} ${e.printStackTrace()}")
                _uiState.value = _uiState.value.copy(
                    error = "Error"
                )
            }
        }
    }

    fun findByName(name: String) {
        viewModelScope.launch {
            _findByNameState = FindNameUiState.Loading
            try {
                val response = repository.findbyname(name)

                _findByNameState = if (response.isSuccessful) {
                    val nurse = response.body()
                    if (nurse != null)
                        FindNameUiState.Success(nurse)
                    else
                        FindNameUiState.NotFound
                } else {
                    FindNameUiState.Error
                }

            } catch (e: Exception) {
                _findByNameState = FindNameUiState.Error
            }
        }
    }


    fun clearResults() {
        _findByNameState = FindNameUiState.Idle
    }

    fun onUserChange(user: String){
        _loginState.update { it.copy(user = user) }
    }

    fun onPasswordChange(password: String) {

        _loginState.update { it.copy(password = password) }
    }

    fun login(){
        viewModelScope.launch {
            try{
                val user = loginState.value.user
                val password = loginState.value.password
                val nurse = Nurse(name="", user = user, pass = password, email = "", surname = "")
                val response = repository.login(nurse)
                Log.d("example", "Login Correcto ${response.user}")

                _sessionState.value = SessionUiState(
                    nurse = response,
                    isLogged = true
                )

                _loginState.update {
                    it.copy(
                        success = true,
                        errorMessage = false
                    )
                }

            } catch (e: Exception) {
                Log.d("example", "ERROR in Login ${e.message} ${e.printStackTrace()}")
                _loginState.update {
                    it.copy(
                        errorMessage = true,
                        success = false
                    )
                }
            }
        }
    }



        fun register(nurse: Nurse) {
            viewModelScope.launch {
                try {
                    val response = repository.registerNurse(nurse)

                    if (response.isSuccessful && response.body() != null) {

                        _sessionState.value = SessionUiState(
                            nurse = response.body(),
                            isLogged = true
                        )

                        _registerState.value = RegisterUiState(success = true)

                        Log.d("REGISTER", "Nurse registrada correctamente")
                    } else {

                        _registerState.value = RegisterUiState(
                            error = "Error ${response.code()}"
                        )
                    }

                } catch (e: Exception) {
                    Log.e("REGISTER", "Excepción: ${e.message}")
                }
            }
        }
    }







