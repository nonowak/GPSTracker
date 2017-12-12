package no.nowak.gpstracker.core.infrastructure.mail

import no.nowak.gpstracker.core.infrastructure.Paths.USER_PATH
import no.nowak.gpstracker.core.user.User
import no.nowak.gpstracker.core.user.UserApi.Companion.ACTIVATION_KEY
import no.nowak.gpstracker.core.user.UserApi.Companion.ACTIVATION_PATH
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context

@Service
class MailService(private val emailSender: EmailSender,
                  private val templateEngine: TemplateEngine) {

    @Value("\${front.protocol}://\${front.hostname}:\${front.port}")
    private val webLink: String = ""

    fun register(user: User) {
        val context = Context()
        val activationLink = "$webLink$USER_PATH$ACTIVATION_PATH?$ACTIVATION_KEY=${user.activationKey}"
        context.setVariable("activationLink", activationLink)
        val body = templateEngine.process("activateAccount", context)
        emailSender.sendMail("Activate your account", body, user.emailAddress)
    }

    fun lostPassword(user: User) {
        val context = Context()
        val resetPasswordLink = "$webLink$USER_PATH"
        context.setVariable("resetPasswordLink", resetPasswordLink)
        val body = templateEngine.process("resetPassword", context)
        emailSender.sendMail("Reset your password", body, user.emailAddress)
    }
}