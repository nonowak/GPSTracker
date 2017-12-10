package no.nowak.gpstracker.core.user

import no.nowak.gpstracker.core.infrastructure.exceptions.ServiceException
import no.nowak.gpstracker.core.password.Password
import no.nowak.gpstracker.core.user.dto.UserRegisterDTO
import no.nowak.gpstracker.core.userDetails.UserDetails
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(val userRepository: UserRepository,
                  val encoder: PasswordEncoder) {

    fun registerUser(userRegisterDTO: UserRegisterDTO): String{
        val email = userRegisterDTO.email
        if(userRepository.findByEmailAddress(email) != null) throw ServiceException(HttpStatus.CONFLICT, "User with this email exists")
        val user = User(
                emailAddress = email,
                password = Password(
                        currentHash = encoder.encode(userRegisterDTO.password)
                ),
                userDetails = UserDetails()
        )

        saveUser(user)
        return email
    }

    fun saveUser(user: User) =
            userRepository.save(user)
}