package com.example.kuchbhi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.concurrent.Flow
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(): ViewModel() {
    var isPermissionGranted = MutableStateFlow(false)
}