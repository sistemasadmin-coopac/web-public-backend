FROM eclipse-temurin:21-jre-alpine

ENV TZ=America/Lima \
    JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -XX:InitialRAMPercentage=50.0 -XX:+TieredCompilation -XX:TieredStopAtLevel=1 -Djava.security.egd=file:/dev/./urandom -XX:+UnlockExperimentalVMOptions -XX:G1NewCollectionPercentThreshold=30 -XX:G1MaxNewGenPercent=30 -XX:-TieredCompilation"

WORKDIR /app

COPY build/libs/*.jar /app/app.jar

EXPOSE 8080

HEALTHCHECK --interval=10s --timeout=5s --start-period=30s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health/readiness || exit 1

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
