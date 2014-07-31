package net.lomeli.ec.entity.addon;

import java.awt.Color;

import cpw.mods.fml.common.Loader;

import net.lomeli.ec.core.EntityRegistering;
import net.lomeli.ec.lib.ECVars;

public class AddonEntities {
    public static void registerEntities() {
        if (Loader.isModLoaded("IC2")) {
            EntityRegistering.registerEntity(EntityEUCreeper.class, EntityRegistering.getCreeperName("EUCreeper"), EntityRegistering.creeperEggGreen, new Color(77, 77, 77).getRGB());
            EntityRegistering.addOverWorldSpawn(EntityEUCreeper.class, ECVars.euCreeperSpawn, 1, 4);
        }

        if (doesRFExist()) {
            EntityRegistering.registerEntity(EntityRFCreeper.class, EntityRegistering.getCreeperName("RFCreeper"), EntityRegistering.creeperEggGreen, new Color(205, 15, 15).getRGB());
            EntityRegistering.addOverWorldSpawn(EntityRFCreeper.class, ECVars.rfCreeperSpawn, 1, 4);
        }

        //if (Loader.isModLoaded("BuildCraft|Core")) {
        //    EntityRegistering.registerEntity(EntityMJCreeper.class, EntityRegistering.getCreeperName("MJCreeper"), EntityRegistering.creeperEggGreen, new Color(237, 90, 0).getRGB());
        //    EntityRegistering.addOverWorldSpawn(EntityMJCreeper.class, ECVars.mjCreeperSpawn, 1, 4);
        //}
    }

    public static boolean doesRFExist() {
        try {
            return Class.forName("cofh.api.energy.IEnergyHandler") != null;
        } catch (Exception e) {
        }
        return false;
    }

}
