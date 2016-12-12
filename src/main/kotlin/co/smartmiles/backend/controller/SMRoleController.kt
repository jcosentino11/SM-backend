package co.smartmiles.backend.controller

import co.smartmiles.backend.dao.SMRoleDao
import co.smartmiles.backend.model.SMRole
import co.smartmiles.backend.validate.SMRoleValidator
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*

/**
 * @author Joseph Cosentino.
 */
@RestController
@RequestMapping("/api/v1/roles")
open class SMRoleController(val roleDao: SMRoleDao, val validator: SMRoleValidator) {

    @GetMapping
    fun getRoles(pageable: Pageable) : List<SMRole> {
        return roleDao.findAll(pageable)
    }

    @GetMapping("/{id}")
    fun getRoleById(@PathVariable("id") id : Long) : SMRole {
        return roleDao.findOne(id)
    }

    @PostMapping
    fun insertRole(@RequestBody role : SMRole) : SMRole {
        validator.validateInsert(role)
        return roleDao.insert(role)
    }

    @PutMapping("/{id}")
    fun updateRole(@PathVariable("id") id: Long, @RequestBody role : SMRole) : SMRole {
        validator.validateUpdate(role)
        return roleDao.update(id, role)
    }

    @DeleteMapping("/{id}")
    fun deleteRole(@PathVariable("id") id : Long) {
        return roleDao.delete(id)
    }

}