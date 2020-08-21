The Hello World Web Service Application running on Spring Boot
==============================================================


The hello world web application API that listens on port 8080 and greets a user with Hello! and exposes a health status endpoint.
The application utilizes Spring Boot as standalone HTTP Web Server running inside Docker container.

Git:
https://github.com/eugene-yakushev/helloworld-webapp-docker.git

1 To build the jar-file with Apache Maven
-------------------------------------
```
mvn clean install
```

2 To build Docker Image with tag "wsapp"
----------------------------------------
```
docker build --tag=wsapp .
```

3 To run the container with on host port 8080
---------------------------------------------
```
docker run -it -p 8080:8080 wsapp
```

4 The helper command-line to do all
-----------------------------------
```
mvn clean install && docker build --tag=wsapp . && docker run -it -p 8080:8080 wsapp
```

5 To test default context
-------------------------------------------------
```
curl -v http://localhost:8080
```
Output
```
$ curl http://localhost:8080
Hello!
```

6 To check health 
-------------------------------------------------
```
curl -v http://localhost:8080/healthz
```
Output
```
$ curl http://localhost:8080
Hello!
```
{
  "status": "OK",
  "version": "0.0.1",
  "uptime": "up since 2020-08-21 04:31:34"
  "cpu:utilization": "4.5337062E-4"
  "memory:utilization": "3676143616"
}

7 Questions and Answers for interview questions
---------------------------------------------
Q: What other information would you add to health endpoint json object ?
A: I have added 2 additional fields to "healthz" method, which I consider important: "cpu:utilization" and "memory:utilization"
Other attributes that can be added to the health check method are: "connections", "responseTime" 
and other system metrics and properties such as "OS", "memory:total", etc.

Q: How would you automate the build/test/deploy process for this application?
A: I would suggest to use Jenkins for CI/CD pipelines. To execute the pipeline I suggest to use a Jenkins file for each stage: build, deploy, and test.
The health status check can be utilized in Robot Framework. 

```
pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Building..'
            }
        }
        stage('Test') {
            steps {
                echo 'Testing..'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}
```

