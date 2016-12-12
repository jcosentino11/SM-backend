package co.smartmiles.backend.dao.impl

import co.smartmiles.backend.dao.SMRoleDao
import co.smartmiles.backend.exception.BadRequestException
import co.smartmiles.backend.exception.NotFoundException
import co.smartmiles.backend.exception.ResourceConflictException
import co.smartmiles.backend.model.SMRole
import co.smartmiles.backend.repository.SMRoleRepository
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import javax.transaction.Transactional

/**
 * @author Joseph Cosentino.
 */
@Component
@Transactional
open class SMRoleDaoImpl(val roleRepository : SMRoleRepository) : SMRoleDao {

    override fun exists(id: Long): Boolean {
        return roleRepository.exists(id)
    }

    override fun findAll(pageable: Pageable): List<SMRole> {
        return roleRepository.findAll(pageable).content
    }

    override fun findOne(id: Long) : SMRole {
        try {
            return roleRepository.findOne(id)
        } catch (e: Exception) {
            throw NotFoundException("Role with id $id not found", e)
        }
    }

    override fun findByName(name: String) : SMRole {
        return roleRepository.findByName(name) ?: throw NotFoundException("Role $name not found.")
    }

    override fun findByIdAndName(id: Long, name: String): SMRole {
        return roleRepository.findByIdAndName(id, name) ?: throw NotFoundException("Role ($id, $name) not found.")
    }

    override fun insert(role: SMRole): SMRole {
        return save(role)
    }

    override fun update(id: Long, role: SMRole): SMRole {
        val existingRole = findOne(id)
        if (role.id != null && id != role.id) {
            throw BadRequestException("Role id must match one provided in url")
        }
        return save(existingRole.merge(role))
    }

    private fun save(role : SMRole) : SMRole {
        try {
            return roleRepository.save(role)
        } catch (e : DataIntegrityViolationException) {
            throw ResourceConflictException(e)
        }
    }

    override fun delete(id: Long) {
        if (!exists(id)) throw NotFoundException("Role with id $id doesn't exist.")
        roleRepository.delete(id)
    }

}