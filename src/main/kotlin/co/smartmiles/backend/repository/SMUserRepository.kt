package co.smartmiles.backend.repository

import co.smartmiles.backend.model.SMUser
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface SMUserRepository : PagingAndSortingRepository<SMUser, Long> {

    fun findByUsername(username: String): SMUser?

}