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


    //Registro_State
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
            return
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

    fun onEmailChange(email: String) {
        _loginState.update { it.copy(email = email) }
    }

    fun onPasswordChange(password: String) {

        _loginState.update { it.copy(password = password) }
    }

    fun login() {
        val nurses = _uiState.value.nurses
        val email = loginState.value.email
        val password = loginState.value.password

        val nurse = nurses.find { it.email == email && it.pass == password }

        if (nurse != null) {
            _loginState.update { it.copy(errorMessage = false, success = true) }
        } else {
            _loginState.update { it.copy(errorMessage = true) }
        }
    }



    //Actualizar nurse
    fun updateNurse(id: Long, nurse: Nurse) {
        viewModelScope.launch {
            try {
                val response = repository.updateNurse(id, nurse)

                if (response.isSuccessful) {
                    Log.d("PUT", "Nurse actualizada correctamente")
                } else {
                    Log.e("PUT", "Error al actualizar: ${response.code()}")
                }

            } catch (e: Exception) {
                Log.e("PUT", "Excepción: ${e.message}")
            }
        }
    }



    //Registrar
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







