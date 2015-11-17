package net.lomeli.ec.lib;

public class ModLib {
    public static final int MAJOR = 5, MINOR = 0, REVISION = 0;
    public static final String VERSION = MAJOR + "." + MINOR + "." + REVISION;
    public static final String MOD_ID = "ElementalCreepers", MOD_NAME = "Elemental Creepers";
    public static final String CLIENT = "net.lomeli.ec.client.ClientProxy", COMMON = "net.lomeli.ec.core.CommonProxy";
    public static final String FACTORY = "net.lomeli.ec.client.gui.ECFactory";

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
    public static final String UPDATE_CHECK = CONFIG + "update";

    public static final String UPDATE_JSON = "https://raw.githubusercontent.com/Lomeli12/ElementalCreepers-4/master/update.json";
}
