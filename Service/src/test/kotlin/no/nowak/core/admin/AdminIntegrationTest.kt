package no.nowak.core.admin

import no.nowak.TestUtil.convertJsonToObject
import no.nowak.TestUtil.convertObjectToJson
import no.nowak.TestUtil.getPathForMethod
import no.nowak.core.admin.dto.DeviceDictionaryDTO
import no.nowak.core.device.DeviceType
import no.nowak.core.deviceDictionary.DeviceDictionaryRepository
import no.nowak.stubs.DeviceDictionaryStub
import no.nowak.stubs.DeviceStub
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@RunWith(SpringRunner::class)
@ActiveProfiles("adminFakeAuthorizationService", "test", "dev")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class AdminIntegrationTest {

    @Autowired
    lateinit var applicationContext: WebApplicationContext

    @Autowired
    lateinit var deviceDictionaryRepository: DeviceDictionaryRepository

    lateinit var mvc: MockMvc

    @Before
    fun setup() {
        this.mvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply<DefaultMockMvcBuilder>(SecurityMockMvcConfigurers.springSecurity())
                .build()
    }

    @Test
    fun `addDevice correct`() {
        //Given
        val url = getPathForMethod(AdminApi::addDevice, AdminApi::class.java)
        val deviceDTO = DeviceDictionaryStub.getCorrectDeviceDTO()
        val body = convertObjectToJson(deviceDTO)
        //When
        val response = mvc.perform(post(url)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated)
                .andReturn()
                .response
        //Then
        val savedDeviceDTO = DeviceDictionaryDTO(deviceDictionaryRepository.findOne(2))
        val responseBody: DeviceDictionaryDTO = convertJsonToObject(response.contentAsString)
        Assert.assertTrue(savedDeviceDTO == responseBody)
        Assert.assertEquals(DeviceType.GPSTRACKER, responseBody.deviceType)
        Assert.assertEquals("admin@test.pl", responseBody.createdByEmailAddress)
        Assert.assertTrue(responseBody.token!!.matches("[A-Za-z0-9]{4}-[A-Za-z0-9]{4}".toRegex()))
    }

    @Test
    fun `getDevices correct`() {
        //Given
        val url = getPathForMethod(AdminApi::getAllDevices, AdminApi::class.java)
        //When
        val response = mvc.perform(get(url)).andReturn().response
        //Then
        val responseBody: List<DeviceDictionaryDTO> = convertJsonToObject(response.contentAsString)
        Assert.assertEquals(2, responseBody.size)
        Assert.assertTrue(responseBody.none { it.createdByEmailAddress == null || it.createdByEmailAddress == null })
    }
}