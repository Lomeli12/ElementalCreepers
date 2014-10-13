package net.lomeli.ec.lib;

public class Strings {
    public static final int MAJOR = 4, MINOR = 2, REVISION = 3;
    public static final String VERSION = MAJOR + "." + MINOR + "." + REVISION;
    public static final String MOD_ID = "ElementalCreepers", MOD_NAME = "Elemental Creepers";
    public static final String CORE = "net.lomeli.ec.core", CLIENT = CORE + ".ClientProxy", COMMON = CORE + ".CommonProxy";
    public static final String FACTORY = "net.lomeli.ec.core.ECFactory";

    public static final String UPDATE = "update.elementalcreepers.";
    public static final String UPDATE_MESSAGE = UPDATE + "message";
    public static final String OLD_VERSION = UPDATE + "old";
    public static final String NEW_VERSION = UPDATE + "new";

    public static final String CONFIG = "config.elementalcreepers.";
    public static final String SPAWN = CONFIG + "spawnRates";
    public static final String EXPLOSION = CONFIG + "explosionConfig";
    public static final String COOKIE = CONFIG + "cookies";
    public static final String DOME = CONFIG + "domeEx";
    public static final String SPECIAL = CONFIG + "specialEvents";
}
