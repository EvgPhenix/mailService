package com.ae.mail.sender

import com.ae.mail.sender.controller.OrdersController.Companion.MAILS
import com.ae.mail.sender.controller.OrdersController.Companion.USER_ID
import com.ae.mail.sender.service.MailService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.nio.charset.Charset
import javax.mail.Session
import javax.mail.internet.MimeMessage

@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = [Application::class]
)
@AutoConfigureMockMvc
class ApplicationTests(@Autowired val mockMvc: MockMvc) {

	@MockBean
	private lateinit var mailService: MailService

	@MockBean
	private lateinit var javaMailSender: JavaMailSender

	private var mimeMessage: MimeMessage? = null

	@Test
	fun contextLoads() {
	}

	@Test
	fun `base mail test`() {
		mimeMessage = MimeMessage(null as Session?)
		javaMailSender = mock(JavaMailSender::class.java)
		`when`(javaMailSender!!.createMimeMessage()).thenReturn(mimeMessage)
		doNothing().`when`(javaMailSender!!).send(mimeMessage!!)
		`when`(javaMailSender!!.createMimeMessage()).thenReturn(mimeMessage)
		mailService = MailService(javaMailSender!!)
		val result = mailService?.sendMail("12345","", false, "", "")
		Assertions.assertEquals("Email sent successfully", result)
	}

	@Test
	fun `base mail test1`() {
		mimeMessage = MimeMessage(null as Session?)
		javaMailSender = mock(JavaMailSender::class.java)
		`when`(javaMailSender!!.createMimeMessage()).thenReturn(mimeMessage)
		doNothing().`when`(javaMailSender!!).send(mimeMessage!!)
		mailService = MailService(javaMailSender!!)
		val result = mailService?.sendMail("12345", "",true, "oranges=3", "$0.50")
		Assertions.assertEquals("Email sent successfully", result)
	}


	@Test
	fun `Must send normal mail`() {

		val mimeMessage = MimeMessage(null as Session?)
		javaMailSender = mock(JavaMailSender::class.java)

		`when`(javaMailSender!!.createMimeMessage()).thenReturn(mimeMessage)
		`when`(mailService!!.sendMail(
				anyString(),  anyString(), anyBoolean(), anyString(), anyString())).thenReturn("Email sent successfully")
		doNothing().`when`(javaMailSender!!).send(mimeMessage)

		val response = mockMvc
				.perform(post("$MAILS?orderDetails=apple=2,orange=3&totalCost=\$1.1").header(USER_ID, USER_ID))
		response
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn()
		Assertions.assertEquals(response.andReturn().response.getContentAsString(Charset.defaultCharset()), "Email sent successfully")
	}

}
