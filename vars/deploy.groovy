def call(String credentialId, String imageName, String containerName, String portMapping) {
    echo "Stage 4 – Deploy"
    
    sh "docker container rm -f ${containerName} || true"
    
    withCredentials([usernamePassword(
        credentialsId: "${credentialId}",
        usernameVariable: 'DOCKERHUB_USER',
        passwordVariable: 'DOCKERHUB_PASSWORD'
    )]) {
        sh "docker run -d --name ${containerName} -p ${portMapping} ${env.DOCKERHUB_USER}/${imageName}:latest"
    }
}
