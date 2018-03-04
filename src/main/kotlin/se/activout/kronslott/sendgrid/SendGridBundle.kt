package se.activout.kronslott.sendgrid

import io.dropwizard.ConfiguredBundle
import io.dropwizard.setup.Bootstrap
import io.dropwizard.setup.Environment

data class SendGridSettings(val apiKey: SendGridApiKey? = null, val test: Boolean = false)


interface SendGridConfig {
    var sendGridSettings: SendGridSettings
}

class SendGridBundle<C : SendGridConfig> : ConfiguredBundle<C> {

    lateinit var sendGridComponent: SendGridComponent

    override fun initialize(bootstrap: Bootstrap<*>) {
        // deliberately empty
    }

    override fun run(configuration: C, environment: Environment) {
        sendGridComponent = DaggerSendGridComponent.builder()
                .sendGridModule(SendGridModule(configuration.sendGridSettings))
                .build()
    }
}