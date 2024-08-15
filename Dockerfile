# Use the OpenJDK image as the base
FROM openjdk:11-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Install sbt
RUN apt-get update && apt-get install -y curl gnupg2 && \
    echo "deb https://repo.scala-sbt.org/scalasbt/debian all main" | tee /etc/apt/sources.list.d/sbt.list && \
    curl -sL "https://keyserver.ubuntu.com/pks/lookup?op=get&search=0x99E82A75642AC823" | apt-key add && \
    apt-get update && apt-get install -y sbt && \
    apt-get clean

# Copy the project files into the container
COPY . .

# Install project dependencies
RUN sbt update

# Default command to run your tests
#CMD ["sbt", "-Denvironment=githubActions", "-Dheadless=true", "test"]
