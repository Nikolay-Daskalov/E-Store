FROM openjdk:11-jre

#Remove dots if does not work
COPY ./build/libs/E-Store.jar app.jar
ENTRYPOINT ["java", "-jar", "./app.jar"]