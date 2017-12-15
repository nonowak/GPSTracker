package no.nowak.core.userDetails

import org.springframework.stereotype.Service

@Service
class UserInfoService(private val userInfoRepository: UserInfoRepository) {

    fun save(userInfo: UserInfo) =
            userInfoRepository.save(userInfo)
}