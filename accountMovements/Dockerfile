# Etapa de compilación
FROM gradle:jdk21-alpine AS build
WORKDIR /app
COPY build.gradle settings.gradle ./
COPY gradle/ gradle/
RUN gradle build --no-daemon || true
COPY src/ src/
RUN gradle build --no-daemon --warning-mode=all -x test

# Etapa de ejecución
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=build /app/build/libs/*0.0.1-SNAPSHOT.jar app-movements.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app-movements.jar"]
