# Usar una imagen base con Maven y OpenJDK 17
FROM maven:3.8.6-openjdk-17-slim AS builder

# Copiar todo el código fuente de la aplicación al contenedor
COPY . /app

# Establecer el directorio de trabajo
WORKDIR /app

# Ejecutar 'mvn clean' para limpiar el proyecto y luego 'mvn package' para compilar el proyecto
RUN mvn clean package -DskipTests

# Usar una imagen base para ejecutar la aplicación (con solo JRE)
FROM eclipse-temurin:17-jre

# Copiar el JAR generado desde la etapa de construcción anterior
COPY --from=builder /app/target/*.jar /app.jar

# Exponer el puerto 8080
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app.jar"]