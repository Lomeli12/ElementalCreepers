package net.lomeli.ec;

import net.minecraftforge.common.config.Configuration;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import net.lomeli.ec.core.CommonProxy;
import net.lomeli.ec.core.Config;
import net.lomeli.ec.core.EntityRegistering;
import net.lomeli.ec.core.VersionChecker;
import net.lomeli.ec.lib.Strings;

@Mod(modid = Strings.MOD_ID, name = Strings.MOD_NAME, version = Strings.VERSION, guiFactory = Strings.FACTORY)
public class ElementalCreepers {

    @SidedProxy(clientSide = Strings.CLIENT, serverSide = Strings.COMMON)
    public static CommonProxy proxy;

    public static Config config;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        config = new Config(new Configuration(event.getSuggestedConfigurationFile()));
        config.loadConfig();
        VersionChecker.checkForUpdates();
        FMLCommonHandler.instance().bus().register(new VersionChecker());
    }

    @Mod.EventHandler
    public void Init(FMLInitializationEvent event) {
        proxy.registerRenders();
        proxy.registerEvents();
        FMLCommonHandler.instance().bus().register(config);
        EntityRegistering.loadEntities();
    }

    @Mod.EventHandler
    public void postLoad(FMLPostInitializationEvent event) {
        if (Loader.isModLoaded("VersionChecker") && VersionChecker.needUpdate())
            VersionChecker.sendMessage();
    }
}
