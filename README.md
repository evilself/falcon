**FALCON.IO Technical Assessment**

_Getting Started_

1. GIT clone the project
2. Run **_./gradlew clean build_** within the installation folder
3. Run **_docker-compose up --build_**
4. Open a browser and navigate to **_localhost:8080_** to use the application
5. Enjoy!

_Code Setup_

There are 3 microservices - FE, REST and PERSISTER - all SpringBoot stateless applications.
Apache Kafka is used to distribute messages among the services, MongoDB is used to persist 
messages in a database. 

The FE microservice is the one that has the front-end HTML page running our application (along with 
WebSocket via Stomp and SockJs). You can update score information by submitting a WebSocket message or
a REST POST call.

_Technical Decisions and reasons behind them_

1. SpringBoot as services base - familiarity with it
2. MongoDB as persistence layer - didn't want to use db schemas/relational databases
3. Redis as another persistence layer with the idea of caching all messages PLUS integrating it with my infrastructure
3. Apache Kafka as messaging orchestration - haven't used Kafka in a professional environment before, so wanted to learn the basics
4. WebSocket via STOMP and SockJS - integration with Spring is easy
5. HTML/CSS/jQuery - didn't want to spend too much time on perfecting the FE, so using just the basics
6. Docker and docker-compose - ease of creating and starting multi-service application quickly