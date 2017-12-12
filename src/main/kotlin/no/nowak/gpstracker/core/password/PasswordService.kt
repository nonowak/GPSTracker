package no.nowak.gpstracker.core.password

import no.nowak.gpstracker.core.infrastructure.exceptions.ServiceException
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class PasswordService(private val encoder: PasswordEncoder,
                      private val passwordRepository: PasswordRepository) {

    fun isPasswordValid(password: Password, newPassword: String): Boolean {
        if (!password.previousHash.isBlank()) {
            if (encoder.matches(newPassword, password.previousHash) && !isPasswordModifiedOnBefore3Months(password.modifiedOn!!))
                throw ServiceException(HttpStatus.BAD_REQUEST, "Password is the same as your previous")
            if (encoder.matches(newPassword, password.currentHash))
                throw ServiceException(HttpStatus.BAD_REQUEST, "Password is the same as your current")
        }
        return true
    }

    fun save(password: Password) =
            passwordRepository.save(password)

    fun isPasswordModifiedOnBefore3Months(modifiedOn: LocalDate): Boolean =
            modifiedOn.plusMonths(3).isBefore(LocalDate.now())

    fun encode(password: String) =
            encoder.encode(password)
}