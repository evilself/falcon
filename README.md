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