FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Crear usuario no-root para ejecutar la aplicación
RUN addgroup -S spring ; adduser -S spring -G spring

# Copiar el JAR construido por GitHub Actions
COPY build/libs/*.jar app.jar

# Crear directorio para uploads
RUN mkdir -p /app/uploads/financial-reports ; \
    chown -R spring:spring /app

# Cambiar al usuario no-root
USER spring:spring

# Exponer el puerto
EXPOSE 8080

# Variables de entorno por defecto (se pueden sobrescribir)
ENV SPRING_PROFILES_ACTIVE=prod \
    JAVA_OPTS="-Xmx512m -Xms256m -XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"

# Comando para ejecutar la aplicación con opciones de JVM optimizadas
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
