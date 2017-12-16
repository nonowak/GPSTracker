package no.nowak.core.infrastructure.security.authorizationService

import no.nowak.core.user.User

interface AuthorizationService {
    fun getCurrentUser(): User
}