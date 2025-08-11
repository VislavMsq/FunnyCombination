package com.mosiuk.funnycombination.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel для экрана Splash.
 * Управляет логикой отображения экрана и навигацией по его завершении.
 */
@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(SplashState())
    val state = _state.asStateFlow()

    private val _effect = Channel<SplashEffect>()
    val effect = _effect.receiveAsFlow()

    init {
        // При инициализации запускаем корутину, которая ждет и отправляет эффект навигации.
        viewModelScope.launch {
            delay(SPLASH_DELAY_MS)
            _effect.send(SplashEffect.NavigateToMainMenu)
        }
    }

    companion object {
        private const val SPLASH_DELAY_MS = 2000L
    }
}