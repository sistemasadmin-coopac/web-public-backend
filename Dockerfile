FROM gcr.io/distroless/java21-debian12

ENV TZ=America/Lima

WORKDIR /app
# Copia el jar final (si generas varios, asegúrate que el boot-jar sea el que se copia)
COPY build/libs/*.jar /app/app.jar

# Asegúrate en application.yml: server.port=${PORT:8080}
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/app.jar"]