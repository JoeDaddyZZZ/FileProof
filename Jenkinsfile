pipeline {
  agent {
    docker {
      image 'openjdk'
    }

  }
  stages {
    stage('Run') {
      steps {
        sh '''java -version
ls -alrt
pwd
cd FileProof
ls -alrt
java -jar FileProof.jar 

'''
        git(url: 'https://github.com/JoeDaddyZZZ/FileProof.git', branch: 'local')
        sh '''ls -alrt
pwd
java -jar FileProof.jar'''
      }
    }
  }
}