== Local Deployment and Test

. In terminal 1; start-up zookeeper, kafka and postgresql
+
-----
podman-compose -f etc/docker-compose.yaml up
-----

-----
curl -v -H "Content-Type: application/json" \
        -X POST  user1-evacuee-dialogue:8080/sms/createIncident/ \
        -d '{"lat":"30.12345","lon":"-70.98765","numberOfPeople":"2","medicalNeeded":false,"victimName":"John Doe","victimPhoneNumber":"111-222-333","timestamp":"1595352054","sentimentData":"HELP"}'
-----
