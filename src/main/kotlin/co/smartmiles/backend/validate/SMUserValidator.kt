package co.smartmiles.backend.validate

import co.smartmiles.backend.exception.BadRequestException
import co.smartmiles.backend.model.SMUser
import co.smartmiles.backend.util.Validator
import org.springframework.stereotype.Component

/**
 * @author Joseph Cosentino.
 */
@Component
class SMUserValidator(val roleValidator: SMRoleValidator) : Validator<SMUser> {

    val usernameMaxLength = 30
    val usernameMinLength = 6

    val passwordMaxLength = 30
    val passwordMinLength = 8

    override fun validateInsert(entity: SMUser) {
        if (entity.id != null) {
            throw BadRequestException("Do not provide id, it will be auto generated")
        }
        if (entity.username == null) {
            throw BadRequestException("Username must not be null")
        }
        entity.username = entity.username!!.trim()
        validateUsername(entity.username!!)

        if (entity.password == null) {
            throw BadRequestException("Password must not be null")
        }
        entity.password = entity.password!!.trim()
        validatePassword(entity.password!!)

        if (entity.roles != null) {
            entity.roles!!.forEach { roleValidator.validateInsert(it) }
        }
    }

    private fun validateUsername(username : String) {
        if (username.length < usernameMinLength) {
            throw BadRequestException("Username must have a length of at least $usernameMinLength")
        }
        if (username.length > usernameMaxLength) {
            throw BadRequestException("Username must have a length no greater than $usernameMaxLength")
        }
    }

    private fun validatePassword(password: String) {
        if (password.length < passwordMinLength) {
            throw BadRequestException("Password must have a length of at least $passwordMinLength")
        }
        if (password.length > passwordMaxLength) {
            throw BadRequestException("Password must have a length no greater than $passwordMaxLength")
        }
        if (password == password.toLowerCase()) {
            throw BadRequestException("Password must have at least one upper-case character")
        }
        if (password == password.toUpperCase()) {
            throw BadRequestException("Password must have at least one lower-case character")
        }
        if (password.matches("^[A-Za-z0-9 ]*$".toRegex())) {
            throw BadRequestException("Password must contain at least one non-alpha-numeric")
        }
    }

    override fun validateUpdate(entity: SMUser) {
        if (entity.id != null && entity.id <= 0) {
            throw BadRequestException("Id must be a positive Integer")
        }
        if (entity.username != null) {
            validateUsername(entity.username!!)
        }
        if (entity.password != null) {
            validatePassword(entity.password!!)
        }
        if (entity.roles != null) {
            entity.roles!!.forEach { roleValidator.validateUpdate(it) }
        }
    }

}