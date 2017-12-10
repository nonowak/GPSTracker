package no.nowak.gpstracker.core.infrastructure.mail

import no.nowak.gpstracker.core.user.User
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context

@Service
class MailService(private val emailSender: EmailSender,
                  private val templateEngine: TemplateEngine) {

    @Value("\${front.protocol}://\${front.hostname}:\${front:port}")
    private val webLink: String = ""

    fun register(user: User) {
        val context = Context()
        val activationLink = "$webLink/register?activationKey=${user.userDetails.activationKey}"
        context.setVariable("activationLink", activationLink)
        val body = templateEngine.process("activateAccount", context)
        emailSender.sendMail("Activate your account", body, user.emailAddress)
    }
}