package net.lomeli.ec.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

import net.lomeli.ec.entity.explosion.ExplosionWind;
import net.lomeli.ec.lib.ModVars;

public class EntityWindCreeper extends EntityBaseCreeper {

    public float field_70886_e;
    public float destPos;
    public float field_70884_g;
    public float field_70888_h;
    public float field_70889_i = 1.0F;

    public EntityWindCreeper(World par1World) {
        super(par1World, false);
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        this.fallDistance = 0;

        this.field_70888_h = this.field_70886_e;
        this.field_70884_g = this.destPos;
        this.destPos = (float) (this.destPos + (this.onGround ? -1 : 4) * 0.3D);

        if (this.destPos < 0.0F)
            this.destPos = 0.0F;

        if (this.destPos > 1.0F)
            this.destPos = 1.0F;

        if (!this.onGround && this.field_70889_i < 1.0F)
            this.field_70889_i = 1.0F;

        this.field_70889_i = (float) (this.field_70889_i * 0.9D);

        if (!this.onGround && this.motionY < 0.0D)
            this.motionY *= 0.6D;

        this.field_70886_e += this.field_70889_i * 2.0F;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
    }

    @Override
    public void explosion(int power, boolean flag) {
        int exPower = ModVars.windCreeperRadius * power;
        createWindGust(this, posX, posY, posZ, exPower, true);
    }

    private ExplosionWind createWindGust(Entity entity, double x, double y, double z, float strength, boolean flag) {
        ExplosionWind explosion = new ExplosionWind(worldObj, entity, x, y, z, strength, ModVars.windCreeperPower);
        BiomeGenBase biome = worldObj.getBiomeGenForCoords(new BlockPos((int) x, (int) y, (int) z));
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
        return explosion;
    }
}
