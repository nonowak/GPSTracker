package no.nowak.stubs

import no.nowak.gpstracker.measurement.dto.MeasurementGPSDTO

class MeasurementStub {
    companion object {
        fun getCorrectMeasurementGPSDTO() =
                MeasurementGPSDTO(
                        latitude = 52.343623300000004,
                        longitude = 17.5344841,
                        measurementTime = "20171223114200"
                )
    }
}