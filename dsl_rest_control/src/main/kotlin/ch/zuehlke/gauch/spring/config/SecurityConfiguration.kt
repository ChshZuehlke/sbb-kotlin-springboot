package ch.zuehlke.gauch.spring.config

import ch.zuehlke.gauch.spring.config.service.ReactiveAuthenticationService
import ch.zuehlke.gauch.spring.feature.account.Account
import ch.zuehlke.gauch.spring.feature.account.AccountRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authorization.AuthorizationDecision
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.web.server.SecurityWebFilterChain


@Configuration
@EnableWebFluxSecurity() // used for url authentication
class SecurityConfiguration(private val accountRepository: AccountRepository) {

    @Bean
    fun springWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http

                .authorizeExchange()

                .anyExchange()
                .authenticated()
                .and()
                .httpBasic()
                .and()
                .csrf().disable()
                .build()
    }


    /**
    In spring-security-core:5.0.0.RC1, the default PasswordEncoder is built as a DelegatingPasswordEncoder.
    When you store the users in memory, you are providing the passwords in plain text and when trying to retrieve
    the encoder from the DelegatingPasswordEncoder to validate the password it can't find one that matches
    the way in which these passwords were stored.

    Use this way to create users instead.

    User.withDefaultPasswordEncoder().username("user").password("user").roles("USER").build();

    You can also simply prefix {noop} to your passwords in order for the DelegatingPasswordEncoder
    use the NoOpPasswordEncoder to validate these passwords. Notice that NoOpPasswordEncoder is deprecated though,
    as it is not a good practice to store passwords in plain text.

    User.withUsername("user").password("{noop}user").roles("USER").build();
     **/
    @Bean
    fun userDetailRepository(): ReactiveUserDetailsService {
        return ReactiveAuthenticationService(accountRepository)
    }

}
