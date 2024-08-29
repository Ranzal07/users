FROM maven:3.9.8-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY . .
RUN mvn clean install -Dmaven.test.skip=true


FROM eclipse-temurin:21-alpine
COPY --from=build /app/target/users-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "/app.jar" ]