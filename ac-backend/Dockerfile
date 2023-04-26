#### Stage 1: Build the application
FROM maven:3.8.4-openjdk-17 as builder

# Set the current working directory inside the image
WORKDIR /app

# Copy the project source
COPY mvnw .
COPY .mvn .mvn

# Copy the pom.xml file
COPY pom.xml .

# Copy the project source
COPY src src

# Package the application
ENV MAVEN_OPT="-Xmx1024m -XX:MaxPermSize=256m"
# RUN ./mvnw package -DskipTests
RUN mvn clean package -DskipTests=true
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

#### Stage 2: A minimal docker image with command to run the app
FROM openjdk:17

ARG DEPENDENCY=/app/target/dependency

# Copy project dependencies from the build stage
COPY --from=builder ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=builder ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=builder ${DEPENDENCY}/BOOT-INF/classes /app

ENTRYPOINT ["java","-Dspring.profiles.active=docker","-cp","app:app/lib/*","com.artconnect.backend.AcBackendApplication"]