/* 
    V1
    def call(String imageName) {
        echo "Stage 2 – Build"
        sh "docker build -t ${imageName}:latest ."
    }

*/

def call(Map config = [:]) {
    def imageName = config.imageName ?: error("Image Name is required")
    def imageTag = config.imageTag ?: 'latest'
    def dockerfile = config.dockerfile ?: 'Dockerfile'
    def context = config.context ?: '.'
}

echo "Building Docker Image: ${imageName}:${imageTag} using ${dockerfile}"

sh """
    docker build -t ${imageName}:${imageTag} -t ${imageName}:latest -f ${dockerfile} ${context}
"""