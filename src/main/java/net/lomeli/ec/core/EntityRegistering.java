package net.lomeli.ec.core;

import java.awt.Color;

import net.lomeli.ec.entity.EntityElectricCreeper;
import net.lomeli.ec.entity.EntityFireCreeper;
import net.lomeli.ec.entity.EntityWaterCreeper;
import net.lomeli.ec.lib.ECVars;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityEggInfo;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.StatCollector;

import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

import cpw.mods.fml.common.registry.EntityRegistry;

public class EntityRegistering {
    
    private static Type[] typeList = { Type.FOREST, Type.HILLS, Type.SWAMP, Type.JUNGLE, Type.WASTELAND, Type.MAGICAL, Type.BEACH, 
        Type.DESERT, Type.FROZEN, Type.MOUNTAIN};
    
    public static void loadEntities(){
        registerEntity(EntityFireCreeper.class, getCreeperName("FireCreeper"), ECVars.fireCreeperID, new Color(894731).getRGB(), new Color(227, 111, 24).getRGB());
        registerEntity(EntityWaterCreeper.class, getCreeperName("WaterCreeper"), ECVars.waterCreeperID, new Color(894731).getRGB(), new Color(59, 115, 205).getRGB());
        registerEntity(EntityElectricCreeper.class, getCreeperName("ElectricCreeper"), ECVars.electricCreeperID, new Color(894731).getRGB(), new Color(251, 234, 57).getRGB());
        
        loadSpawn();
    }
    
    private static void loadSpawn(){
        addOverWorldSpawn(EntityFireCreeper.class, 2, 1, 3);
    }
    
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
    
    private static void addEndSpawn(Class<? extends EntityLiving> entityClass, int spawnprob, int min, int max) {
        EntityRegistry.addSpawn(entityClass, spawnprob, min, max, EnumCreatureType.monster, BiomeDictionary.getBiomesForType(Type.END));
    }
    
    private static String getCreeperName(String mob){
        return ("entity.elementalcreepers:" + mob);
    }
    
    @SuppressWarnings("unchecked")
    private static void registerEntity(Class<? extends Entity> entityClass, String entityName, int id, int bkEggColor, int fgEggColor) {
        EntityRegistry.instance();

        EntityRegistry.registerGlobalEntityID(entityClass, entityName, id);
        EntityList.entityEggs.put(Integer.valueOf(id), new EntityEggInfo(id, bkEggColor, fgEggColor));
    }
}
