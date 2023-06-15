
FROM openjdk:17
LABEL maintainer="abhinav.seth"
COPY . .
ADD build/libs/identify-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]