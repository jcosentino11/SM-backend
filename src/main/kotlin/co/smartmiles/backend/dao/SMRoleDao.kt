package co.smartmiles.backend.dao

import co.smartmiles.backend.model.SMRole
import org.springframework.data.domain.Pageable

/**
 * @author Joseph Cosentino.
 */
interface SMRoleDao {

    fun exists(id: Long) : Boolean

    fun findAll(pageable : Pageable) : List<SMRole>

    fun findOne(id: Long) : SMRole

    fun findByName(name: String) : SMRole

    fun findByIdAndName(id: Long, name: String) : SMRole

    fun insert(role : SMRole): SMRole

    fun update(id: Long, role: SMRole): SMRole

    fun delete(id: Long)

}