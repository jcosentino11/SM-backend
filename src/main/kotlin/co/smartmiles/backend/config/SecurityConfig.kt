package co.smartmiles.backend.config

import co.smartmiles.backend.dao.SMUserDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder


/**
 * Security configuration
 *
 * @author Joseph Cosentino.
 */
@Configuration
open class SecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    var userDao: SMUserDao? = null

    override fun configure(http: HttpSecurity?) {
        http
                ?.authorizeRequests()
                    ?.antMatchers("/health")
                        ?.permitAll()
                    ?.antMatchers("/api/v1/users/**", "/api/v1/roles/**")
                        ?.hasRole("ADMIN")
                    ?.anyRequest()
                        ?.authenticated()?.and()
                ?.httpBasic()?.and()
                ?.csrf()?.disable()
    }

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth!!.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder())
    }

    @Bean
    open fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Bean
    override fun userDetailsService(): UserDetailsService {
        return UserDetailsService { username ->
            val user = userDao!!.findByUsername(username)
            User(user.username, user.password, user.enabled!!, true, true, true,
                    AuthorityUtils.createAuthorityList(*user.roles!!.map { it.name }.toTypedArray()))
        }
    }

}




