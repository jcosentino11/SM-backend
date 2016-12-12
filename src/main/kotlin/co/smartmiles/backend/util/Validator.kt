package co.smartmiles.backend.util

/**
 * @author Joseph Cosentino.
 */
interface Validator<in T> {

    fun validateInsert(entity: T)

    fun validateUpdate(entity: T)

}