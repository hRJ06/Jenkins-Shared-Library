def call(Map config = [:]) {
    def imageName = config.imageName ?: error("Image Name is required")
    def imageTag = config.imageTag ?: 'latest'
    def credentialId = config.credentialId ?: 'docker-hub-credentials'
    
    echo "Pushing Docker Image: ${imageName}:${imageTag}"

    withCredentials([usernamePassword(
        credentialsId: "${credentialId}",
        usernameVariable: 'DOCKERHUB_USER',
        passwordVariable: 'DOCKERHUB_PASSWORD'
    )]) {
        sh "docker login -u ${env.DOCKERHUB_USER} -p ${env.DOCKERHUB_PASSWORD}"
        sh "docker tag ${imageName}:latest ${env.DOCKERHUB_USER}/${imageName}:${imageTag}"
        sh "docker push ${env.DOCKERHUB_USER}/${imageName}:${imageTag}"
    }
}
