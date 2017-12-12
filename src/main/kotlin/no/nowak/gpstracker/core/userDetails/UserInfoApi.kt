package no.nowak.gpstracker.core.userDetails

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import no.nowak.gpstracker.core.infrastructure.Paths.USER_INFO_PATH
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Api(description = "User Info Controller", tags = ["User Info"])
@RequestMapping(USER_INFO_PATH)
interface UserInfoApi {

    @ApiOperation("Get User Info")
    @ApiResponse(code = 200, message = "userInfo", response = UserInfo::class)
    @GetMapping("")
    fun getUserInfo(): UserInfo
}