package co.smartmiles.backend.validate

import co.smartmiles.backend.exception.BadRequestException
import co.smartmiles.backend.model.SMRole
import co.smartmiles.backend.util.Validator
import org.springframework.stereotype.Component

/**
 * @author Joseph Cosentino.
 */
@Component
class SMRoleValidator : Validator<SMRole> {

    val nameMaxLength = 12
    val nameMinLength = 6
    val rolePrefix = "ROLE_"

    override fun validateInsert(entity: SMRole) {
        if (entity.id != null) {
            throw BadRequestException("Do not provide id, it will be auto generated")
        }
        validateName(entity.name)
    }

    private fun validateName(name : String?) {
        if (name == null) {
            throw BadRequestException("Role name must be provided")
        }
        if (!name.startsWith(rolePrefix)) {
            throw BadRequestException("Role name must start with '$rolePrefix'")
        }
        if (name.length < nameMinLength) {
            throw BadRequestException("Role name must have a length of at least $nameMinLength")
        }
        if (name.length > nameMaxLength) {
            throw BadRequestException("Role name must have a length no greater than $nameMaxLength")
        }
    }

    override fun validateUpdate(entity: SMRole) {
        if (entity.id != null && entity.id <= 0) {
            throw BadRequestException("Id must be a positive Integer")
        }
        validateName(entity.name)
    }

}