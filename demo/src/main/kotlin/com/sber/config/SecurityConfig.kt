package com.sber.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import javax.sql.DataSource

@Configuration
@EnableWebSecurity//(debug = true)
class SecurityConfig: WebSecurityConfigurerAdapter() {

    @Autowired
    val dataSource: DataSource? = null

    override fun configure(auth: AuthenticationManagerBuilder) {

        auth.jdbcAuthentication()
            .dataSource(dataSource)
            .usersByUsernameQuery("select login, password, 'true' from user where login=?")
            .authoritiesByUsernameQuery("select login, role from user where login=?")

//        auth.inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance())
//            .withUser("123").password("123").roles("ADMIN")
//            .and()
//            .withUser("1").password("1").roles("APP");
    }

    override fun configure(http: HttpSecurity?) {

        http!!.authorizeRequests()
            .antMatchers("/app/**").hasAnyRole("ADMIN", "APP")
            .antMatchers("/api/**").hasAnyRole("ADMIN", "API")
            .and()
            .formLogin().permitAll()
            .and()
            .logout().permitAll()

        http.httpBasic()
        http.csrf().disable()
        http.headers().frameOptions().disable()
    }
}
