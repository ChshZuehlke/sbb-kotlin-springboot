package ch.zuehlke.gauch.spring.feature.message

import ch.zuehlke.gauch.spring.feature.account.Account
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux

interface MessageRepository: ReactiveCrudRepository<Message, String> {

    fun findByAccount(account: Account): Flux<Message>
}