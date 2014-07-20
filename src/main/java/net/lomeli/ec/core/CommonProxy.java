package net.lomeli.ec.core;

import net.minecraft.world.WorldServer;

import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
    public static FakePlayer ecPlayer;

    public void initMod(FMLPreInitializationEvent event) {
        Config.loadConfig(event.getSuggestedConfigurationFile());
    }

    public void registerRenders() {
    }

    public FakePlayer getEcPlayer(WorldServer world) {
        if (ecPlayer == null)
            ecPlayer = FakePlayerFactory.getMinecraft(world);
        return ecPlayer;
    }
}
