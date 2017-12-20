package no.nowak.core.userDetails

import no.nowak.core.userDetails.dto.UserInfoDTO
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
internal class UserInfoController(private val userInfoService: UserInfoService) : UserInfoApi {
    override fun getUserInfo(): UserInfoDTO =
            UserInfoDTO(userInfoService.getCurrentUserInfo())

    override fun updateUserInfo(@RequestBody @Valid updateUserInfoDTO: UserInfoDTO): UserInfoDTO =
            UserInfoDTO(userInfoService.update(updateUserInfoDTO))


}