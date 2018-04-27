package ch.zuehlke.gauch.spring.feature.account

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class InvalidAccountException(message: String = "",throwable: Throwable? = null) : ResponseStatusException(HttpStatus.BAD_REQUEST,message,throwable)