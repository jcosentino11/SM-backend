package co.smartmiles.backend.model

import javax.persistence.*

/**
 * Role Entity
 *
 * @author Joseph Cosentino.
 */
@Entity
data class SMRole(

        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(insertable = false, updatable = false)
        val id: Long? = null,

        @Column(unique = true, nullable = false)
        val name: String? = null) {

    fun merge(user : SMRole) : SMRole {
        return SMRole(user.id ?: this.id,
                user.name ?: this.name)
    }

}
