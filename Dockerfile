FROM eclipse-temurin:21-jre-alpine

ENV TZ=America/Lima \
    JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -XX:InitialRAMPercentage=50.0 -XX:+TieredCompilation -XX:TieredStopAtLevel=2 -XX:+FastTier4Compilation -XX:CICompilerCount=2 -Djava.security.egd=file:/dev/./urandom -XX:+UnlockExperimentalVMOptions -XX:+UseZGC"

WORKDIR /app

COPY build/libs/*.jar /app/app.jar

EXPOSE 8080

# Health check para Cloud Run
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
