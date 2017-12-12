package no.nowak.gpstracker.core.userDetails

import org.springframework.data.jpa.repository.JpaRepository

interface UserInfoRepository : JpaRepository<UserInfo, Int>
