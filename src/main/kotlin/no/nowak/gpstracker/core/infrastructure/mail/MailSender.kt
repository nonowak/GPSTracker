package no.nowak.gpstracker.core.infrastructure.mail

import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMailMessage
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service

@Service
class MailSender(val javaMailSender: JavaMailSender){

    @Value("\${spring.mail.username}")
    val sender: String = ""

    fun sendMail(subject: String, content: String, recipient: String){
        val mail = javaMailSender.createMimeMessage()
        var helper = MimeMessageHelper(mail)
        helper.setFrom(sender)
        helper.setSubject(subject)
        helper.setText(content)
        helper.setTo(recipient)
        javaMailSender.send(mail)
    }
}