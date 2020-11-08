package com.ae.mail.sender.service

import org.springframework.core.io.ClassPathResource
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import java.io.IOException
import javax.mail.MessagingException
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

@Service
class MailService(private val javaMailSender: JavaMailSender) {

    fun sendMail(userId: String, mailAddress: String, isSuccess: Boolean, orderDetails: String, totalCost: String): String {

        try {
            if (orderDetails.isBlank() and totalCost.isBlank()) sendTechnicalMessage(userId, mailAddress)
            else sendEmailWithAttachment(userId, mailAddress, isSuccess, orderDetails, totalCost)
        } catch (e: Exception) {
            e.multicatch(MessagingException::class, IOException::class) {
                e.printStackTrace()
            }
        }

        return "Email sent successfully"
    }

    @Throws(MessagingException::class, IOException::class)
    fun sendEmailWithAttachment(userId: String, mailAddress: String,  isSuccess: Boolean, orderDetails: String, totalCost: String) {
        val msg = javaMailSender.createMimeMessage()

        // true = multipart message
        val helper = MimeMessageHelper(msg, true)
        var message = ""
        if (!isSuccess) {
            message = String.format("<h2>Thank you for placing order, %s!</h2><h3>Your order is</h3><p>%s</p>" +
                    "<p>Total cost is %s</p><h3>These goods are out of stock</h3><p>Sincerely yours \"Our SHOP\"</p>",
                    userId, orderDetails, totalCost)
        } else {
            message = String.format("<h2>Thank you for placing order, %s!</h2><h3>Your order is</h3><p>%s</p>" +
                    "<p>Total cost is %s</p><p>Estimated delivery time is 24 hours</p><p>Sincerely yours \"Our SHOP\"</p>",
                    userId, orderDetails, totalCost)
        }
        helper.setTo(mailAddress)
        helper.setSubject("Order info from Orders app kotlin Spring Boot")

        helper.setText(message, true)

        helper.addAttachment("logo.png", ClassPathResource("android.png"))
        javaMailSender.send(msg)
    }

    fun sendTechnicalMessage(userId: String, mailAddress: String) {
        val msg = SimpleMailMessage()
        msg.setTo(mailAddress)
        msg.setSubject("Order place failure")
        msg.setText("Shop is down for 24 hours.")
        javaMailSender.send(msg)
    }

    fun <R> Throwable.multicatch(vararg classes: KClass<*>, block: () -> R): R {
        if (classes.any { this::class.isSubclassOf(it) }) {
            return block()
        } else throw this
    }

    companion object {
        val mapOffers = mapOf("apple" to 60, "orange" to 25)
    }

}