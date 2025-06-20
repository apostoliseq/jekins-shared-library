class DockerUtils {

    static String buildImageName(String appName, String tag) {
        return "myregistry/${appName}:${tag}"
    }

}