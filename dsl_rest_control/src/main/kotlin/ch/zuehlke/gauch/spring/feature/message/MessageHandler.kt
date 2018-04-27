package ch.zuehlke.gauch.spring.feature.message

import ch.zuehlke.gauch.spring.feature.account.Account
import ch.zuehlke.gauch.spring.feature.account.AccountNotFoundException
import ch.zuehlke.gauch.spring.feature.account.AccountService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyToServerSentEvents
import reactor.core.publisher.Mono

@Component
class MessageHandler(private val messageService: MessageService, private val accountService: AccountService) {

    fun getAll(request: ServerRequest): Mono<ServerResponse> {
        return ServerResponse.ok().bodyToServerSentEvents(messageService.findAll())
    }

    fun getMessagesForAccount(request: ServerRequest): Mono<ServerResponse> {
        return request
                .principal()
                .flatMap { principal -> accountService.findAccountByName(principal.name) }
                .flatMap { account: Account ->ServerResponse.ok().bodyToServerSentEvents(messageService.findAllForAccount(account)) }
                .switchIfEmpty(Mono.error(MessageNotFoundException("No messages found")))
    }


    fun addMessageForAccount(request: ServerRequest): Mono<ServerResponse> {
       return request.principal()
                .flatMap { principal -> accountService.findAccountByName(principal.name) }
                .zipWith(request.bodyToMono(MessageDto::class.java))
                .flatMap { data -> messageService.saveMessage(Message(data.t2.message,data.t1)) }
                .flatMap { message -> Mono.justOrEmpty(MessageDto(message.message)) }
                .flatMap { message -> ServerResponse.ok().body(BodyInserters.fromObject(message)) }
   }
}