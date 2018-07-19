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



'''
        git(url: 'https://github.com/JoeDaddyZZZ/FileProof.git', branch: 'master')
        sh '''ls -alrt
pwd
java -jar FileProof.jar'''
      }
    }
  }
}