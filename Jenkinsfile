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
        script{
                def mvnHome = tool name: 'MAVEN_HOME', type: 'maven'
                sh "${mvnHome}/bin/mvn package"
        }
      }
    }
    stage('Building Docker Image') {
      steps{
        script {
          dockerImage = docker.build registry + ":$BUILD_NUMBER"
        }
      }
    }
    stage('Push Image To Docker Hub') {
      steps{
        script {
          /* Finally, we'll push the image with two tags:
                   * First, the incremental build number from Jenkins
                   * Second, the 'latest' tag.
                   * Pushing multiple tags is cheap, as all the layers are reused. */
          docker.withRegistry('https://registry.hub.docker.com', 'docker-hub') {
              dockerImage.push("${env.BUILD_NUMBER}")
              dockerImage.push("latest")
          }
        }
      }
    }
  node {
    stage('Apply Kubernetes files') {
    	withKubeCredentials(kubectlCredentials: [[caCertificate: '', clusterName: 'minikube', contextName: 'minikube', credentialsId: '0ca30707-3370-4ad4-b505-7eb59dff427f', namespace: 'myns', serverUrl: 'https://192.168.99.128:8443']]) {
      		sh 'kubectl apply -f my-kubernetes-directory'
      }
    }
    stage('Deploy to Kubernetes'){
        steps{
            sh 'kubectl create -f deployment.yml'
       }
    }
  }
}
