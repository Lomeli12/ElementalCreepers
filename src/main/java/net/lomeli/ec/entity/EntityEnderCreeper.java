package net.lomeli.ec.entity;

import java.util.Collections;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import net.lomeli.lomlib.util.EntityUtil;

public class EntityEnderCreeper extends EntityBaseCreeper {
    private int teleportDelay;

    public EntityEnderCreeper(World par1World) {
        super(par1World);
        this.tasks.taskEntries.clear();
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAICreeperSwell(this));
        this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityOcelot.class, 6.0F, 1.0D, 1.2D));
        this.tasks.addTask(4, new EntityAIAttackOnCollide(this, 1.0D, false));
        this.tasks.addTask(5, new EntityAIWander(this, 0.8D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityEnderCreeper.AIFindPlayer());
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.explosionRadius = 3;
    }

    @Override
    public void onLivingUpdate() {
        if (!this.worldObj.isRemote) {
            if (this.teleportDelay > 0)
                --this.teleportDelay;
            if (this.isEntityAlive()) {
                if (this.worldObj.isDaytime()) {
                    float f = this.getBrightness(1.0F);
                    if (f > 0.5F && this.worldObj.canSeeSky(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)))
                            && this.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F) {
                        this.setAttackTarget(null);
                        EntityUtil.teleportRandomly(this.worldObj, this);
                        this.teleportDelay = 50;
                    }
                }

                if (this.isWet()) {
                    this.damageEntity(DamageSource.drown, 1f);
                    EntityUtil.teleportRandomly(this.worldObj, this);
                }

                if (this.getAttackTarget() != null) {
                    if (this.getAttackTarget() instanceof EntityPlayer) {
                        if (this.getAttackTarget().getDistanceToEntity(this) > 6.0D && this.rand.nextInt(100) < 5) {
                            EntityUtil.teleportToEntity(worldObj, this, this.getAttackTarget());
                            this.teleportDelay = 70;
                        }
                    } else if (this.getAttackTarget().getDistanceToEntity(this) > 256.0D && this.teleportDelay++ >= 30 && EntityUtil.teleportToEntity(worldObj, this, this.getAttackTarget()))
                        this.teleportDelay = 0;
                } else
                    this.teleportDelay = 0;
            }
        }
        super.onLivingUpdate();
    }

    @Override
    protected void damageEntity(DamageSource damageSrc, float damageAmount) {
        super.damageEntity(damageSrc, damageAmount);
        if (!this.worldObj.isRemote && this.rand.nextInt(100) < 15) {
            int newX = (int) this.posX + (this.rand.nextInt(5) * (this.rand.nextBoolean() ? 1 : -1));
            int newY = (int) this.posY + (this.rand.nextInt(5) * (this.rand.nextBoolean() ? 1 : -1));
            int newZ = (int) this.posZ + (this.rand.nextInt(5) * (this.rand.nextBoolean() ? 1 : -1));
            EntityUtil.teleportTo(worldObj, this, newX, newY, newZ);
        }
    }

    @Override
    public void explosion(int power, boolean flag) {
        int radius = getPowered() ? (int) (this.explosionRadius * 1.5F) : this.explosionRadius;
        this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, radius, flag);

        List<?> entities = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(radius, radius, radius));
        if (entities != null && !entities.isEmpty()) {
            for (Object obj : entities) {
                if (obj != null && obj instanceof EntityLivingBase) {
                    if (rand.nextInt(100) <= 25)
                        EntityUtil.teleportRandomly(worldObj, (EntityLivingBase) obj);
                }
            }
        }
    }

    public boolean shouldAttackPlayer(EntityPlayer player) {
        ItemStack itemstack = player.inventory.armorInventory[3];

        if (itemstack != null && itemstack.getItem() == Item.getItemFromBlock(Blocks.pumpkin))
            return false;
        else {
            Vec3 vec3 = player.getLook(1.0F).normalize();
            Vec3 vec31 = new Vec3(this.posX - player.posX, this.getEntityBoundingBox().minY + (double) (this.height / 2.0F) - (player.posY + (double) player.getEyeHeight()), this.posZ - player.posZ);
            double d0 = vec31.lengthVector();
            vec31 = vec31.normalize();
            double d1 = vec3.dotProduct(vec31);
            return d1 > 1.0D - 0.025D / d0 ? player.canEntityBeSeen(this) : false;
        }
    }

    class AIFindPlayer extends EntityAINearestAttackableTarget {
        private EntityPlayer field_179448_g;
        private int field_179450_h;
        private int field_179451_i;
        private EntityEnderCreeper field_179449_j = EntityEnderCreeper.this;

        public AIFindPlayer() {
            super(EntityEnderCreeper.this, EntityPlayer.class, true);
        }

        public boolean shouldExecute() {
            double d0 = this.getTargetDistance();
            List list = this.taskOwner.worldObj.getEntitiesWithinAABB(EntityPlayer.class, this.taskOwner.getEntityBoundingBox().expand(d0, 4.0D, d0), this.targetEntitySelector);
            Collections.sort(list, this.theNearestAttackableTargetSorter);

            if (list.isEmpty()) {
                return false;
            } else {
                this.field_179448_g = (EntityPlayer) list.get(0);
                return true;
            }
        }

        public void startExecuting() {
            this.field_179450_h = 5;
            this.field_179451_i = 0;
        }

        public void resetTask() {
            this.field_179448_g = null;
            super.resetTask();
        }

        public boolean continueExecuting() {
            if (this.field_179448_g != null) {
                if (!this.field_179449_j.shouldAttackPlayer(this.field_179448_g))
                    return false;
                else {
                    this.field_179449_j.faceEntity(this.field_179448_g, 10.0F, 10.0F);
                    return true;
                }
            } else {
                return super.continueExecuting();
            }
        }

        public void updateTask() {
            if (this.field_179448_g != null) {
                if (--this.field_179450_h <= 0) {
                    this.targetEntity = this.field_179448_g;
                    this.field_179448_g = null;
                    super.startExecuting();
                    this.field_179449_j.playSound("mob.endermen.stare", 1.0F, 1.0F);
                }
            } else {
                if (this.targetEntity != null) {
                    if (this.targetEntity instanceof EntityPlayer && this.field_179449_j.shouldAttackPlayer((EntityPlayer) this.targetEntity)) {
                        if (this.targetEntity.getDistanceSqToEntity(this.field_179449_j) < 16.0D) {
                            EntityUtil.teleportRandomly(this.field_179449_j.worldObj, this.field_179449_j);
                        }

                        this.field_179451_i = 0;
                    } else if (this.targetEntity.getDistanceSqToEntity(this.field_179449_j) > 256.0D && this.field_179451_i++ >= 30 && EntityUtil.teleportToEntity(this.field_179449_j.worldObj, this.field_179449_j, this.targetEntity)) {
                        this.field_179451_i = 0;
                    }
                }

                super.updateTask();
            }
        }
    }
}
