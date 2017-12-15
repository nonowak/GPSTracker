package no.nowak.core.userDetails

import no.nowak.TestUtil
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
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
        mvc.perform(MockMvcRequestBuilders.get(url))
                //Then
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.`is`(expectedUserDetails.id)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.`is`(expectedUserDetails.firstName)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", Matchers.`is`(expectedUserDetails.lastName)))
    }
}