FROM openjdk:11
COPY app/build/libs/app.jar demo.jar
CMD ["java", "-jar", "demo.jar"]