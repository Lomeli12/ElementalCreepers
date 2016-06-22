package net.lomeli.ec.core;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityCreeper;

import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import net.lomeli.ec.ElementalCreepers;
import net.lomeli.ec.client.CreeperEntry;
import net.lomeli.ec.entity.*;
import net.lomeli.ec.lib.ModVars;

public class EntityRegistering {

    public static Type[] typeList = {Type.FOREST, Type.HILLS, Type.SWAMP, Type.JUNGLE, Type.WASTELAND, Type.MAGICAL, Type.BEACH, Type.SANDY, Type.SNOWY, Type.MOUNTAIN};
    public static int creeperEggGreen = 894731, count = 0;
    public static List<Class<? extends EntityCreeper>> creeperClassList = new ArrayList<Class<? extends EntityCreeper>>();

    public static void loadEntities() {
        creeperClassList.add(EntityCreeper.class);
        registerEntity(EntityFireCreeper.class, "FireCreeper", creeperEggGreen, 0xE36F18);
        registerEntity(EntityWaterCreeper.class, "WaterCreeper", creeperEggGreen, 0x3B73CD);
        registerEntity(EntityElectricCreeper.class, "ElectricCreeper", creeperEggGreen, 0xFBEA39);
        registerEntity(EntityCookieCreeper.class, "CookieCreeper", creeperEggGreen, 0xCA9362);
        registerEntity(EntityDarkCreeper.class, "DarkCreeper", creeperEggGreen, 0x323232);
        registerEntity(EntityLightCreeper.class, "LightCreeper", creeperEggGreen, 0xFFF47D);
        registerEntity(EntityEarthCreeper.class, "EarthCreeper", creeperEggGreen, 0x5D3200);
        registerEntity(EntityMagmaCreeper.class, "MagmaCreeper", creeperEggGreen, 0xA50010);
        registerEntity(EntityReverseCreeper.class, "ReverseCreeper", 0x0, creeperEggGreen);
        registerEntity(EntityIceCreeper.class, "IceCreeper", creeperEggGreen, 0xFFFFFF);
        registerEntity(EntityFriendlyCreeper.class, "FriendlyCreeper", creeperEggGreen, 0xD771D3, false, true);
        registerEntity(EntityGhostCreeper.class, "GhostCreeper", 99999, 99999, false, false);
        registerEntity(EntityIllusionCreeper.class, "IllusionCreeper", creeperEggGreen, 0x9E9E9E);
        registerEntity(EntityPsyhicCreeper.class, "PsychicCreeper", creeperEggGreen, 0x79338E);
        registerEntity(EntitySpiderCreeper.class, "SpiderCreeper", creeperEggGreen, 0xFF0000);

        registerEntity(EntityWindCreeper.class, "WindCreeper", creeperEggGreen, 0x5FFACB);
        registerEntity(EntityHydrogenCreeper.class, "HydrogenCreeper", creeperEggGreen, 0xFFFF00);
        registerEntity(EntityEnderCreeper.class, "EnderCreeper", creeperEggGreen, 0xFF00FF);
        registerEntity(EntityStoneCreeper.class, "StoneCreeper", creeperEggGreen, 0x404040);
        registerEntity(EntitySolarCreeper.class, "SolarCreeper", creeperEggGreen, 0x001964);
        registerEntity(EntityBirthdayCreeper.class, "CakeCreeper", creeperEggGreen, 0xB85D27);
        registerEntity(EntityFireworkCreeper.class, "FireworkCreeper", 0x0000FF, creeperEggGreen);
        registerEntity(EntityBigBadCreep.class, "BigBadCreep", creeperEggGreen, creeperEggGreen, false, true);
        registerEntity(EntitySpringCreeper.class, "SpringCreeper", creeperEggGreen, 0xFFAFAF);
        registerEntity(EntitySilverCreeper.class, "SilverCreeper", creeperEggGreen, 0xC0C0C0);
        registerEntity(EntityFurnaceCreeper.class, "FurnaceCreeper", 0x404040, creeperEggGreen);
        registerEntity(EntityWarpCreeper.class, "WarpCreeper", creeperEggGreen, 0x00CCC2);
    }

    public static void loadSpawn() {
        addOverWorldSpawn(EntityFireCreeper.class, ModVars.fireCreeperSpawn, 1, 3);
        addOverWorldSpawn(EntityWaterCreeper.class, ModVars.waterCreeperSpawn, 1, 3);
        addOverWorldSpawn(EntityElectricCreeper.class, ModVars.electricCreeperSpawn, 1, 3);
        addOverWorldSpawn(EntityCookieCreeper.class, ModVars.cookieCreeperSpawn, 1, 2);
        addOverWorldSpawn(EntityDarkCreeper.class, ModVars.darkCreeperSpawn, 1, 3);
        addOverWorldSpawn(EntityLightCreeper.class, ModVars.lightCreeperSpawn, 1, 3);
        addOverWorldSpawn(EntityEarthCreeper.class, ModVars.earthCreeperSpawn, 1, 3);
        addNetherSpawn(EntityMagmaCreeper.class, ModVars.magmaCreeperSpawn, 1, 2);
        addOverWorldSpawn(EntityReverseCreeper.class, ModVars.reverseCreeperSpawn, 1, 1);
        addOverWorldSpawn(EntityIceCreeper.class, ModVars.iceCreeperSpawn, 1, 3);
        addOverWorldSpawn(EntityPsyhicCreeper.class, ModVars.psychicCreeperSpawn, 1, 3);
        addOverWorldSpawn(EntityIllusionCreeper.class, ModVars.illusionCreeperSpawn, 1, 1);
        addOverWorldSpawn(EntitySpiderCreeper.class, ModVars.spiderCreeperSpawn, 1, 3);
        addOverWorldSpawn(EntityFriendlyCreeper.class, ModVars.friendlyCreeperSpawn, 1, 2, EnumCreatureType.CREATURE);

        addOverWorldSpawn(EntityWindCreeper.class, ModVars.windCreeperSpawn, 1, 2);
        addOverWorldSpawn(EntityHydrogenCreeper.class, ModVars.hydrogenCreeperSpawn, 1, 1);
        addOverWorldSpawn(EntityEnderCreeper.class, ModVars.enderCreeperSpawn, 1, 2);
        addEndSpawn(EntityEnderCreeper.class, ModVars.enderCreeperSpawn * 5, 1, 3);
        addOverWorldSpawn(EntityStoneCreeper.class, ModVars.stoneCreeperSpawn, 1, 3);
        addOverWorldSpawn(EntitySolarCreeper.class, ModVars.solarCreeperSpawn, 1, 1);
        addOverWorldSpawn(EntityBirthdayCreeper.class, ModVars.cakeCreeperSpawn, 1, 3);
        addOverWorldSpawn(EntityFireworkCreeper.class, ModVars.fireworkCreeperSpawn, 1, 3);
        addOverWorldSpawn(EntityBigBadCreep.class, ModVars.bigBadSpawn, 1, 1);
        addOverWorldSpawn(EntitySpringCreeper.class, ModVars.springCreeperSpawn, 1, 3);
        addOverWorldSpawn(EntityFurnaceCreeper.class, ModVars.furnaceCreeperSpawn, 1, 3);
        addOverWorldSpawn(EntityWarpCreeper.class, ModVars.warpCreeperSpawn, 1, 2);
    }

    public static void addOverWorldSpawn(Class<? extends EntityLiving> entityClass, int spawnprob, int min, int max, EnumCreatureType type) {
        for (int i = 0; i < typeList.length; i++) {
            EntityRegistry.addSpawn(entityClass, spawnprob, min, max, type, BiomeDictionary.getBiomesForType(typeList[i]));
        }
    }

    public static void addOverWorldSpawn(Class<? extends EntityLiving> entityClass, int spawnprob, int min, int max) {
        addOverWorldSpawn(entityClass, spawnprob, min, max, EnumCreatureType.MONSTER);
    }

    public static void addNetherSpawn(Class<? extends EntityLiving> entityClass, int spawnprob, int min, int max) {
        EntityRegistry.addSpawn(entityClass, spawnprob, min, max, EnumCreatureType.MONSTER, BiomeDictionary.getBiomesForType(Type.NETHER));
    }

    public static void addEndSpawn(Class<? extends EntityLiving> entityClass, int spawnprob, int min, int max) {
        EntityRegistry.addSpawn(entityClass, spawnprob, min, max, EnumCreatureType.MONSTER, BiomeDictionary.getBiomesForType(Type.END));
    }

    public static void registerEntity(Class<? extends Entity> entityClass, String entityName, int bkEggColor, int fgEggColor) {
        registerEntity(entityClass, entityName, bkEggColor, fgEggColor, true, true);
    }

    public static void registerEntity(Class<? extends Entity> entityClass, String entityName, int bkEggColor, int fgEggColor, boolean addSpawnList, boolean addEgg) {
        if (addEgg)
            EntityRegistry.registerModEntity(entityClass, entityName, count++, ElementalCreepers.instance, 64, 3, true, bkEggColor, fgEggColor);
        else
            EntityRegistry.registerModEntity(entityClass, entityName, count++, ElementalCreepers.instance, 64, 3, true);
        CreeperEntry.entryList.add(new CreeperEntry(entityClass, "book.entry." + entityName));
        if (addSpawnList)
            creeperClassList.add((Class<? extends EntityCreeper>) entityClass);
    }
}
