pipeline {
  environment {
    registry = "master6789/demo1"
    registryCredential = 'docker-hub'
    dockerImage = ''
  }
  agent any
  stages {
    stage('Compile') {
      steps {
        git 'https://github.com/master6789/javaWelcomePipe.git'
        script {
                def mvnHome = tool name: 'MAVEN_HOME', type: 'maven'
                sh "${mvnHome}/bin/mvn package"
        }
      }
    }
    stage('Building Docker Image') {
      steps {
        script {
          dockerImage = docker.build registry + ":$BUILD_NUMBER"
        }
      }
    }
    stage('Push Image To Docker Hub') {
      steps {
        script {
          /* Finally, we'll push the image with two tags:
                   * First, the incremental build number from Jenkins
                   * Second, the 'latest' tag.
                   * Pushing multiple tags is cheap, as all the layers are reused. */
              docker.withRegistry('https://registry.hub.docker.com','docker-hub') {
                dockerImage.push("${env.BUILD_NUMBER}")
                dockerImage.push("latest")
          }
        }
      }
    }
    stage('Deploying to minikube') {
      steps {
        script {
           withKubeCredentials(kubectlCredentials: [[caCertificate: '', clusterName: 'minikube', contextName: 'minikube', credentialsId: '44b7a3f7-8964-45c0-85f5-b439d6075c20', namespace: 'myns', serverUrl: 'https://192.168.99.117:8443']]) {
                sh 'kubectl apply -f deployment.yml'
            }
        }
      }
    }
  }
}
