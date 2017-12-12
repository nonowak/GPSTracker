package no.nowak.gpstracker.core.user

import no.nowak.gpstracker.TestUtil
import no.nowak.gpstracker.stubs.UserStub
import org.hamcrest.Matchers
import org.junit.Assert
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@RunWith(SpringRunner::class)
@ActiveProfiles("fakeAuthorizationService", "test", "dev")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class UserIntegrationTest {

    @Autowired
    lateinit var applicationContext: WebApplicationContext

    @Autowired
    lateinit var userRepository: UserRepository

    lateinit var mvc: MockMvc

    @Before
    fun setup() {
        this.mvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply<DefaultMockMvcBuilder>(SecurityMockMvcConfigurers.springSecurity())
                .build()
    }

    @Test
    fun `1registerUser should return conflict`() {
        //Given
        val userRegisterDTO = UserStub.getCorrectUserRegisterDTO()
        userRegisterDTO.emailAddress = "test@test.pl"
        val url = TestUtil.getPathForMethod(UserApi::registerUser, UserApi::class.java)
        val body = TestUtil.convertObjectToJson(userRegisterDTO)
        //When
        mvc.perform(post(url)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                //Then
                .andExpect(status().isConflict)
                .andExpect(jsonPath("$.message", Matchers.`is`("User with this email exists")))
    }

    @Test
    fun `11registerUser should return wrong password`() {
        //Given
        val userRegisterDTO = UserStub.getCorrectUserRegisterDTO()
        userRegisterDTO.password = "12345678"
        val url = TestUtil.getPathForMethod(UserApi::registerUser, UserApi::class.java)
        val body = TestUtil.convertObjectToJson(userRegisterDTO)
        //When
        mvc.perform(post(url)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                //Then
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("$.message", Matchers.`is`("Password must contains at least one number, one upper case and be 8")))
    }

    @Test
    fun `2registerUser correct`() {
        //Given
        val url = TestUtil.getPathForMethod(UserApi::registerUser, UserApi::class.java)
        val userRegisterDTO = UserStub.getCorrectUserRegisterDTO()
        val body = TestUtil.convertObjectToJson(userRegisterDTO)
        //When
        mvc.perform(post(url)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                //Then
                .andExpect(status().isCreated)

        val actualUsers = userRepository.findAll()
        Assert.assertTrue(actualUsers.any { it.emailAddress == userRegisterDTO.emailAddress })
    }

    @Test
    fun `3activateUser correct`() {
        //Given
        val url = TestUtil.getPathForMethod(UserApi::activateUser, UserApi::class.java)
        val user = userRepository.findByEmailAddress(UserStub.getCorrectUserRegisterDTO().emailAddress)!!
        val activationKey = user.activationKey
        //When
        mvc.perform(get(url)
                .param(UserApi.ACTIVATION_KEY, activationKey))
                .andExpect(status().isOk)
        //Then
        val actualUser = userRepository.findByEmailAddress(UserStub.getCorrectUserRegisterDTO().emailAddress)!!
        Assert.assertEquals(true, actualUser.enabled)
    }

    @Test
    fun `4activateUser user is enabled`() {
        //Given
        val url = TestUtil.getPathForMethod(UserApi::activateUser, UserApi::class.java)
        val user = userRepository.findByEmailAddress(UserStub.getCorrectUserRegisterDTO().emailAddress)!!
        val activationKey = user.activationKey
        //When
        mvc.perform(get(url)
                .param(UserApi.ACTIVATION_KEY, activationKey))
                //Then
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("$.message", Matchers.`is`("User is enabled")))
    }

    @Test
    fun `5activateUser wrong activation key`() {
        //Given
        val url = TestUtil.getPathForMethod(UserApi::activateUser, UserApi::class.java)
        val user = userRepository.findByEmailAddress(UserStub.getCorrectUserRegisterDTO().emailAddress)!!
        val activationKey = user.activationKey + "wrong"
        //When
        mvc.perform(get(url)
                .param(UserApi.ACTIVATION_KEY, activationKey))
                //Then
                .andExpect(status().isNotFound)
                .andExpect(jsonPath("$.message", Matchers.`is`("User with this activationKey not found")))
    }
}