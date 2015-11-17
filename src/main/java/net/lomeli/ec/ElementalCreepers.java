package net.lomeli.ec;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

import net.lomeli.lomlib.core.config.ModConfig;
import net.lomeli.lomlib.core.version.VersionChecker;
import net.lomeli.lomlib.util.LogHelper;

import net.lomeli.ec.client.handler.ECGuiHandler;
import net.lomeli.ec.core.CommonProxy;
import net.lomeli.ec.core.EntityRegistering;
import net.lomeli.ec.core.addon.AddonBase;
import net.lomeli.ec.core.block.BlockSilverCreeper;
import net.lomeli.ec.core.item.ItemCreepapedia;
import net.lomeli.ec.core.item.ItemSilverBlock;
import net.lomeli.ec.lib.ModVars;
import net.lomeli.ec.lib.ModLib;

@Mod(modid = ModLib.MOD_ID, name = ModLib.MOD_NAME, version = ModLib.VERSION, guiFactory = ModLib.FACTORY, dependencies = "required-after:LomLib")
public class ElementalCreepers {
    @Mod.Instance
    public static ElementalCreepers instance;

    @SidedProxy(clientSide = ModLib.CLIENT, serverSide = ModLib.COMMON)
    public static CommonProxy proxy;

    public static ModConfig config;
    public static VersionChecker updater;
    public static LogHelper logger;

    public static Block silverCreepBlock;
    public static Item creepapedia;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = LogHelper.createLogger(ModLib.MOD_NAME);
        config = new ModConfig(ModLib.MOD_ID, event.getSuggestedConfigurationFile(), ModVars.class);
        config.loadConfig();
        updater = new VersionChecker(ModLib.UPDATE_JSON, ModLib.MOD_ID, ModLib.MOD_NAME, ModLib.MAJOR, ModLib.MINOR, ModLib.REVISION);
        if (ModVars.checkForUpdates)
            new Thread(updater).start();
        AddonBase.registerAddons();
        silverCreepBlock = new BlockSilverCreeper();
        creepapedia = new ItemCreepapedia();

        GameRegistry.registerBlock(silverCreepBlock, ItemSilverBlock.class, "silverCreepBlock");
        GameRegistry.registerItem(creepapedia, "creepapedia");
    }

    @Mod.EventHandler
    public void Init(FMLInitializationEvent event) {
        proxy.registerRenders();
        proxy.registerEvents();
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new ECGuiHandler());
        EntityRegistering.loadEntities();
    }

    @Mod.EventHandler
    public void postLoad(FMLPostInitializationEvent event) {
        AddonBase.activateAddons();
    }
}
