package no.nowak.core.userDetails

import no.nowak.TestUtil
import no.nowak.core.userDetails.dto.UserInfoDTO
import no.nowak.stubs.UserInfoStub
import org.hamcrest.Matchers
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@RunWith(SpringRunner::class)
@ActiveProfiles("userFakeAuthorizationService", "test", "dev")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserInfoIntegrationTest {

    @Autowired
    lateinit var applicationContext: WebApplicationContext

    @Autowired
    lateinit var userInfoRepository: UserInfoRepository

    lateinit var mvc: MockMvc

    @Before
    fun setup() {
        this.mvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply<DefaultMockMvcBuilder>(SecurityMockMvcConfigurers.springSecurity())
                .build()
    }

    @Test
    fun `1getUserInfo should return correct`() {
        //Given
        val url = TestUtil.getPathForMethod(UserInfoApi::getUserInfo, UserInfoApi::class.java)
        val expectedUserDetails = userInfoRepository.findOne(1)
        //When
        mvc.perform(get(url))
                //Then
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.firstName", Matchers.`is`(expectedUserDetails.firstName)))
                .andExpect(jsonPath("$.lastName", Matchers.`is`(expectedUserDetails.lastName)))
    }

    @Test
    fun `2updateUserInfo correct`() {
        //Given
        val url = TestUtil.getPathForMethod(UserInfoApi::updateUserInfo, UserInfoApi::class.java)
        val body = TestUtil.convertObjectToJson(UserInfoStub.getCorrectUpdateUserInfoDTO())
        //When
        val response = mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(body))
                .andExpect(status().isOk)
                .andReturn()
                .response
        //Then
        val responseBody: UserInfoDTO = TestUtil.convertJsonToObject(response.contentAsString)
        val actualUserDetails = userInfoRepository.findOne(1)
        Assert.assertTrue(UserInfoDTO(actualUserDetails) == responseBody)
    }

    @Test
    fun `2updateUserInfo wrong postal`() {
        //Given
        val url = TestUtil.getPathForMethod(UserInfoApi::updateUserInfo, UserInfoApi::class.java)
        var userInfoDTO = UserInfoStub.getCorrectUpdateUserInfoDTO()
        userInfoDTO.postalCode = "55555"
        val body = TestUtil.convertObjectToJson(userInfoDTO)
        //When
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(body))
                //Then
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("$.message", Matchers.`is`("Wrong postal code format")))

    }
}