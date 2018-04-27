package ch.zuehlke.gauch.spring.feature.message

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class MessageNotFoundException(message: String = "",throwable: Throwable? = null) : ResponseStatusException(HttpStatus.NOT_FOUND,message,throwable)
