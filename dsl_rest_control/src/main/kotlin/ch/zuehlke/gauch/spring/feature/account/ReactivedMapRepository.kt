package ch.zuehlke.gauch.spring.feature.account

import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@Component
class ReactivedMapRepository(private val concurrentHashMap: ConcurrentHashMap<String, Account> = ConcurrentHashMap()) {

    fun deleteAll() = Mono.just(concurrentHashMap.clear())

    fun findByName(name: String?): Mono<Account> = when {
        name.isNullOrEmpty() -> {
            Mono.error(AccountNotFoundException("Invalid username, it was null or empty"))
        }
        else -> {
            Mono.just(concurrentHashMap[name!!])
        }
    }

    fun save(account: Account?): Mono<Account?> {
        account?.let { shAccount ->
            if (shAccount.name.isNullOrEmpty()) {
                return Mono.error(InvalidAccountException("Account has no name"))
            }
            if (shAccount.id == null) {
                shAccount.id = UUID.randomUUID().toString()
            }
            concurrentHashMap[shAccount.name!!] = shAccount
            return Mono.just(shAccount)
        }
        return Mono.error(InvalidAccountException("Account was null"))
    }

    fun findAll(): Flux<Account>{
        return Flux.
                fromIterable(concurrentHashMap.values)
                .switchIfEmpty(Flux.error(AccountNotFoundException("No accounts found")))
    }
}