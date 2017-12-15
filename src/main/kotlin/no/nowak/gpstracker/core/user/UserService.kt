package no.nowak.gpstracker.core.user

import no.nowak.gpstracker.core.infrastructure.exceptions.ServiceException
import no.nowak.gpstracker.core.infrastructure.mail.MailService
import no.nowak.gpstracker.core.password.Password
import no.nowak.gpstracker.core.password.PasswordService
import no.nowak.gpstracker.core.user.dto.UserRegisterDTO
import no.nowak.gpstracker.core.userDetails.UserInfo
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

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
                        currentHash = userRegisterDTO.password
                ),
                userInfo = UserInfo()
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

    fun save(user: User): User =
            userRepository.save(user)

    fun getByActivationKey(activationKey: String): User =
            userRepository.findByActivationKey(activationKey) ?: throw ServiceException(HttpStatus.NOT_FOUND, "User with this activationKey not found")

}