package ch.zuehlke.gauch.spring.config

import ch.zuehlke.gauch.spring.feature.account.AccountHandler
import ch.zuehlke.gauch.spring.feature.message.MessageHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router


@Configuration
class RouterConfiguration{

    @Bean
    fun messageRouting(messageHandler: MessageHandler, accountHandler: AccountHandler) = router {
        ("/api" and accept(MediaType.APPLICATION_JSON)).nest {
            GET("/messages",messageHandler::getMessagesForAccount)
            POST("/messages",messageHandler::addMessageForAccount)
            GET("/account",accountHandler::getAccountInformation)
            GET("/account/admin",accountHandler::getAll)
        }
    }
}