package net.lomeli.ec.entity;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;

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
                        teleportRandomly(this);
                    }
                }

                if (this.isWet()) {
                    this.damageEntity(DamageSource.drown, 1f);
                    teleportRandomly(this);
                }

                if (this.entityToAttack != null) {
                    if (this.entityToAttack instanceof EntityPlayer) {
                        if (this.entityToAttack.getDistanceSqToEntity(this) < 16.0D)
                            teleportRandomly(this);
                        this.teleportDelay = 0;

                        if (this.rand.nextInt(1000) < 5)
                            teleportToEntity(this, this.entityToAttack);
                    } else if (this.entityToAttack.getDistanceSqToEntity(this) > 256.0D && this.teleportDelay++ >= 30 && teleportToEntity(this, this.entityToAttack))
                        this.teleportDelay = 0;
                } else
                    this.teleportDelay = 0;
            }
        }
        super.onLivingUpdate();
    }

    @Override
    public void explosion(int power, boolean flag) {
        int radius = getPowered() ? (int) (this.explosionRadius * 1.5F) : this.explosionRadius;
        this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, radius, flag);

        List<?> entities = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(radius, radius, radius));
        if (entities != null && !entities.isEmpty()) {
            for (Object obj : entities) {
                if (obj != null && obj instanceof EntityLivingBase) {
                    if (rand.nextInt(100) <= 25)
                        teleportRandomly((EntityLivingBase) obj);
                }
            }
        }
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
        } else
            this.stareTimer = 0;
        return null;
    }

    public boolean teleportRandomly(EntityLivingBase entity) {
        double var1 = entity.posX + (entity.worldObj.rand.nextDouble() - 0.5D) * 64.0D;
        double var3 = entity.posY + (entity.worldObj.rand.nextInt(64) - 32);
        double var5 = entity.posZ + (entity.worldObj.rand.nextDouble() - 0.5D) * 64.0D;
        return teleportTo(entity, var1, var3, var5);
    }

    public boolean teleportToEntity(EntityLivingBase entity, Entity par1Entity) {
        Vec3 var2 = Vec3.createVectorHelper(entity.posX - par1Entity.posX, entity.boundingBox.minY + entity.height / 2.0F - par1Entity.posY + par1Entity.getEyeHeight(),
                entity.posZ - par1Entity.posZ);
        var2 = var2.normalize();
        double var3 = 16.0D;
        double var5 = entity.posX + (entity.worldObj.rand.nextDouble() - 0.5D) * 8.0D - var2.xCoord * var3;
        double var7 = entity.posY + (entity.worldObj.rand.nextInt(16) - 8) - var2.yCoord * var3;
        double var9 = entity.posZ + (entity.worldObj.rand.nextDouble() - 0.5D) * 8.0D - var2.zCoord * var3;
        return teleportTo(entity, var5, var7, var9);
    }

    public boolean teleportTo(EntityLivingBase entity, double d0, double d1, double d2) {
        EnderTeleportEvent event = new EnderTeleportEvent(entity, d0, d1, d2, 0);
        if (MinecraftForge.EVENT_BUS.post(event))
            return false;
        for (int i = 0; i < 32; ++i) {
            entity.worldObj.spawnParticle("portal", d0, d1 + rand.nextDouble() * 2.0D, d2, this.rand.nextGaussian(), 0.0D, this.rand.nextGaussian());
        }

        boolean successful = false;
        if (!entity.worldObj.isRemote) {
            while (!worldObj.isAirBlock(MathHelper.floor_double(d0), MathHelper.floor_double(d1 + entity.getEyeHeight()), MathHelper.floor_double(d2))) {
                d1++;
            }

            if (entity instanceof EntityPlayerMP) {
                EntityPlayerMP entityplayermp = (EntityPlayerMP) entity;
                if (entityplayermp.playerNetServerHandler.func_147362_b().isChannelOpen()) {
                    if (entityplayermp.isRiding())
                        entityplayermp.mountEntity(null);
                    entityplayermp.setPositionAndUpdate(d0, d1 + 1, d2);
                    entityplayermp.fallDistance = 0f;
                }
            } else {
                if (entity.isRiding())
                    entity.mountEntity(null);
                entity.setPositionAndUpdate(d0, d1 + 1, d2);
                entity.fallDistance = 0f;
            }
            if (entity.posX == d0 && entity.posY == d1 && entity.posZ == d2)
                successful = true;
        }
        entity.worldObj.playSoundAtEntity(entity, "mob.endermen.portal", 1.0F, 1.0F);
        return successful;
    }
}
