package config;

public class ConfigEnv {
    private static String API_URL_CUCUMBER = "API_URL_CUCUMBER";
    public static String getAPIUrl(String localUrl) {
        String connectUrl = localUrl;
        String url = System.getenv(API_URL_CUCUMBER);

        if (url != null && !url.isEmpty() && !url.isBlank()) {
            connectUrl = url;
        }
        System.out.println("**** connectUrl : " + connectUrl);
        return connectUrl;
    }
}
