package net.lomeli.ec.core;

import java.awt.Color;

import net.lomeli.ec.entity.EntityCookieCreeper;
import net.lomeli.ec.entity.EntityDarkCreeper;
import net.lomeli.ec.entity.EntityEarthCreeper;
import net.lomeli.ec.entity.EntityElectricCreeper;
import net.lomeli.ec.entity.EntityFakeIllusionCreeper;
import net.lomeli.ec.entity.EntityFireCreeper;
import net.lomeli.ec.entity.EntityFriendlyCreeper;
import net.lomeli.ec.entity.EntityGhostCreeper;
import net.lomeli.ec.entity.EntityIceCreeper;
import net.lomeli.ec.entity.EntityIllusionCreeper;
import net.lomeli.ec.entity.EntityLightCreeper;
import net.lomeli.ec.entity.EntityMagmaCreeper;
import net.lomeli.ec.entity.EntityPsyhicCreeper;
import net.lomeli.ec.entity.EntityReverseCreeper;
import net.lomeli.ec.entity.EntityWaterCreeper;
import net.lomeli.ec.entity.EntityWindCreeper;
import net.lomeli.ec.lib.ECVars;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityEggInfo;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;

import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

import cpw.mods.fml.common.registry.EntityRegistry;

public class EntityRegistering {

    private static Type[] typeList = { Type.FOREST, Type.HILLS, Type.SWAMP, Type.JUNGLE, Type.WASTELAND, Type.MAGICAL, Type.BEACH, Type.DESERT, Type.FROZEN, Type.MOUNTAIN };
    private static int creeperEggGreen = new Color(894731).getRGB();
    
    public static void loadEntities() {
        registerEntity(EntityFireCreeper.class, getCreeperName("FireCreeper"), ECVars.fireCreeperID, creeperEggGreen, new Color(227, 111, 24).getRGB());
        registerEntity(EntityWaterCreeper.class, getCreeperName("WaterCreeper"), ECVars.waterCreeperID, creeperEggGreen, new Color(59, 115, 205).getRGB());
        registerEntity(EntityElectricCreeper.class, getCreeperName("ElectricCreeper"), ECVars.electricCreeperID, creeperEggGreen, new Color(251, 234, 57).getRGB());
        registerEntity(EntityCookieCreeper.class, getCreeperName("CookieCreeper"), ECVars.cookieCreeperID, creeperEggGreen, new Color(202, 147, 98).getRGB());
        registerEntity(EntityDarkCreeper.class, getCreeperName("DarkCreeper"), ECVars.darkCreeperID, creeperEggGreen, new Color(50, 50, 50).getRGB());
        registerEntity(EntityLightCreeper.class, getCreeperName("LightCreeper"), ECVars.lightCreeperID, creeperEggGreen, new Color(255, 244, 125).getRGB());
        registerEntity(EntityEarthCreeper.class, getCreeperName("EarthCreeper"), ECVars.earthCreeperID, creeperEggGreen, new Color(93, 50, 0).getRGB());
        registerEntity(EntityMagmaCreeper.class, getCreeperName("MagmaCreeper"), ECVars.magmaCreeperID, creeperEggGreen, new Color(165, 0, 16).getRGB());
        registerEntity(EntityReverseCreeper.class, getCreeperName("ReverseCreeper"), ECVars.reverseCreeperID, Color.black.getRGB(), creeperEggGreen);
        registerEntity(EntityIceCreeper.class, getCreeperName("IceCreeper"), ECVars.iceCreeperID, creeperEggGreen, Color.white.getRGB());
        registerEntity(EntityFriendlyCreeper.class, getCreeperName("FriendlyCreeper"), ECVars.friendlyCreeperID, creeperEggGreen, new Color(215, 113, 211).getRGB());
        registerEntity(EntityGhostCreeper.class, getCreeperName("GhostCreeper"), ECVars.ghostCreeperID, 99999, 99999);
        registerEntity(EntityFakeIllusionCreeper.class, getCreeperName("IllusionCreeper"), ECVars.fakeIllusionCreeperID, 99999, 99999);
        registerEntity(EntityIllusionCreeper.class, getCreeperName("IllusionCreeper"), ECVars.illusionCreeperID, creeperEggGreen, new Color(158, 158, 158).getRGB());
        registerEntity(EntityPsyhicCreeper.class, getCreeperName("PsychicCreeper"), ECVars.psychicCreeperID, creeperEggGreen, new Color(121, 51, 142).getRGB());

        registerEntity(EntityWindCreeper.class, getCreeperName("WindCreeper"), ECVars.windCreeperID, creeperEggGreen, new Color(95, 250, 203).getRGB());
        
        loadSpawn();
    }

    private static void loadSpawn() {
        addOverWorldSpawn(EntityFireCreeper.class, ECVars.fireCreeperSpawn, 1, 3);
        addOverWorldSpawn(EntityWaterCreeper.class, ECVars.waterCreeperSpawn, 1, 3);
        addOverWorldSpawn(EntityElectricCreeper.class, ECVars.electricCreeperSpawn, 1, 3);
        addOverWorldSpawn(EntityCookieCreeper.class, ECVars.cookieCreeperSpawn, 1, 2);
        addOverWorldSpawn(EntityDarkCreeper.class, ECVars.darkCreeperSpawn, 1, 3);
        addOverWorldSpawn(EntityLightCreeper.class, ECVars.lightCreeperSpawn, 1, 3);
        addOverWorldSpawn(EntityEarthCreeper.class, ECVars.earthCreeperSpawn, 1, 3);
        addNetherSpawn(EntityMagmaCreeper.class, ECVars.magmaCreeperSpawn, 1, 2);
        addOverWorldSpawn(EntityReverseCreeper.class, ECVars.reverseCreeperSpawn, 1, 3);
        addOverWorldSpawn(EntityIceCreeper.class, ECVars.iceCreeperSpawn, 1, 3);
        addOverWorldSpawn(EntityPsyhicCreeper.class, ECVars.psychicCreeperSpawn, 1, 3);
        
        addOverWorldSpawn(EntityWindCreeper.class, ECVars.windCreeperSpawn, 1, 2);
    }

    @SuppressWarnings("unused")
    private static void addOverWorldSpawn(Class<? extends EntityLiving> entityClass, int spawnprob, int min, int max, EnumCreatureType type) {
        for (int i = 0; i < typeList.length; i++) {
            EntityRegistry.addSpawn(entityClass, spawnprob, min, max, type, BiomeDictionary.getBiomesForType(typeList[i]));
        }
    }

    private static void addOverWorldSpawn(Class<? extends EntityLiving> entityClass, int spawnprob, int min, int max) {
        for (int i = 0; i < typeList.length; i++) {
            EntityRegistry.addSpawn(entityClass, spawnprob, min, max, EnumCreatureType.monster, BiomeDictionary.getBiomesForType(typeList[i]));
        }
    }

    private static void addNetherSpawn(Class<? extends EntityLiving> entityClass, int spawnprob, int min, int max) {
        EntityRegistry.addSpawn(entityClass, spawnprob, min, max, EnumCreatureType.monster, BiomeDictionary.getBiomesForType(Type.NETHER));
    }

    @SuppressWarnings("unused")
    private static void addEndSpawn(Class<? extends EntityLiving> entityClass, int spawnprob, int min, int max) {
        EntityRegistry.addSpawn(entityClass, spawnprob, min, max, EnumCreatureType.monster, BiomeDictionary.getBiomesForType(Type.END));
    }

    private static String getCreeperName(String mob) {
        return ("elementalcreepers:" + mob);
    }

    @SuppressWarnings("unchecked")
    private static void registerEntity(Class<? extends Entity> entityClass, String entityName, int id, int bkEggColor, int fgEggColor) {
        EntityRegistry.instance();

        EntityRegistry.registerGlobalEntityID(entityClass, entityName, id);
        if (bkEggColor != 99999 && fgEggColor != 99999)
            EntityList.entityEggs.put(Integer.valueOf(id), new EntityEggInfo(id, bkEggColor, fgEggColor));
    }
}
