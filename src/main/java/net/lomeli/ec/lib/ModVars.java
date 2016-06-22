package net.lomeli.ec.lib;

import net.lomeli.lomlib.core.config.annotations.ConfigBoolean;
import net.lomeli.lomlib.core.config.annotations.ConfigInt;

public class ModVars {
    @ConfigInt(defaultValue = 10, minValue = 0, category = "spawn-rates", categoryComment = ModLib.SPAWN)
    public static int waterCreeperSpawn;
    @ConfigInt(defaultValue = 10, minValue = 0, category = "spawn-rates")
    public static int fireCreeperSpawn;
    @ConfigInt(defaultValue = 10, minValue = 0, category = "spawn-rates")
    public static int iceCreeperSpawn;
    @ConfigInt(defaultValue = 10, minValue = 0, category = "spawn-rates")
    public static int electricCreeperSpawn;
    @ConfigInt(defaultValue = 10, minValue = 0, category = "spawn-rates")
    public static int earthCreeperSpawn;
    @ConfigInt(defaultValue = 10, minValue = 0, category = "spawn-rates")
    public static int psychicCreeperSpawn;
    @ConfigInt(defaultValue = 10, minValue = 0, category = "spawn-rates")
    public static int cookieCreeperSpawn;
    @ConfigInt(defaultValue = 6, minValue = 0, category = "spawn-rates")
    public static int magmaCreeperSpawn;
    @ConfigInt(defaultValue = 1, minValue = 0, category = "spawn-rates")
    public static int friendlyCreeperSpawn;
    @ConfigInt(defaultValue = 6, minValue = 0, category = "spawn-rates")
    public static int illusionCreeperSpawn;
    @ConfigInt(defaultValue = 4, minValue = 0, category = "spawn-rates")
    public static int lightCreeperSpawn;
    @ConfigInt(defaultValue = 2, minValue = 0, category = "spawn-rates")
    public static int darkCreeperSpawn;
    @ConfigInt(defaultValue = 6, minValue = 0, category = "spawn-rates")
    public static int reverseCreeperSpawn;
    @ConfigInt(defaultValue = 8, minValue = 0, category = "spawn-rates")
    public static int spiderCreeperSpawn;

    @ConfigInt(defaultValue = 10, minValue = 0, category = "spawn-rates")
    public static int windCreeperSpawn;
    @ConfigInt(defaultValue = 10, minValue = 0, category = "spawn-rates")
    public static int stoneCreeperSpawn;
    @ConfigInt(defaultValue = 10, minValue = 0, category = "spawn-rates")
    public static int enderCreeperSpawn;
    @ConfigInt(defaultValue = 10, minValue = 0, category = "spawn-rates")
    public static int hydrogenCreeperSpawn;
    @ConfigInt(defaultValue = 10, minValue = 0, category = "spawn-rates")
    public static int solarCreeperSpawn;
    @ConfigInt(defaultValue = 10, minValue = 0, category = "spawn-rates")
    public static int cakeCreeperSpawn;
    @ConfigInt(defaultValue = 10, minValue = 0, category = "spawn-rates")
    public static int fireworkCreeperSpawn;
    @ConfigInt(defaultValue = 10, minValue = 0, category = "spawn-rates")
    public static int bigBadSpawn;
    @ConfigInt(defaultValue = 10, minValue = 0, category = "spawn-rates")
    public static int springCreeperSpawn;
    @ConfigInt(defaultValue = 10, minValue = 0, category = "spawn-rates")
    public static int silverCreeperSpawn;
    @ConfigInt(defaultValue = 10, minValue = 0, category = "spawn-rates")
    public static int furnaceCreeperSpawn;
    @ConfigInt(defaultValue = 1, minValue = 0, category = "spawn-rates")
    public static int warpCreeperSpawn;

    @ConfigInt(defaultValue = 4, minValue = 0, category = "explosion-configuration", categoryComment = ModLib.EXPLOSION)
    public static int waterCreeperRadius;
    @ConfigInt(defaultValue = 6, minValue = 0, category = "explosion-configuration")
    public static int fireCreeperRadius;
    @ConfigInt(defaultValue = 8, minValue = 0, category = "explosion-configuration")
    public static int iceCreeperRadius;
    @ConfigInt(defaultValue = 5, minValue = 0, category = "explosion-configuration")
    public static int electricCreeperRadius;
    @ConfigInt(defaultValue = 8, minValue = 0, category = "explosion-configuration")
    public static int earthCreeperRadius;
    @ConfigInt(defaultValue = 8, minValue = 0, category = "explosion-configuration")
    public static int psychicCreeperPower;
    @ConfigInt(defaultValue = 5, minValue = 0, category = "explosion-configuration")
    public static int psychicCreeperRadius;
    @ConfigInt(defaultValue = 5, minValue = 1, maxValue = 64, category = "explosion-configuration", nameOverride = "cookiesDropped", comment = ModLib.COOKIE)
    public static int cookieCreeperAmount;
    @ConfigInt(defaultValue = 3, minValue = 0, category = "explosion-configuration")
    public static int magmaCreeperRadius;
    @ConfigInt(defaultValue = 5, minValue = 0, category = "explosion-configuration")
    public static int ghostCreeperRadius;
    @ConfigInt(defaultValue = 35, minValue = 0, category = "explosion-configuration")
    public static int ghostCreeperChance;
    @ConfigInt(defaultValue = 4, minValue = 0, category = "explosion-configuration")
    public static int lightCreeperRadius;
    @ConfigInt(defaultValue = 12, minValue = 0, category = "explosion-configuration")
    public static int darkCreeperRadius;
    @ConfigInt(defaultValue = 8, minValue = 0, category = "explosion-configuration")
    public static int reverseCreeperRadius;
    @ConfigInt(defaultValue = 12, minValue = 0, category = "explosion-configuration")
    public static int spiderCreeperRadius;

    @ConfigInt(defaultValue = 3, minValue = 0, category = "explosion-configuration")
    public static int windCreeperPower;
    @ConfigInt(defaultValue = 5, minValue = 0, category = "explosion-configuration")
    public static int windCreeperRadius;
    @ConfigInt(defaultValue = 8, minValue = 0, category = "explosion-configuration")
    public static int stoneCreeperRadius;
    @ConfigInt(defaultValue = 64, minValue = 0, category = "explosion-configuration")
    public static int hydrogenCreeperRadius;
    @ConfigInt(defaultValue = 5, minValue = 0, category = "explosion-configuration")
    public static int fireworkCreeperRadius;
    @ConfigInt(defaultValue = 7, minValue = 0, category = "explosion-configuration")
    public static int bigBadAmount;
    @ConfigInt(defaultValue = 2, minValue = 0, category = "explosion-configuration")
    public static int springCreeperPower;
    @ConfigInt(defaultValue = 5, minValue = 0, category = "explosion-configuration")
    public static int silverCreeperRadius;
    @ConfigInt(defaultValue = 3, minValue = 0, category = "explosion-configuration")
    public static int furnaceCreeperRadius;
    @ConfigInt(defaultValue = 7, minValue = 0, category = "explosion-configuration")
    public static int warpCreeperRadius;

    @ConfigBoolean(defaultValue = false, comment = ModLib.DOME)
    public static boolean domeExplosion;
    @ConfigBoolean(defaultValue = true, comment = ModLib.SPECIAL)
    public static boolean special;
    @ConfigBoolean(defaultValue = true, comment = ModLib.UPDATE_CHECK)
    public static boolean checkForUpdates;
}
