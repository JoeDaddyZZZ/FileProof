pipeline {
  agent {
    docker {
      image 'maven:3.3.3'
    }

  }
  stages {
    stage('error') {
      steps {
        sh 'sh \'java --version\''
      }
    }
  }
}