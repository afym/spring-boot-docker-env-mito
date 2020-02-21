backendVersion = '0.0.0'
frontendVersion = '0.0.0'

pipeline {

    agent any

    environment {
        // sonar parameters
        SONAR_URL = "${params.SONAR_URL}"
        SONAR_PROJECT = "${params.SONAR_PROJECT}"
        SONAR_LOGIN = "${params.SONAR_LOGIN}"
        SONAR_ORGANIZATION = "${params.SONAR_ORGANIZATION}"
        // selenium parameters
        SELENIUM_HOST = "${params.SELENIUM_HOST}"
        // internal parameters (docker)
        EPHEMERAL_HOST = "${params.EPHEMERAL_HOST}"
        CONTAINER_BACKEND_PATH = "${params.CONTAINER_BACKEND_PATH}"
        CONTAINER_FRONTEND_PATH = "${params.CONTAINER_FRONTEND_PATH}"
        API_EPHEMERAL_URL = "http://${EPHEMERAL_HOST}:9998"
        // external test parameters
        API_URL_CUCUMBER = "${API_EPHEMERAL_URL}"
        SELENIUM_HUB_URL = "http://${EPHEMERAL_HOST}:4444/wd/hub"
        SELENIUM_APP_URL = "http://${EPHEMERAL_HOST}:9999"
    }

    stages {
        stage('Internal testing') {
            agent {
                docker { image 'maven:3.6.3-jdk-11-slim' }
            }
            steps {
                sh 'mvn --version'
                sh 'echo "Iniciando pruebas"'
                sh 'cd /tmp && ls'
                sh 'echo "Obteniendo dependencias"'
                sh 'mvn -B -e -C -T 1C org.apache.maven.plugins:maven-dependency-plugin:3.0.2:go-offline'
                sh 'echo "Ejecutar pruebas unitarias"'
                sh 'mvn test'
                sh 'echo "Ejecutando jacoco report"'
                sh 'mvn jacoco:report'
                sh 'echo "Ejecutando testing calidad sonarqube"'
                sh 'mvn sonar:sonar -Dsonar.projectKey=${SONAR_PROJECT} -Dsonar.host.url=${SONAR_URL} -Dsonar.login=${SONAR_LOGIN} -Dsonar.organization=${SONAR_ORGANIZATION}'

            }
        }

        stage('Prepare backend version') {
            agent {
                docker { image 'maven:3.6.3-jdk-11-slim' }
            }
            steps {
                echo "Getting backend version with maven"
                echo "Before ${backendVersion}"
                sh 'mvn help:evaluate -Dexpression=project.version -q -DforceStdout > backend.txt'
                script {
                    backendVersion = "${CONTAINER_BACKEND_PATH}:" + readFile('backend.txt').trim() + "-" + env.BUILD_NUMBER
                }
                echo "After ${backendVersion}"
            }
        }

        stage('Prepare frontend version') {
            agent {
                docker { image 'node:12.2.0-alpine' }
            }
            steps {
                echo "Getting frontend version with npm"
                echo "Before ${frontendVersion}"
                sh 'ls -la'                
                sh 'node -p "require(\'./frontend/package.json\').version" > frontend.txt'
                script {
                    frontendVersion = "${CONTAINER_FRONTEND_PATH}:" + readFile('frontend.txt').trim() + "-" + env.BUILD_NUMBER
                }
                echo "After ${frontendVersion}"
            }
        }

        stage('Setup compose environmet') {
            steps {
                echo "Building backend image ${backendVersion}"
                sh "docker build -t ${backendVersion} ."
                echo "Building frontend image ${frontendVersion}"
                sh 'cp -rf ./frontend/src/serviceConnection.dist ./frontend/src/serviceConnection.js'
                sh "sed -i 's@{{API_STUDENT_URL}}@${API_EPHEMERAL_URL}@g' ./frontend/src/serviceConnection.js"
                sh "cat ./frontend/src/serviceConnection.js"
                sh "docker build -t ${frontendVersion} ./frontend/"
                echo "Generate docker-compose file"
                sh "sed -i 's@{{BACKEND_DOCKER_IMAGE}}@${backendVersion}@g' docker-compose.dist"
                sh "sed -i 's@{{FRONTEND_DOCKER_IMAGE}}@${frontendVersion}@g' docker-compose.dist"
                sh 'cat docker-compose.dist'
                sh "docker-compose -f docker-compose.dist up -d"
                sh "sleep 5"
                sh "docker-compose -f docker-compose.dist ps"
            }
        }

        stage("External testing BDD") {
            agent {
               docker { image 'maven:3.6.3-jdk-11-slim' }
            }
            steps {
                echo "Testing cucumber BDD"
                sh 'echo $API_URL_CUCUMBER'
                dir("${env.WORKSPACE}/e2e/cucumber-student") {
                    sh 'mvn test'
                    sh 'mvn exec:java -Dexec.classpathScope=test -Dexec.mainClass=io.cucumber.core.cli.Main -Dexec.args="src/test/resources --glue features"'
                }
            }
        }

        stage('Restart compose environmet') {
            steps {
                sh "docker-compose -f docker-compose.dist down"
                sh "docker-compose -f docker-compose.dist up -d"
                sh "sleep 10"
            }
        }

        stage("External testing Selenium") {
            agent {
               docker { image 'maven:3.6.3-jdk-11-slim' }
            }
            steps {
                echo "Testing Selenium"
                sh 'echo $SELENIUM_HUB_URL'
                sh 'echo $SELENIUM_APP_URL'
                dir("${env.WORKSPACE}/e2e/selenium-student") {
                    sh 'mvn test'
                }
            }
        }
    }

    post {
        always {
            echo "Down ephemeral environment...."
            sh "docker-compose -f docker-compose.dist down"
        }

        success {
            echo "success"
        }

        unstable {
            echo "unstable"
        }

        failure {
            echo "failure"
        }

        changed {
            echo "changed"
        }
    }
}