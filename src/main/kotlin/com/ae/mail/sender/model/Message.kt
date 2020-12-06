package com.ae.mail.sender.model

import com.fasterxml.jackson.annotation.JsonInclude
import java.io.Serializable

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Message(
        val userId: String,
        val mailAddress: String,
        val isSuccess: String,
        val orderDetails: String,
        val totalCost: String
) : Serializable {
    companion object {
        const val serialVersionUID: Long = 1L
    }
}
