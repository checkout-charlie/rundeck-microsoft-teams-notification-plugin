//import com.dtolabs.rundeck.plugins.notification.NotificationPlugin
import groovy.json.JsonOutput

def sendMessage(type, color, configuration, execution) {
  //with no args, there is a "configuration" and an "execution" variable in the context
  //sendMessage(type, color, configuration, execution)
  json_payload = JsonOutput.toJson( [
      title: "Rundeck Job Notification",
      summary: "Rundeck Job Notification",
      text: "${type} job: #${execution.id}: ${execution.project} ${execution.status} at ${execution.dateEnded}",
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

  return json_payload
}

configuration = [
  'webhook_url': '<URL HERE>'
]

execution = [
  'id': 'xyz',
  'project': 'projectx',
  'status': 'succes',
  'dateEnded': '2017-05-05'
]

type = "START"
color = "228B22"

json_payload = sendMessage(type, color, configuration, execution)
process = [ 'bash', '-c', "curl -v -k -X POST -H \"Content-Type: application/json\" -d '${json_payload}' ${configuration.webhook_url}" ].execute().text
print process
