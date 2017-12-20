package no.nowak.core.userDetails

import no.nowak.core.infrastructure.security.authorizationService.AuthorizationService
import no.nowak.core.userDetails.dto.UserInfoDTO
import org.springframework.stereotype.Service

@Service
class UserInfoService(private val authorizationService: AuthorizationService,
                      private val userInfoRepository: UserInfoRepository) {

    fun save(userInfo: UserInfo): UserInfo =
            userInfoRepository.save(userInfo)

    fun getCurrentUserInfo(): UserInfo =
            authorizationService.getCurrentUser().userInfo

    fun update(userInfoDTO: UserInfoDTO): UserInfo {
        val userInfo = getCurrentUserInfo()
        userInfo.update(userInfoDTO)
        return save(userInfo)
    }
}