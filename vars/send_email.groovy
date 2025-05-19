def call(String status) {
    emailext(
        from: env.MAIL_FROM,
        to: env.MAIL_TO,
        subject: "Build #${env.BUILD_NUMBER} ${status.toUpperCase()} – ${env.JOB_NAME}",
        body: """Build ${status}.
Job: ${env.JOB_NAME}
Build: #${env.BUILD_NUMBER}
${status == 'failure' ? "Check console: ${env.BUILD_URL}console" : "Link: ${env.BUILD_URL}"}
"""
    )
}
