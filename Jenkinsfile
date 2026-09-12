pipeline {
    agent any

    stages {
        stage('Obtener proyecto') {
            steps {
                checkout scm
            }
        }

        stage('Compilar, probar y medir cobertura') {
            steps {
                bat 'mvnw.cmd clean verify'
            }
        }
    }

    post {
        always {
            junit testResults: 'target/surefire-reports/*.xml', allowEmptyResults: false
            archiveArtifacts artifacts: 'target/site/jacoco/**', allowEmptyArchive: false
        }
    }
}