package no.nowak.gpstracker.core.user

import no.nowak.gpstracker.TestUtil
import no.nowak.gpstracker.core.userDetails.UserDetailsRepository
import no.nowak.gpstracker.stubs.UserStub
import org.bouncycastle.crypto.tls.ContentType
import org.hamcrest.Matchers
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@RunWith(SpringRunner::class)
@ActiveProfiles("fakeAuthorizationService", "test", "dev")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserIntegrationTest {

    @Autowired
    lateinit var applicationContext: WebApplicationContext

    @Autowired
    lateinit var userDetailsRepository: UserDetailsRepository

    lateinit var mvc: MockMvc

    @Before
    fun setup() {
        this.mvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply<DefaultMockMvcBuilder>(SecurityMockMvcConfigurers.springSecurity())
                .build()
    }

    @Test
    fun `register user should return conflict`() {
        //Given
        val userRegisterDTO = UserStub.getCorrectUserRegisterDTO()
        userRegisterDTO.email = "test@test.pl"
        val url = TestUtil.getPathForMethod(UserApi::registerUser, UserApi::class.java)
        val body = TestUtil.convertObjectToJson(userRegisterDTO)
        //When
        mvc.perform(post(url)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                //Then
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isConflict)
                .andExpect(jsonPath("$.message", Matchers.`is`("User with this email exists")))
    }

    @Test
    fun `getUserDetails should return correct`() {
        //Given
        val url = TestUtil.getPathForMethod(UserApi::getUserDetails, UserApi::class.java)
        val expectedUserDetails = userDetailsRepository.findOne(1)
        //When
        mvc.perform(get(url))
                //Then
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.id", Matchers.`is`(expectedUserDetails.id)))
                .andExpect(jsonPath("$.firstName", Matchers.`is`(expectedUserDetails.firstName)))
                .andExpect(jsonPath("$.lastName", Matchers.`is`(expectedUserDetails.lastName)))
    }
}