package co.smartmiles.backend.dao.impl

import co.smartmiles.backend.dao.SMRoleDao
import co.smartmiles.backend.dao.SMUserDao
import co.smartmiles.backend.exception.BadRequestException
import co.smartmiles.backend.exception.NotFoundException
import co.smartmiles.backend.exception.ResourceConflictException
import co.smartmiles.backend.model.SMRole
import co.smartmiles.backend.model.SMUser
import co.smartmiles.backend.repository.SMUserRepository
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.domain.Pageable
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import javax.transaction.Transactional

/**
 * @author Joseph Cosentino.
 */
@Component
@Transactional
open class SMUserDaoImpl(val userRepository: SMUserRepository, val roleDao: SMRoleDao, val passwordEncoder: PasswordEncoder) : SMUserDao {

    override fun exists(id: Long): Boolean {
        return userRepository.exists(id)
    }

    override fun findAll(pageable : Pageable) : List<SMUser> {
        return userRepository.findAll(pageable).content
    }

    override fun findOne(id: Long): SMUser {
        try {
            return userRepository.findOne(id)
        } catch (e : Exception) {
            throw NotFoundException("User with id $id not found.")
        }
    }

    override fun findByUsername(username: String) : SMUser {
        return userRepository.findByUsername(username) ?: throw NotFoundException("User $username not found.")
    }

    override fun insert(user: SMUser): SMUser {
        return save(user)
    }

    override fun update(id: Long, user: SMUser): SMUser {
        val existingUser = findOne(id)
        if (user.id != null && id != user.id) {
            throw BadRequestException("Role id must match one provided in url")
        }
        return save(existingUser.merge(user))
    }

    private fun save(user : SMUser) : SMUser {
        try {
            val roles = validateRoles(user.roles ?: emptyList())
            user.roles = roles
            user.password = passwordEncoder.encode(user.password)
            return userRepository.save(user)
        } catch (e : DataIntegrityViolationException) {
            throw ResourceConflictException(e)
        }
    }

    private fun validateRoles(roles : List<SMRole>) : List<SMRole> {
        return roles.map {
            if (it.id != null && it.name != null) {
                roleDao.findByIdAndName(it.id, it.name)
            } else if (it.id != null) {
                roleDao.findOne(it.id)
            } else if (it.name != null) {
                roleDao.findByName(it.name)
            } else {
                throw BadRequestException("Malformed roles field")
            }
        }
    }

    override fun delete(id: Long) {
        if (!exists(id)) throw NotFoundException("User with id $id doesn't exist.")
        userRepository.delete(id)
    }

}