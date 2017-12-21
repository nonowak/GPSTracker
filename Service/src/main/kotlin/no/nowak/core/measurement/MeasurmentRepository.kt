package no.nowak.core.measurement

import org.springframework.data.jpa.repository.JpaRepository

interface MeasurmentRepository<T: Measurement>: JpaRepository<T, Int> {
}