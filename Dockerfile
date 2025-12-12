FROM eclipse-temurin:21-jre
WORKDIR /app
COPY build/libs/user-service-0.0.1-SNAPSHOT.jar app.jar
ENV SPRING_PROFILES_ACTIVE=docker
ENTRYPOINT ["java", "-jar", "/app/app.jar"]