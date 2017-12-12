package no.nowak.gpstracker.core.userDetails

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import no.nowak.gpstracker.core.infrastructure.Paths
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Api(description = "User controller", tags = ["User"])
@RequestMapping(Paths.USER_DETAILS_PATH)
interface UserDetailsApi {

    @ApiOperation("Get User Details")
    @ApiResponse(code = 200, message = "userDetails", response = UserDetails::class)
    @GetMapping()
    fun getUserDetails(): UserDetails
}