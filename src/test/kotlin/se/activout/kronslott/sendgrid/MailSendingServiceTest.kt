package se.activout.kronslott.sendgrid

import com.nhaarman.mockitokotlin2.*
import com.sendgrid.*
import mockito.MockitoExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock

@ExtendWith(MockitoExtension::class)
class MailSendingServiceTest {

    @Mock
    lateinit var client: Client

    private lateinit var sendGrid: SendGrid

    @BeforeEach
    fun beforeEach() {
        whenever(client.api(any())).thenReturn(Response())

        sendGrid = spy(SendGrid("*API*KEY*", client))
    }

    @Test
    fun testSendEmail() {
        // given
        val mailSendingService = MailSendingService(sendGrid)

        // when
        mailSendingService.sendMail(Email("from@example.com"), "*SUBJECT*", Email("to@example.com"), Content("text/html", "*HTML*"))

        // then
        verify(client, times(1)).api(any())
    }

}