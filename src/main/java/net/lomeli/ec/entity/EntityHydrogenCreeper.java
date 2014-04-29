package net.lomeli.ec.entity;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import net.lomeli.ec.lib.ECVars;

public class EntityHydrogenCreeper extends EntityBaseCreeper {

    public EntityHydrogenCreeper(World par1World) {
        super(par1World);
        this.explosionRadius = ECVars.hydrogenCreeperRadius;
    }

    @Override
    public void onUpdate() {
        if (this.isEntityAlive()) {
            this.lastActiveTime = this.timeSinceIgnited;
            int i = this.getCreeperState();

            if (i > 0 && this.timeSinceIgnited == 0)
                this.playSound("random.fuse", 1.0F, 0.5F);

            this.timeSinceIgnited += i;

            if (this.timeSinceIgnited < 0)
                this.timeSinceIgnited = 0;

            if (this.timeSinceIgnited >= this.fuseTime) {
                this.timeSinceIgnited = this.fuseTime;

                if (!this.worldObj.isRemote) {
                    boolean flag = this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");

                    this.explosion(this.getPowered() ? 2 : 1, flag);

                    if (this.diesAfterExplosion())
                        this.setDead();

                    if (explosionSound)
                        worldObj.playSoundEffect(posX, posY, posZ, "random.explode", 4F, (1.0F + (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.2F) * 0.7F);

                }
                spawnExplosionParticle();
            }
        }
        super.onUpdate();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void explosion(int power, boolean flag) {
        int exPower = this.explosionRadius * power;
        this.timeSinceIgnited = this.fuseTime;
        List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(this.explosionRadius, this.explosionRadius, this.explosionRadius));

        for (Entity ent : list) {
            if (ent instanceof EntityLivingBase) {
                if (this.getDistanceSqToEntity(ent) < this.explosionRadius) {
                    if (this.getDistanceSqToEntity(ent) < (this.explosionRadius / 4))
                        ((EntityLivingBase) ent).addPotionEffect(new PotionEffect(Potion.wither.id, 400, 1));
                    else
                        ((EntityLivingBase) ent).addPotionEffect(new PotionEffect(Potion.poison.id, 500, 2));
                }
            }
        }

        this.worldObj.createExplosion(this, posX, posY, posZ, exPower, flag);
        worldObj.playSoundEffect(posX, posY, posZ, "random.explode", 4F, (1.0F + (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
        spawnExplosionParticle();
    }
}