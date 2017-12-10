package no.nowak.gpstracker.core.infrastructure.mail

import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import java.util.concurrent.Executors
import javax.mail.MessagingException

@Service
class EmailSender(private val javaMailSender: JavaMailSender) {

    @Value("\${spring.mail.username}")
    private val sender: String = ""

    fun sendMail(subject: String, content: String, recipient: String) {
        val mail = javaMailSender.createMimeMessage()
        try {
            val helper = MimeMessageHelper(mail)
            helper.setFrom(sender)
            helper.setSubject(subject)
            helper.setText(content, true)
            helper.setTo(recipient)
        } catch (e: MessagingException) {
            e.printStackTrace()
        }
        val executor = Executors.newSingleThreadExecutor()
        executor.execute({ javaMailSender.send(mail) })
        executor.shutdown()

    }
}