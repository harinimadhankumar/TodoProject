#!/bin/bash
set -e

echo "Building Spring Boot application..."
cd Backend/HelloWorld

# Clean and build
mvn clean package -DskipTests -q

echo "Build completed successfully!"
echo "JAR file: target/HelloWorld-0.0.1-SNAPSHOT.jar"
