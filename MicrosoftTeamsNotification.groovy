import  com.dtolabs.rundeck.plugins.notification.NotificationPlugin

rundeckPlugin(NotificationPlugin){
    title="Microsoft Teams notification Plugin"
    description="""Allows to set up notification for Microsoft Teams chats for
a channel, via Webhook URL. To use it you will have to obtain webhook for your
channel first and setit up."""

    configuration{
        webhook_url title:"Webhook URL", type="String", description:"You may find it in Microsoft Teams Channel user interfaces by using Incomming Webhook connector  via:  Channel Name -> Connectors -> Incomming Webhook"
    }

    onstart { Map executionData,Map config ->
        println("script, start: data ${executionData}, config: ${config}")
        true
    }

    onfailure { Map executionData ->
        //Single argument, the configuration properties are available automatically
        println("script, failure: data ${executionData}, webhook_url: ${webhook_url}")
        true
    }

    onsuccess {
        //with no args, there is a "configuration" and an "execution" variable in the context
        println("script, success: data ${execution}, webhook_url: ${configuration.webhook_url}")
        true
    }
}
