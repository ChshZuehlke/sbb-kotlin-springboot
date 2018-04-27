package ch.zuehlke.gauch.spring.config.service

import ch.zuehlke.gauch.spring.feature.account.Account
import ch.zuehlke.gauch.spring.feature.account.AccountNotFoundException
import ch.zuehlke.gauch.spring.feature.account.AccountRepository
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono


class ReactiveAuthenticationService(private val accountRepository: AccountRepository) : ReactiveUserDetailsService {

    override fun findByUsername(username: String?): Mono<UserDetails> {
        if (username.isNullOrEmpty()) {
            return Mono.error(AccountNotFoundException("Cannot find a user for the given name $username"))
        }

        return accountRepository.findByName(username!!)
                .map(::toUserDetails)
             //   .map { account -> toUserDetails(account) }
    }

    private fun toUserDetails(account: Account): UserDetails {
        return User.builder()
                .username(account.name)
                .accountExpired(!account.isActive)
                .accountLocked(!account.isActive)
                .roles(*account.roles.toTypedArray())
                .password(account.password)
                .authorities(*account.roles.toTypedArray())
                .credentialsExpired(!account.isActive)
                .disabled(!account.isActive)
                .build()

    }
}