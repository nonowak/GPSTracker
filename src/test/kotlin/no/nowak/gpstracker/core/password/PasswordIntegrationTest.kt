package no.nowak.gpstracker.core.password

import no.nowak.gpstracker.TestUtil.convertObjectToJson
import no.nowak.gpstracker.TestUtil.getPathForMethod
import no.nowak.gpstracker.stubs.PasswordStub
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime

@RunWith(SpringRunner::class)
@ActiveProfiles("userFakeAuthorizationService", "test", "dev")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class PasswordIntegrationTest {

    @Autowired
    lateinit var applicationContext: WebApplicationContext

    @Autowired
    lateinit var passwordRepository: PasswordRepository

    lateinit var mvc: MockMvc

    @Before
    fun setup() {
        this.mvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply<DefaultMockMvcBuilder>(SecurityMockMvcConfigurers.springSecurity())
                .build()
    }

    @Test
    fun `1lostPassword correct`() {
        //Given
        val url = getPathForMethod(PasswordApi::lostPassword, PasswordApi::class.java)
        val emailAddress = "test@test.pl"
        //When
        mvc.perform(get(url)
                .param(PasswordApi.EMAIL_ADDRESS, emailAddress))
                .andExpect(status().isOk)
        //Then
        val actualPassword = passwordRepository.findOne(1)
        Assert.assertNotNull(actualPassword.resetKey)
    }

    @Test
    fun `2resetPassword correct`() {
        //Given
        val url = getPathForMethod(PasswordApi::resetPassword, PasswordApi::class.java)
        val resetPasswordKey = passwordRepository.findOne(1).resetKey
        val resetPasswordDTO = PasswordStub.getCorrectResetPasswordDTO()
        val body = convertObjectToJson(resetPasswordDTO)
        //When
        mvc.perform(put(url)
                .param(PasswordApi.RESET_PASSWORD_KEY, resetPasswordKey)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk)
        //Then
        val actualPassword = passwordRepository.findOne(1)
        Assert.assertEquals(LocalDateTime.now().toLocalDate(), actualPassword.modifiedOn?.toLocalDate())
        Assert.assertTrue(BCryptPasswordEncoder().matches(resetPasswordDTO.password, actualPassword.currentHash))
        Assert.assertTrue(BCryptPasswordEncoder().matches("TesT123456", actualPassword.previousHash))
        Assert.assertEquals("", actualPassword.resetKey)
    }

    @Test
    fun `3resetPassword user with this resetPasswordKey not found`() {
        //Given
        val url = getPathForMethod(PasswordApi::resetPassword, PasswordApi::class.java)
        val resetPasswordKey = "not-present-user"
        val resetPasswordDTO = PasswordStub.getCorrectResetPasswordDTO()
        val body = convertObjectToJson(resetPasswordDTO)
        //When
        mvc.perform(put(url)
                .param(PasswordApi.RESET_PASSWORD_KEY, resetPasswordKey)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                //Then
                .andExpect(status().isNotFound)
                .andExpect(jsonPath("$.message", Matchers.`is`("Password for this resetKey and user emailAddress not found")))
    }

    @Test
    fun `4lostPassword correct`() {
        //Given
        val url = getPathForMethod(PasswordApi::lostPassword, PasswordApi::class.java)
        val emailAddress = "test@test.pl"
        //When
        mvc.perform(get(url)
                .param(PasswordApi.EMAIL_ADDRESS, emailAddress))
                .andExpect(status().isOk)
        //Then
        val actualPassword = passwordRepository.findOne(1)
        Assert.assertNotNull(actualPassword.resetKey)
    }

    @Test
    fun `5resetPassword user hash the same as current`() {
        //Given
        val url = getPathForMethod(PasswordApi::resetPassword, PasswordApi::class.java)
        val resetPasswordKey = passwordRepository.findOne(1).resetKey
        val resetPasswordDTO = PasswordStub.getCorrectResetPasswordDTO()
        val body = convertObjectToJson(resetPasswordDTO)
        //When
        mvc.perform(put(url)
                .param(PasswordApi.RESET_PASSWORD_KEY, resetPasswordKey)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                //Then
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("$.message", Matchers.`is`("Password is the same as your current")))
    }

    @Test
    fun `6resetPassword user hash the same as previous`() {
        //Given
        val url = getPathForMethod(PasswordApi::resetPassword, PasswordApi::class.java)
        val resetPasswordKey = passwordRepository.findOne(1).resetKey
        val resetPasswordDTO = PasswordStub.getCorrectResetPasswordDTO()
        resetPasswordDTO.password = "TesT123456"
        val body = convertObjectToJson(resetPasswordDTO)
        //When
        mvc.perform(put(url)
                .param(PasswordApi.RESET_PASSWORD_KEY, resetPasswordKey)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                //Then
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("$.message", Matchers.`is`("Password is the same as your previous")))
    }
}