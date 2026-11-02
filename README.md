# PROGRAM 5: Spring Boot Testing with Docker

## Aim

To develop automated test cases for a Spring Boot REST API using Spring Boot Testing and execute the application inside a Docker container.

## Software Requirements

* Java JDK 17
* Spring Boot
* Maven
* Visual Studio Code
* Docker Desktop
* Windows PowerShell

## Project Structure

```text
demo/
│
├── pom.xml
├── mvnw
├── mvnw.cmd
├── Dockerfile
├── README.md
│
└── src/
    ├── main/
    │   └── java/com/example/demo/
    │       ├── DemoApplication.java
    │       └── HelloController.java
    │
    └── test/
        └── java/com/example/demo/
            ├── DemoApplicationTests.java
            └── HelloControllerTest.java
```

## Step 1: Open the Project

1. Extract the downloaded Spring Boot project.
2. Open VS Code.
3. Select File → Open Folder.
4. Open the project folder containing `pom.xml`.
5. Open Terminal → New Terminal.

IMPORTANT: Execute all commands from the project root folder where `pom.xml` and `mvnw.cmd` are present.

## Step 2: Verify Java and Maven

Check Java:

```powershell
java -version
```

Check Maven Wrapper:

```powershell
.\mvnw.cmd -version
```

## Step 3: Create REST API

File: `HelloController.java`

```java
package com.example.demo;

import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello from Docker!";
    }
}
```

The REST API returns the message:

Hello from Docker!

## Step 4: Create Automated Test Cases

File: `HelloControllerTest.java`

```java
package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HelloControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testHelloAPI() {

        String result = restTemplate.getForObject(
            "http://localhost:" + port + "/hello",
            String.class
        );

        assertEquals("Hello from Docker!", result);
    }

    @Test
    void testStatus() {

        var response = restTemplate.getForEntity(
            "http://localhost:" + port + "/hello",
            String.class
        );

        assertEquals(200, response.getStatusCode().value());
    }
}
```

### Test Cases

1. testHelloAPI() – Checks the API response.
2. testStatus() – Checks whether the HTTP status code is 200.

## Step 5: Clean the Project

Execute:

```powershell
.\mvnw.cmd clean
```

This removes previously generated build files.

## Step 6: Execute Test Cases

Execute:

```powershell
.\mvnw.cmd test
```

Expected output:

```text
Tests run: 2
Failures: 0
Errors: 0

BUILD SUCCESS
```

The project may report additional tests if the default test class is present.

## Step 7: Build the Application

Execute:

```powershell
.\mvnw.cmd package
```

This compiles the application, runs tests and generates the JAR file.

The JAR file will be created inside:

```text
target/demo-0.0.1-SNAPSHOT.jar
```

## Step 8: Create Dockerfile

Create a file named `Dockerfile` in the project root directory.

Add:

```dockerfile
FROM eclipse-temurin:17-jre

COPY target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]
```

## Step 9: Build Docker Image

Make sure Docker Desktop is running.

Execute:

```powershell
docker build -t springtest .
```

Expected output:

```text
Successfully tagged springtest:latest
```

Verify the image:

```powershell
docker images
```

## Step 10: Run Docker Container

Execute:

```powershell
docker run -d -p 8080:8080 --name testapp springtest
```

Check the running container:

```powershell
docker ps
```

## Step 11: Test the Containerized Application

Execute:

```powershell
curl.exe http://localhost:8080/hello
```

Expected output:

```text
Hello from Docker!
```

Alternatively, open the browser and enter:

http://localhost:8080/hello

## Step 12: Stop and Remove Container

Execute:

```powershell
docker rm -f testapp
```

This stops and removes the Docker container.

## Complete Execution Commands

```powershell
java -version

.\mvnw.cmd -version

.\mvnw.cmd clean

.\mvnw.cmd test

.\mvnw.cmd package

docker build -t springtest .

docker run -d -p 8080:8080 --name testapp springtest

docker ps

curl.exe http://localhost:8080/hello

docker rm -f testapp
```

## Expected Results

| Operation          | Expected Output    |
| ------------------ | ------------------ |
| Maven Testing      | BUILD SUCCESS      |
| API Response Test  | Hello from Docker! |
| HTTP Status Test   | 200 OK             |
| Docker Image       | springtest:latest  |
| Docker Container   | Running            |
| Container API Test | Hello from Docker! |

## Result

Successfully developed two automated test cases using Spring Boot Testing, verified the REST API response and HTTP status code, built a Docker image and executed the application inside a Docker container.

















Step 3: Open the VS Code terminal

Click:

Terminal → New Terminal

Run these commands one by one:

java -version
.\mvnw.cmd -version
Step 4: Execute the test cases

Run:

.\mvnw.cmd clean test

Wait until Maven finishes.

Expected output:

Tests run: 3, Failures: 0, Errors: 0, Skipped: 0

BUILD SUCCESS

You may see two or three tests depending on whether the default Spring Boot test is also present.

Step 5: Build the application
.\mvnw.cmd package

Expected:

BUILD SUCCESS
Step 6: Build the Docker image

Make sure Docker Desktop is running.

docker build -t springtest .
Step 7: Run the Docker container
docker run -d -p 8080:8080 --name testapp springtest

Check:

docker ps
Step 8: Show the output

Run:

curl.exe http://localhost:8080/hello

Expected output:

Hello from Docker!

🎉 Your experiment is successfully executed!\
