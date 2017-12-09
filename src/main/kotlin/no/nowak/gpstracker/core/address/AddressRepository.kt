package no.nowak.gpstracker.core.address

import org.springframework.data.jpa.repository.JpaRepository

interface AddressRepository : JpaRepository<Address, Int> {
}

interface CountryRepository : JpaRepository<Country, Int> {
}