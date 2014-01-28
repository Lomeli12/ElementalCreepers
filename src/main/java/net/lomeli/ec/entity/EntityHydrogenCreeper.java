package net.lomeli.ec.entity;

import java.util.List;

import net.lomeli.ec.lib.ECVars;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class EntityHydrogenCreeper extends EntityBaseCreeper {

    public EntityHydrogenCreeper(World par1World) {
        super(par1World);
        this.explosionRadius = ECVars.hydrogenCreeperRadius;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void explosion(int power, boolean flag) {
        int exPower = this.explosionRadius * power;
        this.timeSinceIgnited = this.fuseTime;
        List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(this.explosionRadius, this.explosionRadius, this.explosionRadius));

        for (Entity ent : list) {
            if (ent instanceof EntityLivingBase) {
                if (this.getDistanceToEntity(ent) < this.explosionRadius) {
                    if (this.getDistanceToEntity(ent) < (this.explosionRadius / 4))
                        ((EntityLivingBase) ent).addPotionEffect(new PotionEffect(Potion.wither.id, 400, 1));
                    else
                        ((EntityLivingBase) ent).addPotionEffect(new PotionEffect(Potion.poison.id, 500, 2));
                }
            }
        }

        this.worldObj.createExplosion(this, posX, posY, posZ, (float) exPower, flag);
        worldObj.playSoundEffect(posX, posY, posZ, "random.explode", 4F, (1.0F + (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
        spawnExplosionParticle();
    }
}