package adstestcases;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class Adsconfiguration {

    private Properties pro;
    private String url;
    private String username;
    private String password;

    public Adsconfiguration(String environment) {
        try {
            File src = new File("D:\\Selenium\\hybrid-framework\\configurations\\Ads.properties");

            if (!src.exists()) {
                throw new RuntimeException("Configuration file not found at: " + src.getAbsolutePath());
            }

            FileInputStream fis = new FileInputStream(src);
            pro = new Properties();
            pro.load(fis);

            switch (environment.toLowerCase()) {
                case "dev":
                    url = pro.getProperty("baseurldev");
                    username = pro.getProperty("usernamedev");
                    password = pro.getProperty("passworddev");
                    break;

                case "uat":
                    url = pro.getProperty("baseurluat");
                    username = pro.getProperty("usernameuat");
                    password = pro.getProperty("passworduat");
                    break;

                case "prod":
                    url = pro.getProperty("baseurlprod");
                    username = pro.getProperty("usernameprod");
                    password = pro.getProperty("passwordprod");
                    break;

                default:
                    throw new IllegalArgumentException("Invalid environment: " + environment);
            }

            if (url == null || username == null || password == null) {
                throw new RuntimeException("Missing configuration values for environment: " + environment);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error loading configuration: " + e.getMessage(), e);
        }
    }

    public String geturl() {
        return url;
    }

    public String getusername() {
        return username;
    }

    public String getpassword() {
        return password;
    }
}
