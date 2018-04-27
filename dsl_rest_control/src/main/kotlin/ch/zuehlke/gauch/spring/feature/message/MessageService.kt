package ch.zuehlke.gauch.spring.feature.message

import ch.zuehlke.gauch.spring.feature.account.Account
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.util.function.Tuple2
import java.time.Duration

@Transactional
@Service
class MessageService(private val messageRepository: MessageRepository) {

    fun findAll(): Flux<Message> {
        return messageRepository.findAll()
    }

    fun findAllForAccount(account: Account): Flux<Message> {

        return Flux
                .interval(Duration.ofMillis(1500))
                .zipWith(messageRepository.findByAccount(account))
                .filter { t: Tuple2<Long, Message>? -> t?.t2 != null }
                .map { timedResponse: Tuple2<Long, Message> -> timedResponse.t2 }
        /*
        return messageRepository.
                findByAccount(account)
                .switchIfEmpty(Mono.error(MessageNotFoundException("No messages")))
                */

    }

    fun saveMessage(message: Message): Mono<Message> {
        return messageRepository.save(message)
    }

    fun deleteMessage(id: String) {
        messageRepository.deleteById(id)
    }
}