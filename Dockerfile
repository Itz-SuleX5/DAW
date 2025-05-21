# Usamos una única imagen que incluya Maven y JDK
FROM maven:3.9.6-eclipse-temurin-17

WORKDIR /app

# Copiar todo el proyecto
COPY . .

# Crear directorios necesarios para Vaadin con permisos correctos
RUN mkdir -p /app/frontend && \
    mkdir -p /app/node_modules && \
    mkdir -p /app/generated/jar-resources && \
    chmod -R 777 /app/frontend /app/node_modules /app/generated

# Exponer el puerto
EXPOSE 8080

# Variable de entorno para Vaadin
ENV VAADIN_FRONTEND_FOLDER=/app/frontend
ENV VAADIN_PREPAREJS=true
ENV VAADIN_FOLDER=/app

# Evitar que Vaadin intente limpiar node_modules (causa problemas de permisos)
ENV VAADIN_SKIP_NODE_MODULES_CLEANING=true

# El comando de inicio prepara frontend y luego ejecuta la aplicación
CMD ["sh", "-c", "mvn vaadin:prepare-frontend -Dvaadin.skip.node.modules.cleaning=true && mvn spring-boot:run -Dvaadin.skip.node.modules.cleaning=true"]