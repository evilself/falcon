FROM gradle:4.2.1-jdk8-alpine
COPY build/libs/persister-0.0.1-SNAPSHOT.jar /app/persister-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java"]
CMD ["-jar", "/app/persister-0.0.1-SNAPSHOT.jar"]
EXPOSE 8082
