FROM openjdk:8-jdk-alpine
COPY app/build/libs/app.jar demo.jar
CMD ["java", "-jar", "demo.jar"]