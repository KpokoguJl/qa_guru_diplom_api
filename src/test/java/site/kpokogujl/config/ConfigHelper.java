package site.kpokogujl.config;

import org.aeonbits.owner.ConfigFactory;

public class ConfigHelper {

    public static String getBaseURL() {
        return getConfig().getBaseUrl();
    }

   private static TestsConfig getConfig() {
        return ConfigFactory.newInstance().create(TestsConfig.class);
    }
}
