package com.ae.mail.sender.controller

import com.ae.mail.sender.service.MailService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RequestMapping(produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
@RestController
class OrdersController(private val mailService: MailService) {

    @PostMapping(value = [MAILS])
    fun sendMessage(@RequestHeader(USER_ID) userId: String,
                  @RequestParam("mailAddress", defaultValue = true.toString()) mailAddress: String,
                  @RequestParam("isSuccess", defaultValue = true.toString()) isSuccess: Boolean,
                  @RequestParam("orderDetails", defaultValue = "") orderDetails: String,
                  @RequestParam("totalCost", defaultValue = "") totalCost: String
    ) = mailService.sendMail(userId, mailAddress, isSuccess, orderDetails, totalCost)


    companion object {
        const val MAILS = "/mails"
        const val USER_ID = "USER_ID"
    }
}