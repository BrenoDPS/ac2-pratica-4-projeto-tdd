pipeline {
    agent any
    
    tools {
        jdk 'JDK-21'
        maven 'Maven-3.9'
    }
    
    environment {
        DOCKER_IMAGE = 'tdd-projeto'
        DOCKER_TAG = "${BUILD_NUMBER}"
        JACOCO_THRESHOLD = '99'
    }
    
    stages {
        stage('Checkout') {
            steps {
                echo '=== Clonando repositorio ==='
                checkout scm
            }
        }
        
        stage('Clean') {
            steps {
                echo '=== Limpando projeto ==='
                bat 'mvn clean'
            }
        }
        
        stage('Build') {
            steps {
                echo '=== Compilando projeto ==='
                bat 'mvn compile'
            }
        }
        
        stage('Pipeline-test-dev') {
            steps {
                echo '=== Executando testes unitarios e de integracao ==='
                bat 'mvn test'
            }
            post {
                always {
                    junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml'
                }
            }
        }
        
        stage('Analise PMD') {
            steps {
                echo '=== Executando analise PMD ==='
                bat 'mvn pmd:pmd pmd:cpd'
            }
            post {
                always {
                    recordIssues enabledForFailure: true, 
                                 tool: pmdParser(pattern: '**/target/pmd.xml')
                }
            }
        }
        
        stage('Cobertura JaCoCo') {
            steps {
                echo '=== Gerando relatorio JaCoCo ==='
                bat 'mvn jacoco:report'
            }
            post {
                always {
                    jacoco execPattern: '**/target/jacoco.exec',
                           classPattern: '**/target/classes',
                           sourcePattern: '**/src/main/java',
                           exclusionPattern: '**/test/**'
                }
            }
        }
        
        stage('Quality Gate 99%') {
            steps {
                script {
                    echo '=== Verificando Quality Gate de 99% ==='
                    try {
                        bat 'mvn verify'
                        env.QUALITY_GATE_PASSED = 'true'
                        echo 'SUCESSO: Quality Gate passou - Cobertura >= 99%'
                    } catch (Exception e) {
                        env.QUALITY_GATE_PASSED = 'false'
                        echo 'AVISO: Quality Gate nao passou - Cobertura < 99%'
                        echo 'Continuando pipeline para gerar relatorios...'
                    }
                }
            }
        }
        
        stage('Image Docker') {
            when {
                expression { env.QUALITY_GATE_PASSED == 'true' }
            }
            steps {
                script {
                    echo '=== Construindo imagem Docker ==='
                    bat """
                        docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} .
                        docker tag ${DOCKER_IMAGE}:${DOCKER_TAG} ${DOCKER_IMAGE}:latest
                    """
                }
            }
        }
        
        stage('Package') {
            when {
                expression { env.QUALITY_GATE_PASSED == 'true' }
            }
            steps {
                echo '=== Gerando JAR da aplicacao ==='
                bat 'mvn package -DskipTests'
            }
            post {
                success {
                    archiveArtifacts artifacts: '**/target/*.jar', 
                                     fingerprint: true,
                                     allowEmptyArchive: true
                }
            }
        }
    }
    
    post {
        always {
            echo '=== Pipeline finalizado ==='
            cleanWs()
        }
        success {
            echo 'Pipeline executado com SUCESSO'
        }
        failure {
            echo 'Pipeline FALHOU'
        }
    }
}