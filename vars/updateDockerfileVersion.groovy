def call(String newVersion, String dockerfilePath = 'Dockerfile') {

    echo "Updating Dockerfile version to: ${newVersion}"
    
    def dockerfileContent = readFile file: dockerfilePath
    
    def versionRegex = /(<version>)\d+\.\d+\.\d+(-.*?</version>)/

    def updatedDockerfileContent = dockerfileContent.replaceFirst(versionRegex, "\$1${newVersion}\$2")

    writeFile file: dockerfilePath, text: updatedDockerfileContent
    
    echo "Updated version in ${dockerfilePath} to ${newVersion}"
}