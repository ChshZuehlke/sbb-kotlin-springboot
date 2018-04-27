package ch.zuehlke.gauch.spring.feature.account

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

interface AccountRepository : ReactiveCrudRepository<Account, String> {

    fun findByName(name: String): Mono<Account>
}