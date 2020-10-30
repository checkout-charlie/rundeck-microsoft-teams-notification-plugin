import com.dtolabs.rundeck.plugins.notification.NotificationPlugin
import groovy.json.JsonOutput


rundeckPlugin(NotificationPlugin){
    title="Microsoft Teams notification Plugin"
    description="Allows to set up notification for Microsoft Teams chats for a channel, via Webhook URL. To use it you will have to obtain webhook for your channel first and setit up."

    configuration{
        webhook_url title:"Webhook URL", required: true, type:"String", description:"You may find it in Microsoft Teams Channel user interfaces by using Incomming Webhook connector via:  Channel Name -> Connectors -> Incomming Webhook"
    }

    onstart {
        type = "START"
        color = "696969"
        json_payload = JsonOutput.toJson([
            title: "[${type}] Rundeck Job Notification - ${execution.project}:${execution.job.group}:${execution.job.name}",
            summary: "Rundeck Job Notification",
            text: "$job id: #${execution.job.id}, job project: ${execution.project}, job group: ${execution.job.group}, job name: ${execution.job.name}, job description: ${execution.job.description}, execution id: #${execution.id}, execution status: ${execution.status}, execution started at: ${execution.dateStarted}",
            themeColor: "${color}",
            potentialAction: [
                [
                    "@context": "http://schema.org",
                    "@type": "ViewAction",
                    name: "Seed job execution",
                    target: ["${execution.href}"]
                ]
            ]
        ])
        process = [ 'bash', '-c', "curl -v -k -X POST -H \"Content-Type: application/json\" -d '${json_payload}' ${configuration.webhook_url}" ].execute().text

        return true
    }

    onfailure {
        type = "FAILURE"
        color = "E81123"
        //Single argument, the configuration properties are available automatically
        json_payload = JsonOutput.toJson([
            title: "[${type}] Rundeck Job Notification - ${execution.project}:${execution.job.group}:${execution.job.name}",
            summary: "Rundeck Job Notification",
            text: "job id: #${execution.job.id}, job project: ${execution.job.project}, job group: ${execution.job.group}, job name: ${execution.job.name}, job description: ${execution.job.description}, execution id: #${execution.id}, execution status: ${execution.status}, execution started at: ${execution.dateStarted}, execution ended at: ${execution.dateEnded}",
            themeColor: "${color}",
            potentialAction: [
                [
                    "@context": "http://schema.org",
                    "@type": "ViewAction",
                    name: "Seed job execution",
                    target: ["${execution.href}"]
                ]
            ]
        ])
        process = [ 'bash', '-c', "curl -v -k -X POST -H \"Content-Type: application/json\" -d '${json_payload}' ${configuration.webhook_url}" ].execute().text

        return true
    }

    onsuccess {
        type = "SUCCESS"
        color = "228B22"
        //with no args, there is a "configuration" and an "execution" variable in the context
        json_payload = JsonOutput.toJson([
            title: "Rundeck Job Notification",
            title: "[${type}] Rundeck Job Notification - ${execution.project}:${execution.job.group}:${execution.job.name}",
            text: "job id: #${execution.job.id}, job project: ${execution.job.project}, job group: ${execution.job.group}, job name: ${execution.job.name}, job description: ${execution.job.description}, execution id: #${execution.id}, execution status: ${execution.status}, execution started at: ${execution.dateStarted}, execution ended at: ${execution.dateEnded}",
            themeColor: "${color}",
            potentialAction: [
                [
                    "@context": "http://schema.org",
                    "@type": "ViewAction",
                    name: "Seed job execution",
                    target: ["${execution.href}"]
                ]
            ]
        ])
        process = [ 'bash', '-c', "curl -v -k -X POST -H \"Content-Type: application/json\" -d '${json_payload}' ${configuration.webhook_url}" ].execute().text

        return true
    }
    
    onretryablefailure {
        type = "RETRY"
        color = "FFB900"
        //Single argument, the configuration properties are available automatically
        json_payload = JsonOutput.toJson([
            title: "[${type}] Rundeck Job Notification - ${execution.project}:${execution.job.group}:${execution.job.name}",
            summary: "Rundeck Job Notification",
            text: "job id: #${execution.job.id}, job project: ${execution.job.project}, job group: ${execution.job.group}, job name: ${execution.job.name}, job description: ${execution.job.description}, execution id: #${execution.id}, execution status: ${execution.status}, execution started at: ${execution.dateStarted}, execution ended at: ${execution.dateEnded}",
            themeColor: "${color}",
            potentialAction: [
                [
                    "@context": "http://schema.org",
                    "@type": "ViewAction",
                    name: "Seed job execution",
                    target: ["${execution.href}"]
                ]
            ]
        ])
        process = [ 'bash', '-c', "curl -v -k -X POST -H \"Content-Type: application/json\" -d '${json_payload}' ${configuration.webhook_url}" ].execute().text

        return true
    }
}
