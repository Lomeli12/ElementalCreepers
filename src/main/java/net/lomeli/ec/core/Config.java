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
        ECVars.fireCreeperID = config.getInt("fireCreeperID", 70, "Creeper IDs", ConfigEnum.GENERAL_CONFIG);
        ECVars.waterCreeperID = setGetInt("waterCreeperID", 71);
        ECVars.electricCreeperID = setGetInt("electricCreeperID", 72);
        ECVars.cookieCreeperID = setGetInt("cookieCreeperID", 73);
        ECVars.darkCreeperID = setGetInt("darkCreeperID", 74);
        ECVars.lightCreeperID = setGetInt("lightCreeperID", 75);
        ECVars.earthCreeperID = setGetInt("earthCreeperID", 76);
        ECVars.magmaCreeperID = setGetInt("magmaCreeperID", 77);
        ECVars.reverseCreeperID = setGetInt("reverseCreeperID", 78);
        ECVars.iceCreeperID = setGetInt("iceCreeperID", 79);
        ECVars.friendlyCreeperID = setGetInt("friendlyCreeperID", 40);
        ECVars.ghostCreeperID = setGetInt("ghostCreeperID", 41);
        ECVars.fakeIllusionCreeperID = setGetInt("fakeIllusionCreeperID", 42);
        ECVars.illusionCreeperID = setGetInt("illusionCreeperID", 43);
        
        ECVars.windCreeperID = setGetInt("windCreeperID", 47);
    }

    private static void loadCreeperOptions() {
        ECVars.cookieCreeperAmount = config.getInt("cookiesDropped", 4, "The number of cookies a Cookie Creeper drops", ConfigEnum.OTHER);
    }

    private static void loadSpawnRates() {
        ECVars.fireCreeperSpawn = config.getInt("fireCreeperSpawn", 50, "Creeper Spawn Rates", ConfigEnum.GENERAL_CONFIG);
        ECVars.waterCreeperSpawn = setGetInt("waterCreeperSpawn", 50);
        ECVars.electricCreeperSpawn = setGetInt("electricCreeperSpawn", 50);
        ECVars.cookieCreeperSpawn = setGetInt("cookieCreeperSpawn", 50);
        ECVars.darkCreeperSpawn = setGetInt("darkCreeperSpawn", 50);
        ECVars.lightCreeperSpawn = setGetInt("lightCreeperSpawn", 20);
        ECVars.earthCreeperSpawn = setGetInt("earthCreeperSpawn", 50);
        ECVars.magmaCreeperSpawn = setGetInt("magmaCreeperSpawn", 30);
        ECVars.reverseCreeperSpawn = setGetInt("reverseCreeperSpawn", 30);
        ECVars.iceCreeperSpawn = setGetInt("iceCreeperSpawn", 50);
        
        ECVars.windCreeperSpawn = setGetInt("windCreeperSpawn", 30);
    }

    private static void loadBlastRadius() {
        ECVars.waterCreeperRadius = config.getInt("waterCreeperRadius", 4, "Creeper Explosion Radius", ConfigEnum.GENERAL_CONFIG);
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
        
        ECVars.windCreeperRadius = setGetInt("windCreeperRadius", 5);
    }

    private static int setGetInt(String tag, int id) {
        return config.getInt(tag, id, ConfigEnum.GENERAL_CONFIG);
    }
}
