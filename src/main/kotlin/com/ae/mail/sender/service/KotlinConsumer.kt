package com.ae.mail.sender.service

import com.ae.mail.sender.model.Message
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class KotlinConsumer(private val mailService: MailService) {

  private val mapper = ObjectMapper().registerModule(KotlinModule())
  private val logger = LoggerFactory.getLogger(javaClass)

  @KafkaListener(topics = ["simple-message-topic"], groupId = "simple-kotlin-consumer", autoStartup = "\${custom-configs.auto-start:true}")
  fun processMessage(message: String) {
    logger.info("got message: {}", message)
    val mailMessage: Message = mapper.readValue(message)
    mailService.sendMail(
                          mailMessage.userId,
                          mailMessage.mailAddress,
                          mailMessage.isSuccess.toBoolean(),
                          mailMessage.orderDetails,
                          mailMessage.totalCost
                        )
  }
}

