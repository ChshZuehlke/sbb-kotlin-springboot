package ch.zuehlke.gauch.spring.feature.account

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class AccountNotFoundException(message: String = "",throwable: Throwable? = null) : ResponseStatusException(HttpStatus.NOT_FOUND,message,throwable)