def call(String appName, String version, Map config = [:]) {
    def nexusRegistry = config.nexusRegistry ?: '192.168.138.154:5000/repository/docker-hosted'
    def dockerfilePath = config.dockerfilePath ?: 'Dockerfile'
    def credentialsId = config.credentialsId ?: 'nexus-jenkins'
    
    def imageName = "${nexusRegistry}/${appName}"
    def imageTag = "${imageName}:${version}"
        
    stage('Build Docker Image') {
        sh """
            echo "Building docker image: ${imageTag}"
            docker build -f ${dockerfilePath} -t ${imageTag} .
            docker tag ${imageTag}
        """
    }
    
    stage('Login to Nexus') {
        withCredentials([usernamePassword(
            credentialsId: credentialsId,
            usernameVariable: 'NEXUS_USER',
            passwordVariable: 'NEXUS_PASS'
        )]) {
            sh """
                echo "Logging into Nexus Docker registry: ${nexusRegistry}"
                echo ${NEXUS_PASS} | docker login ${nexusRegistry} -u ${NEXUS_USER} --password-stdin
            """
        }
    }
    
    stage('Push to Nexus') {
        sh """
            echo "Pushing ${imageTag}..."
            docker push ${imageTag}
        """
    }
    
    stage('Logout from Nexus') {
        echo "Logging out from Nexus..."
        sh "docker logout ${nexusRegistry}"
    }
    
    stage('Cleanup') {
        sh """
            echo "Cleaning up local Docker images"
            docker rmi ${imageTag} || true
        """
    }
}