package no.nowak.gpstracker.core.user

import no.nowak.gpstracker.core.infrastructure.exceptions.ServiceException
import no.nowak.gpstracker.core.user.dto.UserRegisterDTO
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class UserService(val userRepository: UserRepository) {

    fun registerUser(userRegisterDTO: UserRegisterDTO): String{
        val email = userRegisterDTO.email

        if(userRepository.findByEmailAddress(email) != null) throw ServiceException(HttpStatus.CONFLICT, "User with this email exists")

        return email
    }
}