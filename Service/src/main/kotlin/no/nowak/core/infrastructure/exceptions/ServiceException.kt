package no.nowak.core.infrastructure.exceptions

import org.springframework.http.HttpStatus

class ServiceException(
        val httpStatus: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
        val body: ResponseTemplate
) : RuntimeException(body.message) {
    constructor(httpStatus: HttpStatus, body: String) : this(httpStatus, ResponseTemplate(httpStatus.toString(), body))
}