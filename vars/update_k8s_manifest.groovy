def call(Map config = [:]) {
    def imageTag = config.imageTag ?: error("Image Tag is required")
    def manifestsPath = config.manifestsPath ?: 'k8s'
    def gitCredentials = config.gitCredentials ?: 'github-credentials'
    def gitUserName = config.gitUserName ?: 'Jenkins CI'
    def gitUserEmail = config.gitUserEmail ?: 'jenkins@example.com'
    
    echo "Updating Kubernetes Manifest with Image Tag - ${imageTag}"
    
    withCredentials([usernamePassword(
        credentialsId: gitCredentials,
        usernameVariable: 'GIT_USERNAME',
        passwordVariable: 'GIT_PASSWORD'
    )]) {
        sh """
            git config user.name "${gitUserName}"
            git config user.email "${gitUserEmail}"
        """
        
        sh """
            sed -i "s|image: hrj06/ecommerce-app:.*|image: hrj06/ecommerce-app:${imageTag}|g" ${manifestsPath}/08-ecommerce-deploy.yml
            
            if [ -f "${manifestsPath}/10-migration-job.yml" ]; then
                sed -i "s|image: hrj06/ecommerce-migration:.*|image: hrj06/ecommerce-migration:${imageTag}|g" ${manifestsPath}/10-migration-job.yml
            fi
            
            if git diff --quiet; then
                echo "No Change"
            else
                git add ${manifestsPath}/*.yml
                git commit -m "ci: Update Image tag to ${imageTag} [ci skip]"
                
                git remote set-url origin https://\${GIT_USERNAME}:\${GIT_PASSWORD}@github.com/hRJ06/Ecommerce-Application.git
                git push origin HEAD:\${GIT_BRANCH}
            fi
        """
    }
}