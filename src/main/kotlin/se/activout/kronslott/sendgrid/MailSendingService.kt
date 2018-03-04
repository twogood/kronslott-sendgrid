package se.activout.kronslott.sendgrid

import com.sendgrid.*
import java.io.IOException
import javax.inject.Inject

class MailSendingException : RuntimeException {
    constructor(message: String, ex: Exception?) : super(message, ex) {}
    constructor(message: String) : super(message) {}
    constructor(ex: Exception) : super(ex) {}
}

class MailSendingService @Inject constructor(private val sendGrid: SendGrid) {

    fun sendMail(from: Email, subject: String, to: Email, content: Content) {
        val mail = Mail(from, subject, to, content)
        val request = Request()
        try {
            request.method = Method.POST
            request.endpoint = "mail/send"
            request.body = mail.build()
            val response = sendGrid.api(request)

            if (response.statusCode >= 400) {
                throw MailSendingException("API error ${response.statusCode}");
            }
        } catch (e: IOException) {
            throw MailSendingException(e)
        }

    }
}