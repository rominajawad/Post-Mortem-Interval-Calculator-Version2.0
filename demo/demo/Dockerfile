#first create a conatiner for the image we make to live in
#maven is used for building software while jdk is used for compiling and run so use both

#Stage 1: build the container you want your image to live in, it creates a single jar file
#to build the jar file, to build application itself
# Stage 1: Build the application using Java 25
# Stage 1: Build the application using Java 21/25 compatible tooling
# Stage 1: Build using a verified image that actually has JDK 25 and Maven
FROM azul/zulu-openjdk:25-latest AS build
WORKDIR /app

# Install Maven manually since it's a raw JDK image
RUN apt-get update && apt-get install -y maven

# Copy your untouched project files
COPY pom.xml .
COPY src ./src

# Compile the Java 25 code
RUN mvn clean package -DskipTests

# Stage 2: Run using Java 25 runtime
FROM azul/zulu-openjdk:25-jre-latest
WORKDIR /app

# This safely finds the main jar and renames it correctly to /app/app.jar
COPY --from=build /app/target/demo-*.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]

