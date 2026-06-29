FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests -B



FROM gcr.io/distroless/java21-debian12:debug
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8989

ENV JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=70.0 \
    -XX:+UseZGC -XX:+ZGenerational \
    -XX:ActiveProcessorCount=2 \
    -Xss256k \
    -XX:+ExitOnOutOfMemoryError"

ENTRYPOINT ["java", "-jar", "/app/app.jar"]