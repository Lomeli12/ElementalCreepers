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

import net.lomeli.ec.ElementalCreepers;
import net.lomeli.ec.entity.*;
import net.lomeli.ec.lib.ECVars;

public class EntityRegistering {

    public static Type[] typeList = {Type.FOREST, Type.HILLS, Type.SWAMP, Type.JUNGLE, Type.WASTELAND, Type.MAGICAL, Type.BEACH, Type.SANDY, Type.SNOWY, Type.MOUNTAIN};
    public static int creeperEggGreen = new Color(894731).getRGB(), count = 0;
    public static List<Class<? extends EntityCreeper>> creeperClassList = new ArrayList<Class<? extends EntityCreeper>>();

    public static void loadEntities() {
        creeperClassList.add(EntityCreeper.class);
        registerEntity(EntityFireCreeper.class, getCreeperName("FireCreeper"), creeperEggGreen, new Color(227, 111, 24).getRGB());
        registerEntity(EntityWaterCreeper.class, getCreeperName("WaterCreeper"), creeperEggGreen, new Color(59, 115, 205).getRGB());
        registerEntity(EntityElectricCreeper.class, getCreeperName("ElectricCreeper"), creeperEggGreen, new Color(251, 234, 57).getRGB());
        registerEntity(EntityCookieCreeper.class, getCreeperName("CookieCreeper"), creeperEggGreen, new Color(202, 147, 98).getRGB());
        registerEntity(EntityDarkCreeper.class, getCreeperName("DarkCreeper"), creeperEggGreen, new Color(50, 50, 50).getRGB());
        registerEntity(EntityLightCreeper.class, getCreeperName("LightCreeper"), creeperEggGreen, new Color(255, 244, 125).getRGB());
        registerEntity(EntityEarthCreeper.class, getCreeperName("EarthCreeper"), creeperEggGreen, new Color(93, 50, 0).getRGB());
        registerEntity(EntityMagmaCreeper.class, getCreeperName("MagmaCreeper"), creeperEggGreen, new Color(165, 0, 16).getRGB());
        registerEntity(EntityReverseCreeper.class, getCreeperName("ReverseCreeper"), Color.black.getRGB(), creeperEggGreen);
        registerEntity(EntityIceCreeper.class, getCreeperName("IceCreeper"), creeperEggGreen, Color.white.getRGB());
        registerEntity(EntityFriendlyCreeper.class, getCreeperName("FriendlyCreeper"), creeperEggGreen, new Color(215, 113, 211).getRGB(), false, true);
        registerEntity(EntityGhostCreeper.class, getCreeperName("GhostCreeper"), 99999, 99999, false, false);
        registerEntity(EntityIllusionCreeper.class, getCreeperName("IllusionCreeper"), creeperEggGreen, new Color(158, 158, 158).getRGB());
        registerEntity(EntityPsyhicCreeper.class, getCreeperName("PsychicCreeper"), creeperEggGreen, new Color(121, 51, 142).getRGB());
        registerEntity(EntitySpiderCreeper.class, getCreeperName("SpiderCreeper"), creeperEggGreen, Color.red.getRGB());

        registerEntity(EntityWindCreeper.class, getCreeperName("WindCreeper"), creeperEggGreen, new Color(95, 250, 203).getRGB());
        registerEntity(EntityHydrogenCreeper.class, getCreeperName("HydrogenCreeper"), creeperEggGreen, Color.YELLOW.getRGB());
        registerEntity(EntityEnderCreeper.class, getCreeperName("EnderCreeper"), creeperEggGreen, Color.MAGENTA.getRGB());
        registerEntity(EntityStoneCreeper.class, getCreeperName("StoneCreeper"), creeperEggGreen, Color.DARK_GRAY.getRGB());
        registerEntity(EntitySolarCreeper.class, getCreeperName("SolarCreeper"), creeperEggGreen, new Color(0, 25, 100).getRGB());
        registerEntity(EntityBirthdayCreeper.class, getCreeperName("CakeCreeper"), creeperEggGreen, new Color(184, 93, 39).getRGB());
        registerEntity(EntityFireworkCreeper.class, getCreeperName("FireworkCreeper"), Color.BLUE.getRGB(), creeperEggGreen);
        registerEntity(EntityBigBadCreep.class, getCreeperName("BigBadCreep"), creeperEggGreen, creeperEggGreen, false, true);
        registerEntity(EntitySpringCreeper.class, getCreeperName("SpringCreeper"), creeperEggGreen, Color.PINK.getRGB());
        registerEntity(EntitySilverCreeper.class, getCreeperName("SilverCreeper"), creeperEggGreen, Color.LIGHT_GRAY.getRGB());
        registerEntity(EntityFurnaceCreeper.class, getCreeperName("FurnaceCreeper"), Color.DARK_GRAY.getRGB(), creeperEggGreen);

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
        addOverWorldSpawn(EntityReverseCreeper.class, ECVars.reverseCreeperSpawn, 1, 1);
        addOverWorldSpawn(EntityIceCreeper.class, ECVars.iceCreeperSpawn, 1, 3);
        addOverWorldSpawn(EntityPsyhicCreeper.class, ECVars.psychicCreeperSpawn, 1, 3);
        addOverWorldSpawn(EntityIllusionCreeper.class, ECVars.illusionCreeperSpawn, 1, 1);
        addOverWorldSpawn(EntitySpiderCreeper.class, ECVars.spiderCreeperSpawn, 1, 3);
        addOverWorldSpawn(EntityFriendlyCreeper.class, ECVars.friendlyCreeperSpawn, 1, 2, EnumCreatureType.CREATURE);

        addOverWorldSpawn(EntityWindCreeper.class, ECVars.windCreeperSpawn, 1, 2);
        addOverWorldSpawn(EntityHydrogenCreeper.class, ECVars.hydrogenCreeperSpawn, 1, 1);
        addOverWorldSpawn(EntityEnderCreeper.class, ECVars.enderCreeperSpawn, 1, 2);
        addEndSpawn(EntityEnderCreeper.class, ECVars.enderCreeperSpawn * 5, 1, 3);
        addOverWorldSpawn(EntityStoneCreeper.class, ECVars.stoneCreeperSpawn, 1, 3);
        addOverWorldSpawn(EntitySolarCreeper.class, ECVars.solarCreeperSpawn, 1, 1);
        addOverWorldSpawn(EntityBirthdayCreeper.class, ECVars.cakeCreeperSpawn, 1, 3);
        addOverWorldSpawn(EntityFireworkCreeper.class, ECVars.fireworkCreeperSpawn, 1, 3);
        addOverWorldSpawn(EntityBigBadCreep.class, ECVars.bigBadSpawn, 1, 1);
        addOverWorldSpawn(EntitySpringCreeper.class, ECVars.springCreeperSpawn, 1, 3);
        addOverWorldSpawn(EntityFurnaceCreeper.class, ECVars.furnaceCreeperSpawn, 1, 3);
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
        return ("elementalcreepers:" + mob);
    }

    public static void registerEntity(Class<? extends Entity> entityClass, String entityName, int bkEggColor, int fgEggColor) {
        registerEntity(entityClass, entityName, bkEggColor, fgEggColor, true, true);
    }

    public static void registerEntity(Class<? extends Entity> entityClass, String entityName, int bkEggColor, int fgEggColor, boolean addSpawnList, boolean addEgg) {
        EntityUtil.registerEntity(entityClass, entityName, ElementalCreepers.instance, bkEggColor, fgEggColor, count++, addEgg);
        if (addSpawnList)
            creeperClassList.add((Class<? extends EntityCreeper>) entityClass);
    }
}
