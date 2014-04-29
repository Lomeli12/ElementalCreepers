package net.lomeli.ec.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import net.lomeli.lomlib.entity.EntityUtil;

public class EntityEnderCreeper extends EntityBaseCreeper {
    private int teleportDelay;
    private int stareTimer;

    public EntityEnderCreeper(World par1World) {
        super(par1World);
        this.explosionRadius = 3;
    }

    @Override
    public void onLivingUpdate() {
        if (!this.worldObj.isRemote) {
            if (this.isEntityAlive()) {
                if (this.worldObj.isDaytime()) {
                    float f = this.getBrightness(1.0F);

                    if (f > 0.5F && this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ))
                            && this.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F) {
                        this.entityToAttack = null;
                        EntityUtil.teleportRandomly(this);
                    }
                }

                if (this.isWet()) {
                    this.damageEntity(DamageSource.drown, 1f);
                    EntityUtil.teleportRandomly(this);
                }

                if (this.entityToAttack != null) {
                    if (this.entityToAttack instanceof EntityPlayer) {
                        if (this.entityToAttack.getDistanceSqToEntity(this) < 16.0D)
                            EntityUtil.teleportRandomly(this);
                        this.teleportDelay = 0;

                        if (this.rand.nextInt(1000) < 5)
                            EntityUtil.teleportToEntity(this, this.entityToAttack);
                    }else if (this.entityToAttack.getDistanceSqToEntity(this) > 256.0D && this.teleportDelay++ >= 30 && EntityUtil.teleportToEntity(this, this.entityToAttack))
                        this.teleportDelay = 0;
                }else
                    this.teleportDelay = 0;
            }
        }
        super.onLivingUpdate();
    }

    @Override
    public void explosion(int power, boolean flag) {
        int radius = getPowered() ? (int) (this.explosionRadius * 1.5F) : this.explosionRadius;
        this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, radius, flag);
    }

    @Override
    protected Entity findPlayerToAttack() {
        EntityPlayer entityplayer = this.worldObj.getClosestVulnerablePlayerToEntity(this, 16.0D);
        if (entityplayer != null) {
            if (this.stareTimer == 0)
                this.worldObj.playSoundAtEntity(entityplayer, "mob.endermen.stare", 1f, 1f);
            if (this.stareTimer++ == 5) {
                this.stareTimer = 0;
                return entityplayer != null && this.canEntityBeSeen(entityplayer) ? entityplayer : null;
            }
        }else
            this.stareTimer = 0;
        return null;
    }
}
