# Use an OpenJDK image with sbt pre-installed
FROM hseeberger/scala-sbt:11.0.16_8u345-b01_1.7.2_2.13.10

# Set the working directory inside the container
WORKDIR /app

# Copy the project files into the container
COPY . .

# Install project dependencies
RUN sbt update

# Default command to run your tests
#CMD ["sbt", "-Denvironment=githubActions", "-Dheadless=true", "test"]