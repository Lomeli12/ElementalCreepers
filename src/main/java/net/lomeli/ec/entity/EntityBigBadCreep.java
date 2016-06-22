package net.lomeli.ec.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.lomeli.lomlib.util.EntityUtil;

import net.lomeli.ec.core.EntityRegistering;
import net.lomeli.ec.entity.ai.EntityAIBigBadSwell;
import net.lomeli.ec.lib.ModVars;

public class EntityBigBadCreep extends EntityMob {
    protected int lastActiveTime;
    protected int timeSinceIgnited;
    protected int fuseTime = 75;
    protected int explosionRadius = 3;
    protected boolean explosionSound;
    protected boolean startIgnight;

    public EntityBigBadCreep(World world) {
        super(world);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIBigBadSwell(this));
        this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityOcelot.class, 6.0F, 1.0D, 1.2D));
        this.tasks.addTask(4, new EntityAIAttackOnCollide(this, 1.0D, false));
        this.tasks.addTask(5, new EntityAIWander(this, 0.8D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false));
        this.setSize(this.width * 6, this.height * 6);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(150);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, Byte.valueOf((byte) -1));
        this.dataWatcher.addObject(17, Byte.valueOf((byte) 0));
        this.dataWatcher.addObject(18, Byte.valueOf((byte) 0));
    }

    @Override
    public void fall(float distance, float damageMultiplier) {
        super.fall(distance, damageMultiplier);
        this.timeSinceIgnited = (int) (this.timeSinceIgnited + distance * 1.5F);

        if (this.timeSinceIgnited > this.fuseTime - 5)
            this.timeSinceIgnited = this.fuseTime - 5;
    }

    @Override
    public void onUpdate() {
        if (this.isEntityAlive()) {
            this.lastActiveTime = this.timeSinceIgnited;
            int i = this.getCreeperState();

            if (i > 0 && this.timeSinceIgnited == 0)
                this.playSound("random.fuse", 1.0F, 0.5F);

            if (this.startIgnight) {
                if (this.timeSinceIgnited < 0)
                    this.timeSinceIgnited = 0;

                if (++this.timeSinceIgnited >= this.fuseTime)
                    explode();
            } else {
                this.timeSinceIgnited += i;

                if (this.timeSinceIgnited < 0)
                    this.timeSinceIgnited = 0;

                if (this.timeSinceIgnited >= this.fuseTime)
                    explode();
            }
        }
        super.onUpdate();
    }

    public void explode() {
        if (!this.worldObj.isRemote) {
            boolean flag = this.worldObj.getGameRules().getBoolean("mobGriefing");

            this.explosion(this.getPowered() ? 2 : 1, flag);
            this.setDead();
        }
        if (worldObj.isRemote) {
            if (explosionSound)
                worldObj.playSoundEffect(posX, posY, posZ, "random.explode", 4F, (1.0F + (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
            spawnExplosionParticle();
        }
    }

    public void explosion(int power, boolean flag) {
        this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 10 * power, flag);
        for (int i = 0; i < ModVars.bigBadAmount * power; i++) {
            try {
                Class<? extends EntityCreeper> creepClass = EntityRegistering.creeperClassList.get(this.rand.nextInt(EntityRegistering.creeperClassList.size()));
                if (creepClass != null) {
                    EntityLivingBase creeper = creepClass.getConstructor(World.class).newInstance(this.worldObj);
                    if (creeper != null) {
                        creeper.setPositionAndUpdate(this.posX, this.posY, this.posZ);
                        creeper.setRevengeTarget(this.getAttackTarget());
                        creeper.motionX = 0.8f * (this.rand.nextBoolean() ? 1 : -1);
                        creeper.motionY = 0.5f;
                        creeper.motionZ = 0.8f * (this.rand.nextBoolean() ? 1 : -1);
                        this.worldObj.spawnEntityInWorld(creeper);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public float getCreeperFlashIntensity(float par1) {
        return (this.lastActiveTime + (this.timeSinceIgnited - this.lastActiveTime) * par1) / (this.fuseTime - 2);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);

        if (this.dataWatcher.getWatchableObjectByte(17) == 1)
            par1NBTTagCompound.setBoolean("powered", true);

        par1NBTTagCompound.setShort("Fuse", (short) this.fuseTime);
        par1NBTTagCompound.setByte("ExplosionRadius", (byte) this.explosionRadius);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.dataWatcher.updateObject(17, Byte.valueOf((byte) (par1NBTTagCompound.getBoolean("powered") ? 1 : 0)));

        if (par1NBTTagCompound.hasKey("Fuse"))
            this.fuseTime = par1NBTTagCompound.getShort("Fuse");

        if (par1NBTTagCompound.hasKey("ExplosionRadius"))
            this.explosionRadius = par1NBTTagCompound.getByte("ExplosionRadius");
    }

    @Override
    protected boolean interact(EntityPlayer player) {
        ItemStack stack = player.getCurrentEquippedItem();
        if (stack != null && stack.getItem() == Items.flint_and_steel) {
            worldObj.playSoundEffect(posX + 0.5D, posY + 0.5D, posZ + 0.5D, "fire.ignite", 1.0F, rand.nextFloat() * 0.4F + 0.8F);
            player.swingItem();
            if (!worldObj.isRemote) {
                stack.damageItem(1, player);
                this.startIgnight = true;
                this.func_146079_cb();
                return true;
            }
        }
        return false;
    }

    @Override
    protected String getHurtSound() {
        return "mob.creeper.say";
    }

    @Override
    protected String getDeathSound() {
        return "mob.creeper.death";
    }

    @Override
    public void onDeath(DamageSource p_70645_1_) {
        super.onDeath(p_70645_1_);

        if (p_70645_1_.getEntity() instanceof EntitySkeleton) {
            int i = Item.getIdFromItem(Items.record_13);
            int j = Item.getIdFromItem(Items.record_wait);
            int k = i + this.rand.nextInt(j - i + 1);
            this.dropItem(Item.getItemById(k), 1);
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity p_70652_1_) {
        return true;
    }

    public boolean getPowered() {
        return this.dataWatcher.getWatchableObjectByte(17) == 1;
    }

    @Override
    protected Item getDropItem() {
        return Items.gunpowder;
    }

    public int getCreeperState() {
        return this.dataWatcher.getWatchableObjectByte(16);
    }

    public void setCreeperState(int p_70829_1_) {
        this.dataWatcher.updateObject(16, Byte.valueOf((byte) p_70829_1_));
    }

    @Override
    public void onStruckByLightning(EntityLightningBolt p_70077_1_) {
        super.onStruckByLightning(p_70077_1_);
        this.dataWatcher.updateObject(17, Byte.valueOf((byte) 1));
    }

    public boolean func_146078_ca() {
        return this.dataWatcher.getWatchableObjectByte(18) != 0;
    }

    public void func_146079_cb() {
        this.dataWatcher.updateObject(18, Byte.valueOf((byte) 1));
    }
}
