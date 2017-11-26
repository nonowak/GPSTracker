package no.nowak.gpstracker.core.infrastructure.security.authorizationService

import no.nowak.gpstracker.core.user.User

interface AuthorizationService {
    fun getCurrentUser(): User
}