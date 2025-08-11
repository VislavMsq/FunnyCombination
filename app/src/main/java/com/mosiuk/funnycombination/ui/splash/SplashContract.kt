package com.mosiuk.funnycombination.ui.splash

/**
 * Контракт для экрана Splash. Описывает его состояние, события и эффекты.
 * Паттерн UDF (Unidirectional Data Flow).
 */

/**
 * События, которые могут произойти на экране (действия пользователя).
 * Для Splash-экрана событий нет, так как пользователь с ним не взаимодействует.
 */
interface SplashEvent

/**
 * Состояние экрана, которое должно быть отображено в UI.
 * @param isLoading флаг для отображения индикатора загрузки (в данном случае не используется).
 */
data class SplashState(val isLoading: Boolean = true)

/**
 * Одноразовые события (side-effects), которые не являются частью состояния.
 * Например, навигация, показ Toast-сообщений.
 */
sealed interface SplashEffect {
    /**
     * Эффект для перехода на главный экран.
     */
    object NavigateToMainMenu : SplashEffect
}