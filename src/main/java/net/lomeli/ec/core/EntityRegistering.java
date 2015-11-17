package net.lomeli.ec.core;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityCreeper;

import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import net.lomeli.lomlib.util.entity.EntityUtil;
import net.lomeli.lomlib.util.entity.SimpleEggInfo;

import net.lomeli.ec.ElementalCreepers;
import net.lomeli.ec.client.CreeperEntry;
import net.lomeli.ec.entity.*;
import net.lomeli.ec.lib.ModVars;
import net.lomeli.ec.lib.ModLib;

public class EntityRegistering {

    public static Type[] typeList = {Type.FOREST, Type.HILLS, Type.SWAMP, Type.JUNGLE, Type.WASTELAND, Type.MAGICAL, Type.BEACH, Type.SANDY, Type.SNOWY, Type.MOUNTAIN};
    public static int creeperEggGreen = 894731, count = 0;
    public static List<Class<? extends EntityCreeper>> creeperClassList = new ArrayList<Class<? extends EntityCreeper>>();

    public static void loadEntities() {
        creeperClassList.add(EntityCreeper.class);
        registerEntity(EntityFireCreeper.class, "FireCreeper", creeperEggGreen, new Color(227, 111, 24).getRGB());
        registerEntity(EntityWaterCreeper.class, "WaterCreeper", creeperEggGreen, new Color(59, 115, 205).getRGB());
        registerEntity(EntityElectricCreeper.class, "ElectricCreeper", creeperEggGreen, new Color(251, 234, 57).getRGB());
        registerEntity(EntityCookieCreeper.class, "CookieCreeper", creeperEggGreen, new Color(202, 147, 98).getRGB());
        registerEntity(EntityDarkCreeper.class, "DarkCreeper", creeperEggGreen, new Color(50, 50, 50).getRGB());
        registerEntity(EntityLightCreeper.class, "LightCreeper", creeperEggGreen, new Color(255, 244, 125).getRGB());
        registerEntity(EntityEarthCreeper.class, "EarthCreeper", creeperEggGreen, new Color(93, 50, 0).getRGB());
        registerEntity(EntityMagmaCreeper.class, "MagmaCreeper", creeperEggGreen, new Color(165, 0, 16).getRGB());
        registerEntity(EntityReverseCreeper.class, "ReverseCreeper", Color.black.getRGB(), creeperEggGreen);
        registerEntity(EntityIceCreeper.class, "IceCreeper", creeperEggGreen, Color.white.getRGB());
        registerEntity(EntityFriendlyCreeper.class, "FriendlyCreeper", creeperEggGreen, new Color(215, 113, 211).getRGB(), false, true);
        registerEntity(EntityGhostCreeper.class, "GhostCreeper", 99999, 99999, false, false);
        registerEntity(EntityIllusionCreeper.class, "IllusionCreeper", creeperEggGreen, new Color(158, 158, 158).getRGB());
        registerEntity(EntityPsyhicCreeper.class, "PsychicCreeper", creeperEggGreen, new Color(121, 51, 142).getRGB());
        registerEntity(EntitySpiderCreeper.class, "SpiderCreeper", creeperEggGreen, Color.red.getRGB());

        registerEntity(EntityWindCreeper.class, "WindCreeper", creeperEggGreen, new Color(95, 250, 203).getRGB());
        registerEntity(EntityHydrogenCreeper.class, "HydrogenCreeper", creeperEggGreen, Color.YELLOW.getRGB());
        registerEntity(EntityEnderCreeper.class, "EnderCreeper", creeperEggGreen, Color.MAGENTA.getRGB());
        registerEntity(EntityStoneCreeper.class, "StoneCreeper", creeperEggGreen, Color.DARK_GRAY.getRGB());
        registerEntity(EntitySolarCreeper.class, "SolarCreeper", creeperEggGreen, new Color(0, 25, 100).getRGB());
        registerEntity(EntityBirthdayCreeper.class, "CakeCreeper", creeperEggGreen, new Color(184, 93, 39).getRGB());
        registerEntity(EntityFireworkCreeper.class, "FireworkCreeper", Color.BLUE.getRGB(), creeperEggGreen);
        registerEntity(EntityBigBadCreep.class, "BigBadCreep", creeperEggGreen, creeperEggGreen, false, true);
        registerEntity(EntitySpringCreeper.class, "SpringCreeper", creeperEggGreen, Color.PINK.getRGB());
        registerEntity(EntitySilverCreeper.class, "SilverCreeper", creeperEggGreen, Color.LIGHT_GRAY.getRGB());
        registerEntity(EntityFurnaceCreeper.class, "FurnaceCreeper", Color.DARK_GRAY.getRGB(), creeperEggGreen);

        loadSpawn();
    }

    private static void loadSpawn() {
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
    }

    public static void addOverWorldSpawn(Class<? extends EntityLiving> entityClass, int spawnprob, int min, int max, EnumCreatureType type) {
        for (int i = 0; i < typeList.length; i++) {
            EntityRegistry.addSpawn(entityClass, spawnprob, min, max, type, BiomeDictionary.getBiomesForType(typeList[i]));
        }
    }

    public static void addOverWorldSpawn(Class<? extends EntityLiving> entityClass, int spawnprob, int min, int max) {
        for (int i = 0; i < typeList.length; i++) {
            EntityRegistry.addSpawn(entityClass, spawnprob, min, max, EnumCreatureType.MONSTER, BiomeDictionary.getBiomesForType(typeList[i]));
        }
    }

    public static void addNetherSpawn(Class<? extends EntityLiving> entityClass, int spawnprob, int min, int max) {
        EntityRegistry.addSpawn(entityClass, spawnprob, min, max, EnumCreatureType.MONSTER, BiomeDictionary.getBiomesForType(Type.NETHER));
    }

    public static void addEndSpawn(Class<? extends EntityLiving> entityClass, int spawnprob, int min, int max) {
        EntityRegistry.addSpawn(entityClass, spawnprob, min, max, EnumCreatureType.MONSTER, BiomeDictionary.getBiomesForType(Type.END));
    }

    public static String getCreeperName(String mob) {
        return (ModLib.MOD_ID + "." + mob);
    }

    public static void registerEntity(Class<? extends Entity> entityClass, String entityName, int bkEggColor, int fgEggColor) {
        registerEntity(entityClass, entityName, bkEggColor, fgEggColor, true, true);
    }

    public static void registerEntity(Class<? extends Entity> entityClass, String entityName, int bkEggColor, int fgEggColor, boolean addSpawnList, boolean addEgg) {
        EntityRegistry.registerModEntity(entityClass, entityName, count++, ElementalCreepers.instance, 64, 3, true);
        if (addEgg) {
            SimpleEggInfo info = new SimpleEggInfo(entityClass, bkEggColor, fgEggColor, getCreeperName(entityName));
            if (!EntityUtil.eggList.contains(info))
                EntityUtil.eggList.add(info);
        }
        CreeperEntry.entryList.add(new CreeperEntry(entityClass, "book.entry." + entityName));
        if (addSpawnList)
            creeperClassList.add((Class<? extends EntityCreeper>) entityClass);
    }
}
