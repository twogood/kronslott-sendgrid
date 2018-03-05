package se.activout.kronslott.sendgrid

import io.dropwizard.Configuration
import io.dropwizard.setup.Environment
import com.example.mockito.MockitoExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock

class TestConfiguration() : Configuration(), SendGridConfig {
    override var sendGridSettings: SendGridSettings = SendGridSettings()
}

@ExtendWith(MockitoExtension::class)
class SendGridBundleTest {

    @Mock
    private lateinit var environment: Environment

    private lateinit var bundle: SendGridBundle<TestConfiguration>

    private lateinit var config: TestConfiguration

    @BeforeEach
    fun before() {
        bundle = SendGridBundle()
        config = TestConfiguration()
    }

    @Test
    fun testSendGridComponent() {
        // given
        config.sendGridSettings = SendGridSettings(apiKey = SendGridApiKey("*API*KEY*"))

        // when
        bundle.run(config, environment)

        // then
        assertEquals("api.sendgrid.com", bundle.sendGridComponent.sendGrid().host)
        bundle.sendGridComponent.mailSendingService()
    }
}