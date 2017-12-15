package no.nowak.core.infrastructure.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandlerAdvice {

    @ExceptionHandler(ServiceException::class)
    fun handleException(e: ServiceException): ResponseEntity<*> =
            ResponseEntity.status(e.httpStatus).body(e.body)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleException(e: MethodArgumentNotValidException): ResponseEntity<*> =
            handleException(ServiceException(HttpStatus.BAD_REQUEST, e.bindingResult.fieldError.defaultMessage))
}