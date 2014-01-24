package net.lomeli.ec.entity;

import net.lomeli.ec.entity.explosion.ExplosionWind;
import net.lomeli.ec.lib.ECVars;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class EntityWindCreeper extends EntityBaseCreeper {

    public EntityWindCreeper(World par1World) {
        super(par1World, false);
        this.explosionRadius = ECVars.windCreeperRadius;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(0.5D);
    }

    @Override
    public void explosion(int power, boolean flag) {
        int exPower = this.explosionRadius * power;
        createWindGust(this, posX, posY, posZ, (float) exPower, true);
    }

    private ExplosionWind createWindGust(Entity entity, double x, double y, double z, float strength, boolean flag) {
        ExplosionWind explosion = new ExplosionWind(worldObj, entity, x, y, z, strength, ECVars.windCreeperPower);
        BiomeGenBase biome = worldObj.getBiomeGenForCoords((int) x, (int) y);
        boolean flammingFlag = false;
        if (biome != null) {
            if (BiomeDictionary.isBiomeRegistered(biome)) {
                if (BiomeDictionary.isBiomeOfType(biome, Type.NETHER) || BiomeDictionary.isBiomeOfType(biome, Type.WASTELAND))
                    flammingFlag = true;
            }
        }
        explosion.isFlaming = flammingFlag;
        explosion.isSmoking = flag;
        explosion.doExplosionA();
        explosion.doExplosionB(true);
        return explosion;
    }
}
