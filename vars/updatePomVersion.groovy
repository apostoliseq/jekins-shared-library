def call(String newVersion, String pomPath = 'pom.xml') {

    echo "Updating POM version to: ${newVersion}"
    
    def pomContent = readFile file: pomPath
    
    def versionRegex = /(target\/java-maven-app-)\d+\.\d+\.\d+(.*?\.jar")/

    def updatedPomContent = pomContent.replaceFirst(versionRegex, "\$1${newVersion}\$2")

    writeFile file: pomPath, text: updatedPomContent
    
    echo "Updated version in ${pomPath} to ${newVersion}"
}