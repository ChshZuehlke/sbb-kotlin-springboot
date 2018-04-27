package ch.zuehlke.gauch.spring.feature.account

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.concurrent.ConcurrentHashMap


@Service
@Transactional
class AccountService(private val accountRepository: ReactivedMapRepository){

    private val users : ConcurrentHashMap<String,Account> = ConcurrentHashMap<String,Account>()

    @Transactional(readOnly = true)
    fun findAll(): Flux<Account> = accountRepository.findAll()

    fun findAccountByName(name: String): Mono<Account> {
       return accountRepository
               .findByName(name)    // Mono.just(users[name])
               .switchIfEmpty(Mono.error(AccountNotFoundException("A user for the given name $name couldn't be found")))

    }
}