package co.smartmiles.backend.dao

import co.smartmiles.backend.model.SMUser
import org.springframework.data.domain.Pageable

/**
 * @author Joseph Cosentino.
 */
interface SMUserDao {

    fun exists(id : Long) : Boolean

    fun findAll(pageable : Pageable) : List<SMUser>

    fun findOne(id : Long) : SMUser

    fun findByUsername(username: String): SMUser

    fun insert(user : SMUser): SMUser

    fun update(id: Long, user: SMUser): SMUser

    fun delete(id: Long)

}