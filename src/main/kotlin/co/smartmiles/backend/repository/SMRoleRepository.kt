package co.smartmiles.backend.repository

import co.smartmiles.backend.model.SMRole
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface SMRoleRepository : PagingAndSortingRepository<SMRole, Long> {

    fun findByName(name: String) : SMRole?

    fun findByIdAndName(id: Long, name: String) : SMRole?

}