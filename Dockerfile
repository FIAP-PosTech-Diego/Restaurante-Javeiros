FROM openjdk:17-jdk

COPY target/javeiros-0.0.1-SNAPSHOT.jar /app/app.jar

CMD ["java", "-jar", "app/app.jar"]