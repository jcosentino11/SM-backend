package co.smartmiles.backend.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.*


/**
 * User Entity
 *
 * @author Joseph Cosentino.
 */
@Entity
data class SMUser(

        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(insertable = false, updatable = false)
        val id: Long? = null,

        @Column(unique = true, nullable = false)
        var username: String? = null,

        @get:JsonIgnore
        @set:JsonProperty
        @Column(nullable = false)
        var password: String? = null,

        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(
                name = "SMUserRole",
                joinColumns = arrayOf(JoinColumn(name = "userId")),
                inverseJoinColumns = arrayOf(JoinColumn(name = "roleId")))
        var roles: List<SMRole>? = null,

        val enabled: Boolean? = null) {

    fun merge(user : SMUser) : SMUser {
        return SMUser(user.id ?: this.id,
                user.username ?: this.username,
                user.password ?: this.password,
                user.roles ?: this.roles,
                user.enabled ?: this.enabled)
    }

}