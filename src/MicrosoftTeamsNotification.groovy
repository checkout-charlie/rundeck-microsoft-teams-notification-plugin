import  com.dtolabs.rundeck.plugins.notification.NotificationPlugin

rundeckPlugin(NotificationPlugin){
    title="Microsoft Teams notification Plugin"
    description="Allows to set up notification for Microsoft Teams chats for a channel, via Webhook URL. To use it you will have to obtain webhook for your channel first and setit up."

    configuration{
        webhook_url title:"Webhook URL", required: true, type:"String", description:"You may find it in Microsoft Teams Channel user interfaces by using Incomming Webhook connector via:  Channel Name -> Connectors -> Incomming Webhook"
    }

    onstart { Map executionData,Map config ->
        process = [ 'bash', '-c', "curl -v -k -X POST -H \"Content-Type: application/json\" -d '{\"text\": \"START job: #${execution.id}: ${execution.project} ${execution.status} at ${execution.dateEnded} - ${execution.href} \"}' ${configuration.webhook_url}" ].execute().text
        true
    }

    onfailure { Map executionData ->
        //Single argument, the configuration properties are available automatically
        process = [ 'bash', '-c', "curl -v -k -X POST -H \"Content-Type: application/json\" -d '{\"text\": \"FAILURE job: #${execution.id}: ${execution.project} ${execution.status} at ${execution.dateEnded} - ${execution.href} \"}' ${configuration.webhook_url}" ].execute().text
        true
    }

    onsuccess {
        //with no args, there is a "configuration" and an "execution" variable in the context
        process = [ 'bash', '-c', "curl -v -k -X POST -H \"Content-Type: application/json\" -d '{\"text\": \"SUCCESS job: #${execution.id}: ${execution.project} ${execution.status} at ${execution.dateEnded} - ${execution.href} \"}' ${configuration.webhook_url}" ].execute().text

        true
    }
}
