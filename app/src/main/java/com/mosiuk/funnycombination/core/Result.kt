package com.mosiuk.funnycombination.core

/**
 * Универсальный sealed-класс для представления результата операции.
 * Используется во всей архитектуре для обработки успешных исходов и ошибок.
 *
 * @param T Тип данных в случае успеха.
 */
sealed class Result<out T> {
    /**
     * Представляет успешный результат операции.
     * @property data Данные, полученные в результате.
     */
    data class Success<out T>(val data: T) : Result<T>()

    /**
     * Представляет ошибку, возникшую во время операции.
     * @property exception Исключение, которое было перехвачено.
     */
    data class Error(val exception: Throwable) : Result<Nothing>()
}