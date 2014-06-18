package net.lomeli.ec.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

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
                    }else if (this.entityToAttack.getDistanceSqToEntity(this) > 256.0D && this.teleportDelay++ >= 30 && teleportToEntity(this, this.entityToAttack))
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

    public static boolean teleportRandomly(EntityLivingBase entity) {
        double var1 = entity.posX + (entity.worldObj.rand.nextDouble() - 0.5D) * 64.0D;
        double var3 = entity.posY + (entity.worldObj.rand.nextInt(64) - 32);
        double var5 = entity.posZ + (entity.worldObj.rand.nextDouble() - 0.5D) * 64.0D;
        return teleportTo(entity, var1, var3, var5);
    }

    public static boolean teleportToEntity(EntityLivingBase entity, Entity par1Entity) {
        Vec3 var2 = entity.worldObj.getWorldVec3Pool().getVecFromPool(entity.posX - par1Entity.posX, entity.boundingBox.minY + entity.height / 2.0F - par1Entity.posY + par1Entity.getEyeHeight(),
                entity.posZ - par1Entity.posZ);
        var2 = var2.normalize();
        double var3 = 16.0D;
        double var5 = entity.posX + (entity.worldObj.rand.nextDouble() - 0.5D) * 8.0D - var2.xCoord * var3;
        double var7 = entity.posY + (entity.worldObj.rand.nextInt(16) - 8) - var2.yCoord * var3;
        double var9 = entity.posZ + (entity.worldObj.rand.nextDouble() - 0.5D) * 8.0D - var2.zCoord * var3;
        return teleportTo(entity, var5, var7, var9);
    }

    public static boolean teleportTo(EntityLivingBase entity, double par1, double par3, double par5) {
        double var7 = entity.posX;
        double var9 = entity.posY;
        double var11 = entity.posZ;
        entity.posX = par1;
        entity.posY = par3;
        entity.posZ = par5;
        boolean var13 = false;
        int var14 = MathHelper.floor_double(entity.posX);
        int var15 = MathHelper.floor_double(entity.posY);
        int var16 = MathHelper.floor_double(entity.posZ);
        Block var18;

        if (entity.worldObj.blockExists(var14, var15, var16)) {
            boolean var17 = false;

            while (!var17 && var15 > 0) {
                var18 = entity.worldObj.getBlock(var14, var15 - 1, var16);

                if (var18 != null && var18.getMaterial().blocksMovement())
                    var17 = true;
                else {
                    --entity.posY;
                    --var15;
                }
            }

            if (var17) {
                entity.setPositionAndUpdate(entity.posX, entity.posY, entity.posZ);

                if (entity.worldObj.getCollidingBoundingBoxes(entity, entity.boundingBox).isEmpty() && !entity.worldObj.isAnyLiquid(entity.boundingBox))
                    var13 = true;
            }
        }

        if (!var13) {
            entity.setPositionAndUpdate(var7, var9, var11);
            return false;
        }else {
            short var30 = 128;

            for (int j = 0; j < var30; ++j) {
                double var19 = j / (var30 - 1.0D);
                float var21 = (entity.worldObj.rand.nextFloat() - 0.5F) * 0.2F;
                float var22 = (entity.worldObj.rand.nextFloat() - 0.5F) * 0.2F;
                float var23 = (entity.worldObj.rand.nextFloat() - 0.5F) * 0.2F;
                double var24 = var7 + (entity.posX - var7) * var19 + (entity.worldObj.rand.nextDouble() - 0.5D) * entity.width * 2.0D;
                double var26 = var9 + (entity.posY - var9) * var19 + entity.worldObj.rand.nextDouble() * entity.height;
                double var28 = var11 + (entity.posZ - var11) * var19 + (entity.worldObj.rand.nextDouble() - 0.5D) * entity.width * 2.0D;
                entity.worldObj.spawnParticle("portal", var24, var26, var28, var21, var22, var23);
            }

            entity.worldObj.playSoundEffect(var7, var9, var11, "mob.endermen.portal", 1.0F, 1.0F);
            entity.playSound("mob.endermen.portal", 1.0F, 1.0F);
            return true;
        }
    }
}
