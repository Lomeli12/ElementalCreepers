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
        ECVars.useStaticIds = config.getBoolean("useStaticIDs", false, "Set specific ids for the creepers. If false (which is recommended), the creepers will get a unique ID on mod loading.",
                ConfigEnum.GENERAL_CONFIG);

        ECVars.fireCreeperID = config.getInt("fireCreeperID", 121, "Creeper IDs - Can be between -127 to 127, check for http://minecraft.gamepedia.com/Data_values/Entity_IDs open IDs",
                ConfigEnum.GENERAL_CONFIG);
        ECVars.waterCreeperID = setGetInt("waterCreeperID", 122);
        ECVars.electricCreeperID = setGetInt("electricCreeperID", 123);
        ECVars.cookieCreeperID = setGetInt("cookieCreeperID", 124);
        ECVars.darkCreeperID = setGetInt("darkCreeperID", 125);
        ECVars.lightCreeperID = setGetInt("lightCreeperID", 126);
        ECVars.earthCreeperID = setGetInt("earthCreeperID", 127);
        ECVars.magmaCreeperID = setGetInt("magmaCreeperID", 101);
        ECVars.reverseCreeperID = setGetInt("reverseCreeperID", 102);
        ECVars.iceCreeperID = setGetInt("iceCreeperID", 103);
        ECVars.friendlyCreeperID = setGetInt("friendlyCreeperID", 104);
        ECVars.ghostCreeperID = setGetInt("ghostCreeperID", 105);
        ECVars.illusionCreeperID = setGetInt("illusionCreeperID", 107);
        ECVars.psychicCreeperID = setGetInt("psychicCreeperID", 108);
        ECVars.spiderCreeperID = setGetInt("spiderCreeperID", 106);

        ECVars.windCreeperID = setGetInt("windCreeperID", 114);
        ECVars.hydrogenCreeperID = setGetInt("hydrogenCreperID", 115);
        ECVars.enderCreeperID = setGetInt("enderCreeperID", 116);
        ECVars.stoneCreeperID = setGetInt("stoneCreeperID", 117);

        ECVars.euCreeperID = setGetInt("euCreeperID", 118);
        ECVars.rfCreeperID = setGetInt("rfCreeperID", 119);
    }

    private static void loadCreeperOptions() {
        ECVars.cookieCreeperAmount = config.getInt("cookiesDropped", 5, "The number of cookies a Cookie Creeper drops", ConfigEnum.OTHER);
    }

    private static void loadSpawnRates() {
        ECVars.fireCreeperSpawn = config.getInt("fireCreeperSpawn", 10, "Creeper Spawn Rates", ConfigEnum.GENERAL_CONFIG);
        ECVars.waterCreeperSpawn = setGetInt("waterCreeperSpawn", 10);
        ECVars.electricCreeperSpawn = setGetInt("electricCreeperSpawn", 10);
        ECVars.cookieCreeperSpawn = setGetInt("cookieCreeperSpawn", 10);
        ECVars.darkCreeperSpawn = setGetInt("darkCreeperSpawn", 2);
        ECVars.lightCreeperSpawn = setGetInt("lightCreeperSpawn", 4);
        ECVars.earthCreeperSpawn = setGetInt("earthCreeperSpawn", 10);
        ECVars.magmaCreeperSpawn = setGetInt("magmaCreeperSpawn", 6);
        ECVars.reverseCreeperSpawn = setGetInt("reverseCreeperSpawn", 6);
        ECVars.iceCreeperSpawn = setGetInt("iceCreeperSpawn", 10);
        ECVars.psychicCreeperSpawn = setGetInt("psychicCreeperSpawn", 10);
        ECVars.illusionCreeperSpawn = setGetInt("illusionCreeperSpawn", 6);
        ECVars.spiderCreeperSpawn = setGetInt("spiderCreeperSpawn", 8);
        ECVars.friendlyCreeperSpawn = setGetInt("friendlyCreeperSpawn", 1);

        ECVars.windCreeperSpawn = setGetInt("windCreeperSpawn", 10);
        ECVars.hydrogenCreeperSpawn = setGetInt("hydrogenCreeperSpawn", 1);
        ECVars.enderCreeperSpawn = setGetInt("enderCreeperSpawn", 4);
        ECVars.stoneCreeperSpawn = setGetInt("stoneCreeperSpawn", 10);

        ECVars.euCreeperSpawn = setGetInt("euCreeperSpawn", 10);
        ECVars.rfCreeperSpawn = setGetInt("rfCreeperSpawn", 10);
    }

    private static void loadBlastRadius() {
        ECVars.waterCreeperRadius = config.getInt("waterCreeperRadius", 4, "Creeper Explosion Radius", ConfigEnum.GENERAL_CONFIG);
        ECVars.fireCreeperRadius = setGetInt("fireCreeperRadius", 6);
        ECVars.iceCreeperRadius = setGetInt("iceCreeperRadius", 12);
        ECVars.electricCreeperRadius = setGetInt("electricCreeperRadius", 5);
        ECVars.earthCreeperRadius = setGetInt("earthCreeperRadius", 8);
        ECVars.psychicCreeperPower = setGetInt("psychicCreeperPower", 8);
        ECVars.psychicCreeperRadius = setGetInt("psychicCreeperRadius", 5);
        ECVars.magmaCreeperRadius = setGetInt("magmaCreeperRadius", 3);
        ECVars.ghostCreeperRadius = setGetInt("ghostCreeperRadius", 5);
        ECVars.ghostCreeperChance = setGetInt("ghostCreeperChance", 35);
        ECVars.lightCreeperRadius = setGetInt("lightCreeperRadius", 4);
        ECVars.darkCreeperRadius = setGetInt("darkCreeperRadius", 12);
        ECVars.reverseCreeperRadius = setGetInt("reverseCreeperRadius", 8);
        ECVars.spiderCreeperRadius = setGetInt("spiderCreeperRadius", 12);

        ECVars.windCreeperPower = setGetInt("windCreeperPower", 3);
        ECVars.windCreeperRadius = setGetInt("windCreeperRadius", 5);
        ECVars.hydrogenCreeperRadius = setGetInt("hydrogenCreeperRadius", 64);
        ECVars.stoneCreeperRadius = setGetInt("stoneCreeperRadius", 8);

        ECVars.euCreeperRadius = setGetInt("euCreeperRadius", 3);
        ECVars.rfCreeperRadius = setGetInt("rfCreeperRadius", 3);
    }

    private static int setGetInt(String tag, int id) {
        return config.getInt(tag, id, ConfigEnum.GENERAL_CONFIG);
    }
}
