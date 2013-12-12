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
        loadSpawnRates();
        loadBlastRadius();

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

    private static void loadSpawnRates() {
        ECVars.fireCreeperSpawn = setGetInt("fireCreeperSpawn", 5);
        ECVars.waterCreeperSpawn = setGetInt("waterCreeperSpawn", 5);
        ECVars.electricCreeperSpawn = setGetInt("electricCreeperSpawn", 5);
        ECVars.cookieCreeperSpawn = setGetInt("cookieCreeperSpawn", 5);
        ECVars.darkCreeperSpawn = setGetInt("darkCreeperSpawn", 5);
        ECVars.lightCreeperSpawn = setGetInt("lightCreeperSpawn", 2);
        ECVars.earthCreeperSpawn = setGetInt("earthCreeperSpawn", 5);
        ECVars.magmaCreeperSpawn = setGetInt("magmaCreeperSpawn", 3);
        ECVars.reverseCreeperSpawn = setGetInt("reverseCreeperSpawn", 3);
        ECVars.iceCreeperSpawn = setGetInt("iceCreeperSpawn", 5);
    }

    private static void loadBlastRadius() {
        ECVars.waterCreeperRadius = setGetInt("waterCreeperRadius", 4);
        ECVars.fireCreeperRadius = setGetInt("fireCreeperRadius", 6);
        ECVars.iceCreeperRadius = setGetInt("iceCreeperRadius", 12);
        ECVars.electricCreeperRadius = setGetInt("electricCreeperRadius", 5);
        ECVars.earthCreeperRadius = setGetInt("earthCreeperRadius", 8);
        ECVars.psychicCreeperRadius = setGetInt("psychicCreeperRadius", 5);
        ECVars.psychicCreeperPower = setGetInt("psychicCreeperPower", 8);
        ECVars.cookieCreeperAmount = setGetInt("cookieCreeperAmount", 5);
        ECVars.magmaCreeperRadius = setGetInt("magmaCreeperRadius", 3);
        ECVars.ghostCreeperRadius = setGetInt("ghostCreeperRadius", 5);
        ECVars.ghostCreeperChance = setGetInt("ghostCreeperChance", 35);
        ECVars.lightCreeperRadius = setGetInt("lightCreeperRadius", 4);
        ECVars.darkCreeperRadius = setGetInt("darkCreeperRadius", 12);
        ECVars.reverseCreeperRadius = setGetInt("reverseCreeperRadius", 8);
    }

    private static int setGetInt(String tag, int id) {
        return config.getInt(tag, id, ConfigEnum.GENERAL_CONFIG);
    }
}
