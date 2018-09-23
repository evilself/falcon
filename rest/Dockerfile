FROM gradle:4.2.1-jdk8-alpine
COPY build/libs/rest-0.0.1-SNAPSHOT.jar /app/rest-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java"]
CMD ["-jar", "/app/rest-0.0.1-SNAPSHOT.jar"]
EXPOSE 8081
