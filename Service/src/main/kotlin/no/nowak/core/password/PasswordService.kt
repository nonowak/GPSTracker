package no.nowak.core.password

import no.nowak.core.infrastructure.Tools
import no.nowak.core.infrastructure.exceptions.ServiceException
import no.nowak.core.infrastructure.mail.MailService
import no.nowak.core.password.dto.ResetPasswordDTO
import no.nowak.core.user.User
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class PasswordService(private val encoder: PasswordEncoder,
                      private val passwordRepository: PasswordRepository,
                      private val mailService: MailService) {

    fun lostPassword(emailAddress: String) {
        val password = getByUserEmailAddress(emailAddress)
        val user = getUserFromPassword(password)
        password.resetKey = Tools.generateUUIDString()
        save(password)
        mailService.lostPassword(user)
    }

    fun resetPassword(resetKey: String, resetPasswordDTO: ResetPasswordDTO) {
        val password = getByResetPasswordKeyAndUserEmailAddress(resetKey, resetPasswordDTO.emailAddress)
        lockPassword(password)
        if (isPasswordValid(password, resetPasswordDTO.password)) {
            save(Password(id = password.id,
                    currentHash = encode(resetPasswordDTO.password),
                    previousHash = password.currentHash,
                    modifiedOn = LocalDateTime.now(),
                    lockedDuringReset = false,
                    user = password.user))
        }
    }

    fun lockPassword(password: Password) {
        password.lockedDuringReset = true
        save(password)
    }

    fun isPasswordValid(password: Password, newPassword: String): Boolean {
        if (!password.previousHash.isNullOrEmpty()) {
            if (encoder.matches(newPassword, password.previousHash) && !isPasswordModifiedOnBefore3Months(password.modifiedOn!!))
                throw ServiceException(HttpStatus.BAD_REQUEST, "Password is the same as your previous")
            if (encoder.matches(newPassword, password.currentHash))
                throw ServiceException(HttpStatus.BAD_REQUEST, "Password is the same as your current")
        }
        return true
    }

    fun getUserFromPassword(password: Password): User =
            password.user ?: throw ServiceException(HttpStatus.NOT_FOUND, "Not found user with this key")

    fun save(password: Password) =
            passwordRepository.save(password)

    fun isPasswordModifiedOnBefore3Months(modifiedOn: LocalDateTime): Boolean =
            modifiedOn.plusMonths(3).isBefore(LocalDateTime.now())

    fun encode(password: String) =
            encoder.encode(password)

    fun getByUserEmailAddress(emailAddress: String): Password =
            passwordRepository.findByUser_emailAddress(emailAddress) ?: throw ServiceException(HttpStatus.NOT_FOUND, "Password for this email not found")

    fun getByResetPasswordKeyAndUserEmailAddress(resetPasswordKey: String, emailAddress: String) =
            passwordRepository.findByResetKeyAndUser_emailAddress(resetPasswordKey, emailAddress) ?: throw ServiceException(HttpStatus.NOT_FOUND, "Password for this resetKey and user emailAddress not found")
}