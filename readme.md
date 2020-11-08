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
- order service is available on port 8090

#### This service (tag v1.0) can send e-mails

- curl -X POST http://localhost:8090/mails?mailAddress=recipien.mail@gmail.com&orderDetails=apple=2,orange=3&totalCost=$1.1 -H 'USER_ID: John Doe'
- Unit tests has added


