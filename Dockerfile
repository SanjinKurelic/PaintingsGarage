# Usage:
# 1. Run: ./mvnw clean package -DskipTests
# 2. Run: docker-compose up
# 3. Run: docker build -t paintings_garage .
# 4. - Fow Windows and macOS run: docker run -p 8080:8080 paintings_garage
#    - For Linux add extra flag: docker run -p 8080:8080 --add-host=host.docker.internal:host-gateway paintings_garage

FROM openjdk:17.0.1

VOLUME /tmp

COPY target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=docker","/app.jar"]