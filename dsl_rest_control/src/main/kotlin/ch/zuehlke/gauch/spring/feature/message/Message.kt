package ch.zuehlke.gauch.spring.feature.message

import ch.zuehlke.gauch.spring.feature.account.Account
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Message(var message: String? = null,
                   var account: Account? = null,
                   @Id var id: String? = null)