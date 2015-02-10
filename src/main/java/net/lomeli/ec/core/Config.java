package net.lomeli.ec.core;

import net.minecraft.util.StatCollector;

import net.minecraftforge.common.config.Configuration;

import net.lomeli.lomlib.util.SimpleConfig;

import net.lomeli.ec.lib.ECVars;
import net.lomeli.ec.lib.Strings;

public class Config extends SimpleConfig {

    public Config(Configuration config) {
        super(Strings.MOD_ID, config);
    }

    @Override
    public void loadConfig() {
        ECVars.cookieCreeperAmount = config.getInt("cookiesDropped", Configuration.CATEGORY_GENERAL, 5, 1, 64, StatCollector.translateToLocal(Strings.COOKIE));
        ECVars.domeExplosion = config.getBoolean("domeExplosion", Configuration.CATEGORY_GENERAL, false, StatCollector.translateToLocal(Strings.DOME));
        ECVars.special = config.getBoolean("special", Configuration.CATEGORY_GENERAL, true, StatCollector.translateToLocal(Strings.SPECIAL));

        String cat = "spawn-rates";

        config.addCustomCategoryComment(cat, StatCollector.translateToLocal(Strings.SPAWN));

        ECVars.fireCreeperSpawn = setGetInt(cat, "fireCreeperSpawn", 10);
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
        ECVars.cakeCreeperSpawn = setGetInt(cat, "cakeCreeperSpawn", 2);
        ECVars.fireworkCreeperSpawn = setGetInt(cat, "fireworkCreeperSpawn", 8);
        ECVars.bigBadSpawn = setGetInt(cat, "bigBadSpawn", 1);
        ECVars.springCreeperSpawn = setGetInt(cat, "springCreeperSpawn", 7);

        ECVars.euCreeperSpawn = setGetInt(cat, "euCreeperSpawn", 10);
        ECVars.rfCreeperSpawn = setGetInt(cat, "rfCreeperSpawn", 10);
        ECVars.mjCreeperSpawn = setGetInt(cat, "mjCreeperSpawn", 10);

        cat = "explosion-configuration";

        config.addCustomCategoryComment(cat, StatCollector.translateToLocal(Strings.EXPLOSION));

        ECVars.waterCreeperRadius = setGetInt(cat, "waterCreeperRadius", 4);
        ECVars.fireCreeperRadius = setGetInt(cat, "fireCreeperRadius", 6);
        ECVars.iceCreeperRadius = setGetInt(cat, "iceCreeperRadius", 8);
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
        ECVars.fireworkCreeperRadius = setGetInt(cat, "fireworkCreeperRadius", 5);
        ECVars.bigBadAmount = setGetInt(cat, "bigBadAmount", 7);
        ECVars.springCreeperPower = setGetInt(cat, "springCreeperPower", 2);

        ECVars.euCreeperRadius = setGetInt(cat, "euCreeperRadius", 3);
        ECVars.rfCreeperRadius = setGetInt(cat, "rfCreeperRadius", 3);
        ECVars.mjCreeperRadius = setGetInt(cat, "mjCreeperRadius", 3);

        if (config.hasChanged())
            config.save();
    }

    private int setGetInt(String cat, String tag, int id) {
        return config.getInt(tag, cat, id, 0, 100, "");
    }
}