package com.test.student.config;

public class ConfigEnv {
    private static String SELENIUM_HUB_URL = "SELENIUM_HUB_URL";
    private static String SELENIUM_APP_URL = "SELENIUM_APP_URL";

    public static String getSeleniumHubUrl(String localUrl) {
        return getUrl(localUrl, SELENIUM_HUB_URL);
    }

    public static String getSeleniumAppUrl(String localUrl) {
        return getUrl(localUrl, SELENIUM_APP_URL);
    }

    private static String getUrl(String localUrl, String env) {
        String connectUrl = localUrl;
        String url = System.getenv(env);

        if (url != null && !url.isEmpty() && !url.isBlank()) {
            connectUrl = url;
        }
        System.out.println("**** connectUrl : " + connectUrl);
        return connectUrl;
    }
}
