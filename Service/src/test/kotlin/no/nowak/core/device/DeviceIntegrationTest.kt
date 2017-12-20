package no.nowak.core.device

import no.nowak.TestUtil.convertJsonToObject
import no.nowak.TestUtil.convertObjectToJson
import no.nowak.TestUtil.getPathForMethod
import no.nowak.core.device.dto.DeviceDTO
import no.nowak.core.deviceDictionary.DeviceDictionaryRepository
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@RunWith(SpringRunner::class)
@ActiveProfiles("userFakeAuthorizationService", "test", "dev")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DeviceIntegrationTest {

    @Autowired
    lateinit var applicationContext: WebApplicationContext

    @Autowired
    lateinit var deviceRepository: DeviceRepository

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
        val url = getPathForMethod(DeviceApi::addDevice, DeviceApi::class.java)
        val addDeviceDTO = DeviceStub.getCorrectUserDeviceDTO()
        val body = convertObjectToJson(addDeviceDTO)
        //When
        val response = mvc.perform(post(url)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated)
                .andReturn()
                .response
        //Then
        val responseBody: Map<DeviceType, DeviceDTO> = convertJsonToObject(response.contentAsString)
        val deviceDictionary = deviceDictionaryRepository.findOne(1)
        val device = deviceRepository.findOne(1)
        Assert.assertTrue(responseBody.values.any { it.token == deviceDictionary.token })
        Assert.assertTrue(responseBody.values.any { it.permission == Permission.OWNER })
        Assert.assertTrue(deviceDictionary.enabled)
        Assert.assertEquals(DeviceType.GPSTRACKER, device.deviceType)
    }

    @Test
    fun `addDevice already added`() {
        //Given
        val url = getPathForMethod(DeviceApi::addDevice, DeviceApi::class.java)
        val addDeviceDTO = DeviceStub.getCorrectUserDeviceDTO()
        val body = convertObjectToJson(addDeviceDTO)
        //When
        mvc.perform(post(url)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                //Then
                .andExpect(status().isConflict)
                .andExpect(jsonPath("$.message", Matchers.`is`("Device is enabled")))
    }

    @Test
    fun `addDevice device not found`() {
        //Given
        val url = getPathForMethod(DeviceApi::addDevice, DeviceApi::class.java)
        val addDeviceDTO = DeviceStub.getCorrectUserDeviceDTO()
        addDeviceDTO.token = "1111-1190"
        val body = convertObjectToJson(addDeviceDTO)
        //When
        mvc.perform(post(url)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                //Then
                .andExpect(status().isNotFound)
                .andExpect(jsonPath("$.message", Matchers.`is`("Device not found")))
    }

    @Test
    fun `addDevice invalid token`() {
        //Given
        val url = getPathForMethod(DeviceApi::addDevice, DeviceApi::class.java)
        val addDeviceDTO = DeviceStub.getCorrectUserDeviceDTO()
        addDeviceDTO.token = "11111111"
        val body = convertObjectToJson(addDeviceDTO)
        //When
        mvc.perform(post(url)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                //Then
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("$.message", Matchers.`is`("Invalid deviceDictionary")))
    }

}