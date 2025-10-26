FROM openjdk:21-jdk-slim-buster
WORKDIR /app
COPY target/userSpringApp-1.0-SNAPSHOT.jar ./userSpringApp.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "userSpringApp.jar", "--spring.kafka.bootstrap-servers=broker:9092", "--spring.kafka.producer.bootstrap-servers=broker:9092", "--spring.datasource.url=jdbc:postgresql://db:5432/postgres", "--spring.profiles.active=cloudConfig", "--eureka.client.service-url.defaultZone=http://eureka-server:8761/eureka"]