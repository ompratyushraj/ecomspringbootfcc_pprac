The steps to clone, build, and run a Spring Boot project from the terminal using Maven and Java.

Commands to Run the Project:
bash

# 1. Clone the Git repository to your local machine
git clone https://github.com/ompratyushraj/ecomspringbootfcc_pprac/tree/main

# 2. Navigate into the project directory
cd ecomspringbootfcc_pprac

# 3. Clean and build the project using Maven
mvn clean install

# 4. Navigate to the target directory where the JAR file is located
cd target

# 5. Run the Spring Boot application using the generated JAR file
java -jar your-app-name.jar
What You Need:
Git: To clone the repository.
Maven: To build the project (mvn clean install).
Java: To run the JAR file (java -jar).
