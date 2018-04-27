package ch.zuehlke.gauch.spring.feature.account

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Account(var name: String? = null,
                   var password: String? = null,
                   @Id var id: String? = null,
                   val isActive: Boolean = true,
                   val roles: List<String> = mutableListOf())