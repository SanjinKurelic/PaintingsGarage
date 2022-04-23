# Usage:
# 1. Change every occurance of "localhost" in application.yaml with "host.docker.internal"
# 2. Run: ./mvnw clean package -DskipTests
# 3. Run: docker-compose up
# 4. Run: docker build -t paintings_garage .
# 5. - Fow Windows and macOS run: docker run -p 8080:8080 paintings_garage
#    - For Linux add extra flag: docker run -p 8080:8080 --add-host=host.docker.internal:host-gateway paintings_garage

FROM openjdk:17.0.1

VOLUME /tmp

COPY target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]