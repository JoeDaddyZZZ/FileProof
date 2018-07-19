pipeline {
  agent {
    docker {
      image 'openjdk'
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