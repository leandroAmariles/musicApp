# Etapa de construcción: Usamos una imagen de Maven con JDK 17
FROM maven:3.8.4-openjdk-17 AS build

LABEL authors="Leandro"

# Directorio de trabajo en el contenedor
WORKDIR /app


COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim


COPY --from=build /app/target/*.jar /app/app.jar


EXPOSE 8080

CMD ["java", "-jar", "/app/app.jar"]
