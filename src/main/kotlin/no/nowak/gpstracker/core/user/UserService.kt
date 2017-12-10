package no.nowak.gpstracker.core.user

import no.nowak.gpstracker.core.infrastructure.exceptions.ServiceException
import no.nowak.gpstracker.core.infrastructure.mail.MailService
import no.nowak.gpstracker.core.password.Password
import no.nowak.gpstracker.core.user.dto.UserRegisterDTO
import no.nowak.gpstracker.core.userDetails.UserDetails
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository,
                  private val encoder: PasswordEncoder,
                  private val mailService: MailService) {

    fun registerUser(userRegisterDTO: UserRegisterDTO): String {
        val email = userRegisterDTO.email
        if (userRepository.findByEmailAddress(email) != null) throw ServiceException(HttpStatus.CONFLICT, "User with this email exists")
        val user = User(
                emailAddress = email,
                password = Password(
                        currentHash = encoder.encode(userRegisterDTO.password)
                ),
                userDetails = UserDetails()
        )
        save(user)
        mailService.register(user)
        return email
    }

    fun activateUser(activationKey: String) {
        val user = getByActivationKey(activationKey)
        user.enabled = true
        user.userDetails.activationKey = ""
        save(user)
    }

    fun save(user: User): User =
            userRepository.save(user)

    fun getByEmail(email: String) =
            userRepository.findByEmailAddress(email) ?: throw ServiceException(HttpStatus.NOT_FOUND, "User with this email not found")

    fun getByActivationKey(activationKey: String): User =
            userRepository.findByUserDetails_activationKey(activationKey) ?: throw ServiceException(HttpStatus.NOT_FOUND, "User with this activationKey not found")

}