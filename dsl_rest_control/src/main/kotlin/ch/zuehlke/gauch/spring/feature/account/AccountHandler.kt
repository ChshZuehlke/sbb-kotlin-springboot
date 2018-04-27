package ch.zuehlke.gauch.spring.feature.account

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyToServerSentEvents
import reactor.core.publisher.Mono
import java.security.Principal

@Component
class AccountHandler(private val accountService: AccountService){

    fun getAll(request: ServerRequest): Mono<ServerResponse> {
        return ServerResponse.ok().bodyToServerSentEvents(accountService.findAll())
    }

    fun getAccountInformation(request: ServerRequest): Mono<ServerResponse>{
        return request
                .principal()
                .flatMap {principal: Principal? -> accountService.findAccountByName(principal!!.name) }
                .filter  { t: Account? -> t != null  && t.name.isNullOrBlank()}
                .flatMap { principal: Account -> accountService.findAccountByName(principal.name!!) }
                .flatMap { account: Account? -> ServerResponse.ok().body(BodyInserters.fromObject(account)) }
    }



}