package se.activout.kronslott.sendgrid

import com.sendgrid.SendGrid
import dagger.Component;

@Component(modules = [SendGridModule::class])
interface SendGridComponent {
    fun sendGrid(): SendGrid
    fun mailSendingService(): MailSendingService
}