FROM maven:3.6.3-jdk-11-slim AS build
WORKDIR /home/app
COPY . /home/app
RUN mvn -f /home/app/pom.xml clean package -DskipTests
 
FROM  openjdk:11
VOLUME /tmp
EXPOSE 80
COPY --from=build /home/app/target/*.jar app.jar
#ENV SPRING_PROFILES_ACTIVE=docker
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]