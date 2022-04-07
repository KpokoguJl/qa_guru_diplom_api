package site.kpokogujl.config;

import org.aeonbits.owner.ConfigFactory;

public class ConfigHelper {

    public static String getBaseURL() {
        return getConfig().getBaseUrl();
    }

    public static String apiLogin() {
        return getConfig().apiLogin();
    }

    public static String apiPassword() {
        return getConfig().apiPassword();
    }

    public static String apiUserName() {
        return getConfig().apiUserName();
    }

    private static TestsConfig getConfig() {
        return ConfigFactory.newInstance().create(TestsConfig.class);
    }
}
