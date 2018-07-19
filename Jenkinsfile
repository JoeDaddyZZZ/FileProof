pipeline {
  agent {
    docker {
      image 'openjdk'
    }

  }
  stages {
    stage('error') {
      steps {
        sh '''java -version
ls -alrt
pwd
cd FileProof
ls -alrt
java -jar FileProof.jar 

'''
      }
    }
  }
}