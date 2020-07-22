== To-do

. Create a chat dialogue
+
-----
How many people are with you that need assistance ?
Please elaborate on any emergency services you may need.
-----

. Define a new rule that increases priority when sentimentData includes word: "pregnant"

== Local Deployment and Test

. In terminal 1; start-up zookeeper, kafka and postgresql
+
-----
podman-compose -f etc/docker-compose.yaml up
-----

. Test creation of a new incident from evacuee-service
+
-----
curl -v -H "Content-Type: application/json" \
        -X POST  user1-evacuee-dialogue:8080/sms/createIncident/ \
        -d '{"lat":"30.12345","lon":"-70.98765","numberOfPeople":"2","medicalNeeded":false,"victimName":"John Doe","victimPhoneNumber":"111-222-333","timestamp":"1595352054","sentimentData":"HELP"}'
-----

. Simulate a text message being sent to evacuee-service
+
-----
curl -v \
        -X POST \
        -H "Accept: application/xml" \
        -H "Content-Type: application/x-www-form-urlencoded" \
        "http://localhost:8080/sms" \
        -d "ToCountry=US&ToState=MA&SmsMessageSid=SM40e358b8f07953be4010c1a831cec06b&NumMedia=0&ToCity=NORTH+FALMOUTH&FromZip=80207&SmsSid=SM40e358b8f07953be4010c1a831cec06b&FromState=CO&SmsStatus=received&FromCity=DENVER&Body=hello+from+alex&FromCountry=US&To=%2B17742527773&ToZip=02559&NumSegments=1&MessageSid=SM40e358b8f07953be4010c1a831cec06b&AccountSid=AC83ae9d8f6e41edbd45faaa388dfd0295&From=%2B13035237885&ApiVersion=2010-04-01"
-----

. Receive location from what's app via twilio:  https://www.twilio.com/blog/location-aware-whatsapp-bot-ruby-sinatra-twilio
