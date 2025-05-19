def call(String imageName) {
    echo "Stage 2 – Build"
    sh "docker build -t ${imageName}:latest ."
}
