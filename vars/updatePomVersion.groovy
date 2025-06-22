def call(String newVersion) {
    echo "Updating POM version to: ${newVersion}"
    
    sh """
        mvn versions:set -DnewVersion=${newVersion}-SNAPSHOT
        mvn versions:commit
    """
}