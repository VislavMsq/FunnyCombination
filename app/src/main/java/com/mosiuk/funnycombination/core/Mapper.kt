package com.mosiuk.funnycombination.core

/**
 * Универсальный интерфейс для преобразования (маппинга) одного объекта в другой.
 * Помогает преобразовывать модели между слоями (например, Data-модель в Domain-модель).
 *
 * @param I Тип входного объекта.
 * @param O Тип выходного объекта.
 */
interface Mapper<in I, out O> {
    fun map(input: I): O
}