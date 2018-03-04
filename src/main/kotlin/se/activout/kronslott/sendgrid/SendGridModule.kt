package se.activout.kronslott.sendgrid

import com.sendgrid.SendGrid
import dagger.Module
import dagger.Provides


@Module
class SendGridModule(private val sendGridSettings: SendGridSettings) {
    @Provides
    fun provideSendGrid(): SendGrid = SendGrid(sendGridSettings.apiKey.toString(), sendGridSettings.test)
}