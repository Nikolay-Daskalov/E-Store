FROM openjdk:11-jre

COPY build/libs/E-Store.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]

#docker build -t nikolaydaskalov/E-Store:v1 -f deployment/Dockerfile .