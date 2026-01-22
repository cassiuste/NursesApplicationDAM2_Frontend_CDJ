package com.example.frontendnursesapplication.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontendnursesapplication.entities.LoginUiState
import com.example.frontendnursesapplication.entities.Nurse
import com.example.frontendnursesapplication.entities.NurseUiState
import com.example.frontendnursesapplication.entities.RegisterUiState
import com.example.frontendnursesapplication.network.RetrofitClient
import com.example.frontendnursesapplication.repository.NurseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NurseViewModel: ViewModel() {

    private val repository = NurseRepository(RetrofitClient.instance)

    
    private val _uiState = MutableStateFlow(NurseUiState())
    val uiState: StateFlow<NurseUiState> get() = _uiState.asStateFlow()

    
    private val _findByNameState = MutableStateFlow(NurseUiState())
    val findByNameState: StateFlow<NurseUiState> get() = _findByNameState.asStateFlow()

    
    private val _loginState = MutableStateFlow(LoginUiState())
    val loginState: StateFlow<LoginUiState> get() = _loginState.asStateFlow()

    
    private val _registerState = MutableStateFlow(RegisterUiState())
    val registerState: StateFlow<RegisterUiState> get() = _registerState.asStateFlow()

    //init
    init {
        _uiState.value = NurseUiState(nurses = emptyList())
        _findByNameState.value = NurseUiState(nurses = emptyList())
        _loginState.value = LoginUiState("", "")
        _registerState.value = RegisterUiState()

    }

    fun getAllNurses(): List<Nurse> {
        viewModelScope.launch {
            try {
                val response = repository.getNurses()
                _uiState.update { it.copy(nurses = response) }
            } catch (e: Exception) {
                Log.d("example", "response ERROR ${e.message} ${e.printStackTrace()}")
            }
        }
        return _uiState.value.nurses
    }

    fun findByName(name: String) {
        if (name.trim().isEmpty()) {
            _findByNameState.update {
                it.copy(
                    nurses = emptyList(),
                    error = null
                )
            }
        }

        val nurses = _uiState.value.nurses

        val results = nurses.filter { nurse ->
            nurse.name.contains(name.trim(), ignoreCase = true)
        }

        _findByNameState.update {
            it.copy(
                nurses = results,
                error = if (results.isEmpty()) "No se ha encontrado ningun Nurse con el nombre \"${name.trim()}\"" else null
            )
        }
    }

    fun clearResults() {
        _findByNameState.update { it.copy(nurses = emptyList(), error = null) }
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

                    if (response.isSuccessful) {

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







