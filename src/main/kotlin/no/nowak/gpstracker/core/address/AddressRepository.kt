package no.nowak.gpstracker.core.address

import org.springframework.data.jpa.repository.JpaRepository

interface AddressRepository : JpaRepository<Address, Long> {
}

interface CountryRepository : JpaRepository<Country, Long> {
}