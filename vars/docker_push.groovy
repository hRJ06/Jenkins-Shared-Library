def call(String credentialId, String imageName) {
    withCredentials([usernamePassword(
        credentialsId: "${credentialId}",
        usernameVariable: 'DOCKERHUB_USER',
        passwordVariable: 'DOCKERHUB_PASSWORD'
    )]) {
        sh "docker login -u ${env.DOCKERHUB_USER} -p ${env.DOCKERHUB_PASSWORD}"
        sh "docker tag ${imageName}:latest ${env.DOCKERHUB_USER}/${imageName}:latest"
        sh "docker push ${env.DOCKERHUB_USER}/${imageName}:latest"
    }
}
