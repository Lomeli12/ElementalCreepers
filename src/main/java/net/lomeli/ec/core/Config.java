package net.lomeli.ec.core;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

import net.lomeli.ec.lib.ECVars;

public class Config {
    public static Configuration config;

    public static void loadConfig(File configFile) {
        config = new Configuration(configFile);

        config.load();

        loadBasicOptions();
        loadID();
        loadSpawnRates();
        loadBlastRadius();

        config.save();
    }

    private static void loadID() {
        String cat = "Entity IDs";

        config.addCustomCategoryComment(cat, "Creeper IDs - Can be between -127 to 127, check for http://minecraft.gamepedia.com/Data_values/Entity_IDs open IDs");

        ECVars.fireCreeperID = config.get(cat, "fireCreeperID", 121).getInt(121);
        ECVars.waterCreeperID = setGetInt(cat, "waterCreeperID", 122);
        ECVars.electricCreeperID = setGetInt(cat, "electricCreeperID", 123);
        ECVars.cookieCreeperID = setGetInt(cat, "cookieCreeperID", 124);
        ECVars.darkCreeperID = setGetInt(cat, "darkCreeperID", 125);
        ECVars.lightCreeperID = setGetInt(cat, "lightCreeperID", 126);
        ECVars.earthCreeperID = setGetInt(cat, "earthCreeperID", 127);
        ECVars.magmaCreeperID = setGetInt(cat, "magmaCreeperID", 101);
        ECVars.reverseCreeperID = setGetInt(cat, "reverseCreeperID", 102);
        ECVars.iceCreeperID = setGetInt(cat, "iceCreeperID", 103);
        ECVars.friendlyCreeperID = setGetInt(cat, "friendlyCreeperID", 104);
        ECVars.ghostCreeperID = setGetInt(cat, "ghostCreeperID", 105);
        ECVars.illusionCreeperID = setGetInt(cat, "illusionCreeperID", 107);
        ECVars.psychicCreeperID = setGetInt(cat, "psychicCreeperID", 108);
        ECVars.spiderCreeperID = setGetInt(cat, "spiderCreeperID", 106);

        ECVars.windCreeperID = setGetInt(cat, "windCreeperID", 114);
        ECVars.hydrogenCreeperID = setGetInt(cat, "hydrogenCreperID", 115);
        ECVars.enderCreeperID = setGetInt(cat, "enderCreeperID", 116);
        ECVars.stoneCreeperID = setGetInt(cat, "stoneCreeperID", 117);

        ECVars.euCreeperID = setGetInt(cat, "euCreeperID", 118);
        ECVars.rfCreeperID = setGetInt(cat, "rfCreeperID", 119);
        ECVars.solarCreeperID = setGetInt(cat, "solarCreeperID", 120);
        ECVars.mjCreeperID = setGetInt(cat, "mjCreeperID", 121);
    }

    private static void loadBasicOptions() {
        ECVars.cookieCreeperAmount = config.get(Configuration.CATEGORY_GENERAL, "cookiesDropped", 5, "The number of cookies a Cookie Creeper drops").getInt(5);
        ECVars.useStaticIds = config.get(Configuration.CATEGORY_GENERAL, "useStaticIDs", false, "Set specific ids for the creepers. If false (which is recommended), the creepers will get a unique ID on mod loading.").getBoolean(false);
    }

    private static void loadSpawnRates() {
        String cat = "Spawn Rates";

        config.addCustomCategoryComment(cat, "Creeper Spawn Rates");

        ECVars.fireCreeperSpawn = config.get(cat, "fireCreeperSpawn", 10).getInt(10);
        ECVars.waterCreeperSpawn = setGetInt(cat, "waterCreeperSpawn", 10);
        ECVars.electricCreeperSpawn = setGetInt(cat, "electricCreeperSpawn", 10);
        ECVars.cookieCreeperSpawn = setGetInt(cat, "cookieCreeperSpawn", 10);
        ECVars.darkCreeperSpawn = setGetInt(cat, "darkCreeperSpawn", 2);
        ECVars.lightCreeperSpawn = setGetInt(cat, "lightCreeperSpawn", 4);
        ECVars.earthCreeperSpawn = setGetInt(cat, "earthCreeperSpawn", 10);
        ECVars.magmaCreeperSpawn = setGetInt(cat, "magmaCreeperSpawn", 6);
        ECVars.reverseCreeperSpawn = setGetInt(cat, "reverseCreeperSpawn", 6);
        ECVars.iceCreeperSpawn = setGetInt(cat, "iceCreeperSpawn", 10);
        ECVars.psychicCreeperSpawn = setGetInt(cat, "psychicCreeperSpawn", 10);
        ECVars.illusionCreeperSpawn = setGetInt(cat, "illusionCreeperSpawn", 6);
        ECVars.spiderCreeperSpawn = setGetInt(cat, "spiderCreeperSpawn", 8);
        ECVars.friendlyCreeperSpawn = setGetInt(cat, "friendlyCreeperSpawn", 1);

        ECVars.windCreeperSpawn = setGetInt(cat, "windCreeperSpawn", 10);
        ECVars.hydrogenCreeperSpawn = setGetInt(cat, "hydrogenCreeperSpawn", 1);
        ECVars.enderCreeperSpawn = setGetInt(cat, "enderCreeperSpawn", 4);
        ECVars.stoneCreeperSpawn = setGetInt(cat, "stoneCreeperSpawn", 10);
        ECVars.solarCreeperSpawn = setGetInt(cat, "solarCreeperSpawn", 4);

        ECVars.euCreeperSpawn = setGetInt(cat, "euCreeperSpawn", 10);
        ECVars.rfCreeperSpawn = setGetInt(cat, "rfCreeperSpawn", 10);
        ECVars.mjCreeperSpawn = setGetInt(cat, "mjCreeperSpawn", 10);
    }

    private static void loadBlastRadius() {
        String cat = "Explosion Configuration";

        config.addCustomCategoryComment(cat, "Creeper Explosion Radius");

        ECVars.waterCreeperRadius = config.get(cat, "waterCreeperRadius", 4).getInt(4);
        ECVars.fireCreeperRadius = setGetInt(cat, "fireCreeperRadius", 6);
        ECVars.iceCreeperRadius = setGetInt(cat, "iceCreeperRadius", 12);
        ECVars.electricCreeperRadius = setGetInt(cat, "electricCreeperRadius", 5);
        ECVars.earthCreeperRadius = setGetInt(cat, "earthCreeperRadius", 8);
        ECVars.psychicCreeperPower = setGetInt(cat, "psychicCreeperPower", 8);
        ECVars.psychicCreeperRadius = setGetInt(cat, "psychicCreeperRadius", 5);
        ECVars.magmaCreeperRadius = setGetInt(cat, "magmaCreeperRadius", 3);
        ECVars.ghostCreeperRadius = setGetInt(cat, "ghostCreeperRadius", 5);
        ECVars.ghostCreeperChance = setGetInt(cat, "ghostCreeperChance", 35);
        ECVars.lightCreeperRadius = setGetInt(cat, "lightCreeperRadius", 4);
        ECVars.darkCreeperRadius = setGetInt(cat, "darkCreeperRadius", 12);
        ECVars.reverseCreeperRadius = setGetInt(cat, "reverseCreeperRadius", 8);
        ECVars.spiderCreeperRadius = setGetInt(cat, "spiderCreeperRadius", 12);

        ECVars.windCreeperPower = setGetInt(cat, "windCreeperPower", 3);
        ECVars.windCreeperRadius = setGetInt(cat, "windCreeperRadius", 5);
        ECVars.hydrogenCreeperRadius = setGetInt(cat, "hydrogenCreeperRadius", 64);
        ECVars.stoneCreeperRadius = setGetInt(cat, "stoneCreeperRadius", 8);

        ECVars.euCreeperRadius = setGetInt(cat, "euCreeperRadius", 3);
        ECVars.rfCreeperRadius = setGetInt(cat, "rfCreeperRadius", 3);
        ECVars.mjCreeperRadius = setGetInt(cat, "mjCreeperRadius", 3);
    }

    private static int setGetInt(String cat, String tag, int id) {
        return config.get(cat, tag, id).getInt(id);
    }
}