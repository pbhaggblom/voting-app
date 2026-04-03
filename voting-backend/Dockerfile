FROM maven:3.9-eclipse-temurin-21 AS build
COPY . /usr/src/app
WORKDIR /usr/src/app
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /usr/src/app/target/quarkus-app/ /app/
CMD ["java", "-jar", "/app/quarkus-run.jar"]