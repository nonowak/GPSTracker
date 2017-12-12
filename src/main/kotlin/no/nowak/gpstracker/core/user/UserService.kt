package no.nowak.gpstracker.core.user

import no.nowak.gpstracker.core.infrastructure.Tools
import no.nowak.gpstracker.core.infrastructure.exceptions.ServiceException
import no.nowak.gpstracker.core.infrastructure.mail.MailService
import no.nowak.gpstracker.core.password.Password
import no.nowak.gpstracker.core.password.PasswordService
import no.nowak.gpstracker.core.user.dto.UserRegisterDTO
import no.nowak.gpstracker.core.user.dto.UserResetPasswordDTO
import no.nowak.gpstracker.core.userDetails.UserDetails
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class UserService(private val userRepository: UserRepository,
                  private val mailService: MailService,
                  private val passwordService: PasswordService) {

    fun registerUser(userRegisterDTO: UserRegisterDTO): String {
        val email = userRegisterDTO.emailAddress
        if (userRepository.findByEmailAddress(email) != null) throw ServiceException(HttpStatus.CONFLICT, "User with this email exists")
        val user = User(
                emailAddress = email,
                password = Password(
                        currentHash = passwordService.encode(userRegisterDTO.password)
                ),
                userDetails = UserDetails()
        )
        save(user)
        mailService.register(user)
        return email
    }

    fun activateUser(activationKey: String) {
        val user = getByActivationKey(activationKey)
        if (user.enabled) throw ServiceException(HttpStatus.BAD_REQUEST, "User is enabled")
        user.enabled = true
        save(user)
    }

    fun lostPassword(emailAddress: String) {
        val user = getByEmail(emailAddress)
        user.userDetails.resetPasswordKey = Tools.generateUUIDString()
        save(user)
        mailService.lostPassword(user)
    }

    fun resetPassword(resetPasswordKey: String, userResetPasswordDTO: UserResetPasswordDTO) {
        val user = getByEmail(userResetPasswordDTO.emailAddress)
        passwordService.isPasswordValid(user.password, userResetPasswordDTO.password)
        passwordService.save(Password(id = user.password.id,
                currentHash = passwordService.encode(userResetPasswordDTO.password),
                previousHash = user.password.currentHash,
                modifiedOn = LocalDate.now()))
    }

    fun save(user: User): User =
            userRepository.save(user)

    fun getByEmail(email: String) =
            userRepository.findByEmailAddress(email) ?: throw ServiceException(HttpStatus.NOT_FOUND, "User with this email not found")

    fun getByActivationKey(activationKey: String): User =
            userRepository.findByUserDetails_activationKey(activationKey) ?: throw ServiceException(HttpStatus.NOT_FOUND, "User with this activationKey not found")

}