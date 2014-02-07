package net.lomeli.ec.entity.addon;

import java.awt.Color;

import net.lomeli.ec.core.EntityRegistering;
import net.lomeli.ec.lib.ECVars;

import net.lomeli.lomlib.util.ModLoaded;

public class AddonEntities {
    public static void registerEntities() {
        if (ModLoaded.isModInstalled("IC2")) {
            EntityRegistering.registerEntity(EntityEUCreeper.class, EntityRegistering.getCreeperName("EUCreeper"), ECVars.euCreeperID, EntityRegistering.creeperEggGreen,
                    new Color(77, 77, 77).getRGB());
        }
        
        if (ModLoaded.isModInstalled("ThermalExpansion")) {
            EntityRegistering.registerEntity(EntityRFCreeper.class, EntityRegistering.getCreeperName("RFCreeper"), ECVars.rfCreeperID, EntityRegistering.creeperEggGreen,
                    new Color(205, 15, 15).getRGB());
        }
    }

    public static void loadSpawns() {

    }
}
