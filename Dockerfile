
FROM openjdk:17
WORKDIR /
COPY . .
ADD build/libs/identify-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
EXPOSE 8080