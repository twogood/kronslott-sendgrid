Kronslott Project
# DropWizard SendGrid bundle
DropWizard bundle to set the [SendGrid](https://github.com/sendgrid/sendgrid-java) 
API key in your DropWizard configuration and expose the SendGrid object through
[Dagger](https://google.github.io/dagger/).

A simple mail sending service is also exposed via Dagger.

## Usage

### example.yml

```yaml
sendGrid:
  apiKey: ...
```

### example.kt

This also shows how I add Dagger to DropWizard: I run bootstrap.addBundle() 
with an object expression (anonymous class) that instantiates the main
Dagger component for the application and sets it as a property of the 
DropWizard application.

```kotlin
@Singleton
@Component(dependencies = [SendGridComponent::class], modules = [/*...*/])
interface ExampleComponent {
    // ...
}

class ExampleConfig : Configuration(), SendGridConfig {
    @Valid
    @JsonProperty("sendGrid")
    override var sendGridSettings: SendGridSettings = SendGridSettings()
   
    // ...
}

class ExampleApp : Application<ExampleConfig>() {

    private lateinit var component: ExampleComponent

    override fun initialize(bootstrap: Bootstrap<ExampleConfig>) {
        val sendGridBundle = SendGridBundle<ExampleConfig>()
        bootstrap.addBundle(sendGridBundle)

        bootstrap.addBundle(object : ConfiguredBundle<Configuration> {

            override fun run(configuration: Configuration, environment: Environment) {
                component = DaggerExampleComponent.builder()
                        .exampleModule(ExampleModule(configuration as ExampleConfig, environment))
                        .sendGridComponent(sendGridBundle.sendGridComponent)
                        .build()
            }

            override fun initialize(bootstrap: Bootstrap<*>) {}
        })

        // ...
    }
    
    // ...
}    

// Class that needs SendGrid
class ExampleService1 @Inject constructor(private val sendGrid: SendGrid) {
    // ...
}

// Class that needs the MailSendingService
class ExampleService2 @Inject constructor(private val mailSendingService: MailSendingService) {
    
    fun sendMail() {
        mailSendingService.sendMail(Email("from@example.com"), "*SUBJECT*", Email("to@example.com"), Content("text/html", "*HTML*"))
    }
    
}

```

## Adding this library to your project

This project is not yet available from Maven Central Repository, but it's 
available via
[JitPack.io](https://jitpack.io/#twogood/kronslott-sendgrid).
