package net.lomeli.ec;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import net.lomeli.lomlib.core.version.VersionChecker;

import net.lomeli.ec.core.CommonProxy;
import net.lomeli.ec.core.Config;
import net.lomeli.ec.core.EntityRegistering;
import net.lomeli.ec.core.addon.AddonBase;
import net.lomeli.ec.lib.ECVars;
import net.lomeli.ec.lib.Strings;

@Mod(modid = Strings.MOD_ID, name = Strings.MOD_NAME, version = Strings.VERSION, guiFactory = Strings.FACTORY, dependencies = "required-after:LomLib")
public class ElementalCreepers {
    @Mod.Instance
    public static ElementalCreepers instance;

    @SidedProxy(clientSide = Strings.CLIENT, serverSide = Strings.COMMON)
    public static CommonProxy proxy;

    public static Config config;
    public static VersionChecker checker;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        config = new Config(new Configuration(event.getSuggestedConfigurationFile()));
        config.loadConfig();
        checker = new VersionChecker(Strings.UPDATE_JSON, Strings.MOD_ID, Strings.MOD_NAME, ECVars.MAJOR, ECVars.MINOR, ECVars.REVISION);
        checker.checkForUpdates();
        AddonBase.registerAddons();
    }

    @Mod.EventHandler
    public void Init(FMLInitializationEvent event) {
        proxy.registerRenders();
        proxy.registerEvents();
        EntityRegistering.loadEntities();
    }

    @Mod.EventHandler
    public void postLoad(FMLPostInitializationEvent event) {
        AddonBase.activateAddons();
    }
}
