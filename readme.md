# Mail Sender Service
### How to build
- git clone git@github.com:evgPhoenix/mailService.git
- mkdir to service directory
- change your ```spring.mail.username=
                 spring.mail.password=```
                 in ```application.properties``` file
- the settings are for gmail.com smtp mail, but you can change it
- ./gradlew build
- java jar [jarName] (get jar from build directory)
- mail sender service will be available on port 8090

#### This service (tag v1.0) can send e-mails

- curl -X POST http://localhost:8090/mails?mailAddress=recipien.mail@gmail.com&orderDetails=apple=2,orange=3&totalCost=$1.1 -H 'USER_ID: John Doe'
- Unit tests has added

#### Version 2.0 (tag v2.0) can listen for message broker to get info for sending e-mails

- I guess that somebody has started message broker before the order service had started
- So mail-sender will listen to the same topic as the order service 


