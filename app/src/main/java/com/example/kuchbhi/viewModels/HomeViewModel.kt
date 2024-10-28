package com.example.kuchbhi.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(): ViewModel() {
    var from by mutableStateOf("")
    val clients = mutableStateListOf<String>()
    var regex by mutableStateOf("")
    var baseUrl by mutableStateOf("")
    var mode by mutableStateOf("or") // State for "or" or "and"
    var errorMessage by mutableStateOf("") // For error message


    fun validateInput() {
        errorMessage = if (clients.isEmpty() && regex.isBlank()) {
            "Field cannot be empty"
        } else {
            ""
        }
    }
}
