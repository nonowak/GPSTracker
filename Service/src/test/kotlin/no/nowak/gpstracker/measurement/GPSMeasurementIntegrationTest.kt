package no.nowak.gpstracker.measurement

import com.google.maps.model.LatLng
import no.nowak.TestUtil.convertObjectToJson
import no.nowak.TestUtil.getPathForMethod
import no.nowak.core.device.DeviceRepository
import no.nowak.gpstracker.google.GoogleAddress
import no.nowak.stubs.MeasurementStub
import org.junit.Assert
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import java.time.LocalDate
import java.time.LocalTime

@RunWith(SpringRunner::class)
@ActiveProfiles("authorizationService", "test", "dev")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GPSMeasurementIntegrationTest {

    @Autowired
    lateinit var applicationContext: WebApplicationContext

    @Autowired
    lateinit var measurementRepository: MeasurementGPSRepository

    @Autowired
    lateinit var deviceRepository: DeviceRepository

    lateinit var mvc: MockMvc

    @Before
    fun setup() {
        this.mvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply<DefaultMockMvcBuilder>(SecurityMockMvcConfigurers.springSecurity())
                .build()
    }

    //    @Ignore("PreAuthorize problems")
    @Test
    fun `addMeasurement Unauthorized`() {
        //Given
        val url = getPathForMethod(MeasurementGPSApi::addMeasurement, MeasurementGPSApi::class.java)
        val token = "1111-1111"
        val body = convertObjectToJson(MeasurementStub.getCorrectMeasurementGPSDTO())
        //When
        mvc.perform(post(url, token)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                //Then
                .andExpect(status().isUnauthorized)
    }

    @Test
    fun `addMeasurement ok`() {
        //Given
        val url = getPathForMethod(MeasurementGPSApi::addMeasurement, MeasurementGPSApi::class.java)
        val token = "h1rQ-BWZ9"
        val body = convertObjectToJson(MeasurementStub.getCorrectMeasurementGPSDTO())
        //When
        mvc.perform(post(url, token)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated).andReturn().response
        //Then
        val measurement = measurementRepository.findByDevice_DeviceDictionary_TokenAndTimeBetweenAndMeasurementDate_Date("h1rQ-BWZ9", LocalTime.of(11, 42), LocalTime.of(11, 42), LocalDate.of(2017, 12, 23))
        val expectedLatLng = LatLng(MeasurementStub.getCorrectMeasurementGPSDTO().latitude, MeasurementStub.getCorrectMeasurementGPSDTO().longitude)
        val expectedGoogleAddress = GoogleAddress(countryName = "Poland", cityName = "Psary Polskie", streetName = "193A")
        Assert.assertTrue(expectedLatLng == measurement.latLng)
        Assert.assertTrue(expectedGoogleAddress == measurement.address)
    }
}