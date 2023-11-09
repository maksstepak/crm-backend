FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml /app/pom.xml
COPY src /app/src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17
COPY --from=build /app/target/crm-0.0.1-SNAPSHOT.jar /app.jar
CMD ["java", "-Dspring.profiles.active=prod", "-jar", "/app.jar"]