package ch.zuehlke.gauch.spring

import ch.zuehlke.gauch.spring.feature.account.Account
import ch.zuehlke.gauch.spring.feature.account.AccountRepository
import ch.zuehlke.gauch.spring.feature.account.ReactivedMapRepository
import ch.zuehlke.gauch.spring.feature.message.Message
import ch.zuehlke.gauch.spring.feature.message.MessageRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import java.util.*
import java.util.stream.Stream

@SpringBootApplication
class ReactiveApplication(private val accountRepository: ReactivedMapRepository,private val messageRepository: MessageRepository) : CommandLineRunner {

    override fun run(vararg args: String) {
        accountRepository.deleteAll().block()
        messageRepository.deleteAll().block()
        Stream.of("user:{noop}password:USER", "admin:{noop}password:ADMIN")
                .map { accountString ->
                    accountString.split(":")
                }.forEach { splitted: List<String>? ->
                    splitted?.let {
                        val roles = mutableListOf<String>().apply { addAll(it[2].split(",")) }
                        val account = Account(it[0], it[1], UUID.randomUUID().toString(),isActive = true,roles = roles)
                        val persistedAccount = accountRepository.save(account).block()

                        Stream.of("message 1","message 2").forEach {
                            messageRepository.save(Message(it,persistedAccount)).block()
                        }


                    }
                }
    }
}


fun main(args: Array<String>) {
    SpringApplicationBuilder()
            .sources(ReactiveApplication::class.java)
            .web(WebApplicationType.REACTIVE)
            .run(*args)

}