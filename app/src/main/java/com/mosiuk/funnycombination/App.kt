package com.mosiuk.funnycombination

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Основной класс Application.
 * Аннотация @HiltAndroidApp запускает генерацию кода Hilt
 * и создает базовый компонент для всего приложения.
 */
@HiltAndroidApp
class App : Application()