package io.github.lokka30.phantomcore.utils;

import io.github.lokka30.phantomcore.PhantomCore;

public class Utils {

    private PhantomCore instance;

    public Utils(PhantomCore instance) {
        this.instance = instance;
    }

    public String getRecommendedServerVersion() {
        //If changing this, consider 'api-version' too.
        return "1.15";
    }

    public int getLatestSettingsVersion() {
        return 1;
    }

    public int getLatestMessagesVersion() {
        return 2;
    }

    public int getLatestDataVersion() {
        return 1;
    }
}
