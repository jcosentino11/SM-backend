package co.smartmiles.backend.controller

import co.smartmiles.backend.dao.SMUserDao
import co.smartmiles.backend.model.SMUser
import co.smartmiles.backend.validate.SMUserValidator
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*

/**
 * Controller for accessing SMUser information
 *
 * @author Joseph Cosentino.
 */
@RestController
@RequestMapping("/api/v1/users")
open class SMUserController(val userDao: SMUserDao, val validator: SMUserValidator) {

    // TODO rate limiting
    // TODO circuit breaking

    @GetMapping
    fun getUsers(pageable: Pageable) : List<SMUser> {
        return userDao.findAll(pageable)
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable("id") id : Long) : SMUser {
        return userDao.findOne(id)
    }

    @PostMapping
    fun insertUser(@RequestBody user : SMUser) : SMUser {
        validator.validateInsert(user)
        return userDao.insert(user)
    }

    @PutMapping("/{id}")
    open fun updateUser(@PathVariable("id") id: Long, @RequestBody user : SMUser) : SMUser {
        validator.validateUpdate(user)
        return userDao.update(id, user)
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable("id") id : Long) {
        return userDao.delete(id)
    }

}