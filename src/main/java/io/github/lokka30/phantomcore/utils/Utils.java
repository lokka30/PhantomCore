package io.github.lokka30.phantomcore.utils;

public class Utils {

    public Utils() {
    }

    public String getRecommendedServerVersion() {
        //If changing this, consider 'api-version' too.
        return "1.15";
    }

    public int getLatestSettingsVersion() {
        return 3;
    }

    public int getLatestMessagesVersion() {
        return 5;
    }

    public int getLatestDataVersion() {
        return 1;
    }

    public double round(final double value) {
        return Double.parseDouble(String.format("%.2f", value));
    }


}
