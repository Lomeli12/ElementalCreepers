package net.lomeli.ec.core;

import java.io.File;

import net.lomeli.ec.lib.ECVars;

import net.lomeli.lomlib.util.XMLConfiguration;
import net.lomeli.lomlib.util.XMLConfiguration.ConfigEnum;

public class Config {
    public static XMLConfiguration config;

    public static void loadConfig(File configFile) {
        config = new XMLConfiguration(configFile);

        config.loadXml();

        loadID();
        loadCreeperOptions();

        config.saveXML();
    }

    private static void loadID() {
        ECVars.fireCreeperID = setGetInt("fireCreeperID", 40);
        ECVars.waterCreeperID = setGetInt("waterCreeperID", 41);
        ECVars.electricCreeperID = setGetInt("electricCreeperID", 42);
        ECVars.cookieCreeperID = setGetInt("cookieCreeperID", 43);
        ECVars.darkCreeperID = setGetInt("darkCreeperID", 44);
        ECVars.lightCreeperID = setGetInt("lightCreeperID", 45);
        ECVars.earthCreeperID = setGetInt("earthCreeperID", 46);
        ECVars.magmaCreeperID = setGetInt("magmaCreeperID", 47);
        ECVars.reverseCreeperID = setGetInt("reverseCreeperID", 48);
        ECVars.iceCreeperID = setGetInt("iceCreeperID", 49);
    }

    private static void loadCreeperOptions() {
        ECVars.cookieCreeperAmount = config.getInt("cookiesDropped", 4, "The number of cookies a Cookie Creeper drops", ConfigEnum.OTHER);
    }

    private static int setGetInt(String tag, int id) {
        return config.getInt(tag, id, ConfigEnum.GENERAL_CONFIG);
    }
}
