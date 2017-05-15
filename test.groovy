webhook_url = "<WEBHOOK_URL>"
process = [ 'bash', '-c', "curl -v -k -X POST -H \"Content-Type: application/json\" -d '{\"text\": \"Test for Simple Notification from Rundeck\"}' ${webhook_url}" ].execute().text
print process
