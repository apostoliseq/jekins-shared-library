def call(String repoUrl, Map config = [:]) {
    def branch = config.branch ?: '*/main'
    def credentialsId = config.credentialsId ?: 'github'
    def includeChangelog = config.includeChangelog ?: != false
    def includePoll = config.includePoll ?: != false


    checkout changelog: includeChangelog,
            poll: includePoll,
            scm: scmGit(
                branches: [[name: branch]],
                extensions: [],
                userRemoteConfigs: [[
                        credentialsId: credentialsId,
                        url: repoUrl
                ]]
            )
}
    // * Pushes new Dockerfile to nexus

    // * kubectl deploys the new Dockerfile that's hosted on nexus
