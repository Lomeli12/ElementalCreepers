package net.lomeli.ec;

import net.lomeli.lomlib.util.ModLoaded;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

import net.lomeli.ec.core.CommonProxy;
import net.lomeli.ec.core.Config;
import net.lomeli.ec.core.EntityRegistering;
import net.lomeli.ec.core.MorphAddon;
import net.lomeli.ec.lib.Strings;

@Mod(modid = Strings.MOD_ID, name = Strings.MOD_NAME, version = Strings.VERSION, dependencies = "required-after:LomLib@[1.0.9,)")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class ElementalCreepers {

    @SidedProxy(clientSide = Strings.CLIENT, serverSide = Strings.COMMON)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Config.loadConfig(event.getSuggestedConfigurationFile());
    }

    @Mod.EventHandler
    public void Init(FMLInitializationEvent event) {
        proxy.registerRenders();

        EntityRegistering.loadEntities();
    }

    @Mod.EventHandler
    public void postLoad(FMLPostInitializationEvent event) {
        if (ModLoaded.isModInstalled("Morph"))
            MorphAddon.loadAddon();
    }
}
