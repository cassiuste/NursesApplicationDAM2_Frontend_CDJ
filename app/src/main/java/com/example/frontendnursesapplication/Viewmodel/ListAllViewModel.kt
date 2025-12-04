package com.example.frontendnursesapplication.Viewmodel

import androidx.lifecycle.ViewModel
import com.example.frontendnursesapplication.entities.Nurse
import com.example.frontendnursesapplication.entities.NurseUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class ListAllViewModel : ViewModel(){
    private val _uiState = MutableStateFlow(NurseUiState())
    // Evitem que sigui modificable desde fora el AppViewModel
    val uiState: StateFlow<NurseUiState> get()= _uiState.asStateFlow()
   // en el init inicialitzem els valors

    init {
        _uiState.value = NurseUiState(getHardcodedNurses())
    }
    private fun getHardcodedNurses() = listOf(
        Nurse("Juan", "Perez", "juan@mail.com", "juan123", "1234"),
        Nurse("Pepe", "Lopez", "pepe@mail.com", "pepe45", "abcd"),
        Nurse("Maria", "Gomez", "mariag", "mariag", "pass"),
        Nurse("Maria", "Gomez", "mariag", "mariag", "pass"),
        Nurse("Maria", "Gomez", "mariag", "mariag", "pass"),
        Nurse("Maria", "Gomez", "mariag", "mariag", "pass"),
        Nurse("Maria", "Gomez", "mariag", "mariag", "pass")
    )

    // actualitzem els valors creant una nova instancia de _uiState


    fun updateNurse(updatedNurse: Nurse) {
        _uiState.update { state ->
            state.copy(
                nurses = state.nurses.map { nurse ->
                    if (nurse.email == updatedNurse.email) updatedNurse else nurse
                }
            )
        }
    }


    fun getNurses(): List<Nurse> {
        return _uiState.value.nurses
    }


}
