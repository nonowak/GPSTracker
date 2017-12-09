package no.nowak.gpstracker.core.infrastructure.exceptions

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandlerAdvice {

    @ExceptionHandler(ServiceException::class)
    fun handleException(e: ServiceException): ResponseEntity<*> =
         ResponseEntity.status(e.httpStatus).body(e.body)

}