package net.lomeli.ec.entity;

import net.lomeli.ec.entity.ai.EntityAIFriendlyCreeperSwell;
import net.lomeli.ec.entity.explosion.ExplosionFriendly;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityFriendlyCreeper extends EntityTameable {
    protected EntityLivingBase owner;
    protected ItemStack armor;

    protected int lastActiveTime;
    protected int timeSinceIgnited;
    protected int fuseTime = 30;
    protected int explosionRadius = 3;
    protected int coolDownTime;
    protected boolean explosionSound;

    private float field_70926_e;
    private float field_70924_f;

    public EntityFriendlyCreeper(World par1World) {
        super(par1World);
        this.setSize(1.0F, 2.0F);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, this.aiSit);
        this.tasks.addTask(3, new EntityAIFriendlyCreeperSwell(this));
        this.tasks.addTask(4, new EntityAIAvoidEntity(this, EntityOcelot.class, 6.0F, 0.25F, 0.3F));
        this.tasks.addTask(5, new EntityAIAttackOnCollide(this, 1.0D, true));
        this.tasks.addTask(6, new EntityAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
        this.tasks.addTask(7, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(10, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(0.25);

        if (this.isTamed())
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(20.0D);
        else
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(8.0D);
    }

    @Override
    public boolean isAIEnabled() {
        return true;
    }

    @Override
    public void setAttackTarget(EntityLivingBase par1EntityLivingBase) {
        super.setAttackTarget(par1EntityLivingBase);

        if (par1EntityLivingBase == null)
            this.setAngry(false);
        else if (!this.isTamed())
            this.setAngry(true);
    }

    @Override
    protected void updateAITick() {
        this.dataWatcher.updateObject(18, Float.valueOf(this.getHealth()));
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(20, Byte.valueOf((byte) -1));
        this.dataWatcher.addObject(21, Byte.valueOf((byte) 0));
        this.dataWatcher.addObject(18, new Float(this.getHealth()));
        this.dataWatcher.addObject(19, new Byte((byte) 0));
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 8;
    }

    public boolean isAngry() {
        return (this.dataWatcher.getWatchableObjectByte(16) & 2) != 0;
    }

    public void setAngry(boolean par1) {
        byte b0 = this.dataWatcher.getWatchableObjectByte(16);

        if (par1) {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte) (b0 | 2)));
        } else {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte) (b0 & -3)));
        }
    }

    @Override
    protected boolean canDespawn() {
        return !this.isTamed() && this.ticksExisted > 2400;
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
    protected float getSoundVolume() {
        return 0.4F;
    }

    @Override
    protected int getDropItemId() {
        return Item.gunpowder.itemID;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
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

                    doFriendlyExplosion(flag);
                }
            }
        }
        if (isSitting())
            this.rotationPitch = 45.0F;

        super.onUpdate();
        this.field_70924_f = this.field_70926_e;

        if (this.func_70922_bv())
            this.field_70926_e += (1.0F - this.field_70926_e) * 0.4F;
        else
            this.field_70926_e += (0.0F - this.field_70926_e) * 0.4F;

        if (this.func_70922_bv())
            this.numTicksToChaseTarget = 10;
    }

    public void doFriendlyExplosion(boolean flag) {
        if (!isTamed()) {
            worldObj.createExplosion(this, this.posX, this.posY, this.posZ, (this.explosionRadius), flag);
            this.setDead();
        } else {
            if (++coolDownTime == 30) {
                createFriendlyExplosion(this.posX, this.posY, this.posZ, (this.explosionRadius));
                coolDownTime = 0;
            }
        }
    }

    public ExplosionFriendly createFriendlyExplosion(double x, double y, double z, float strength) {
        ExplosionFriendly explosion = new ExplosionFriendly(worldObj, this, x, y, z, strength);
        explosion.isFlaming = false;
        explosion.isSmoking = true;
        explosion.doExplosionA();
        explosion.doExplosionB(true);
        return explosion;
    }

    @Override
    public void onDeath(DamageSource par1DamageSource) {
        super.onDeath(par1DamageSource);

        if (par1DamageSource.getEntity() instanceof EntitySkeleton) {
            int i = Item.record13.itemID + this.rand.nextInt(Item.recordWait.itemID - Item.record13.itemID + 1);
            this.dropItem(i, 1);
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        if (this.isEntityInvulnerable())
            return false;
        else {
            Entity entity = par1DamageSource.getEntity();
            this.aiSit.setSitting(false);

            if (entity != null && !(entity instanceof EntityPlayer) && !(entity instanceof EntityArrow))
                par2 = (par2 + 1.0F) / 2.0F;

            return super.attackEntityFrom(par1DamageSource, par2);
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity par1Entity) {
        int i = this.isTamed() ? 4 : 2;
        return par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), i);
    }

    @Override
    public void setTamed(boolean par1) {
        super.setTamed(par1);

        if (par1)
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(20.0D);
        else
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(8.0D);
    }

    @Override
    public boolean interact(EntityPlayer par1EntityPlayer) {
        ItemStack itemstack = par1EntityPlayer.inventory.getCurrentItem();

        if (this.isTamed()) {
            if (itemstack != null) {
                if (Item.itemsList[itemstack.itemID] instanceof ItemFood) {
                    ItemFood itemfood = (ItemFood) Item.itemsList[itemstack.itemID];
                    if (this.dataWatcher.getWatchableObjectInt(18) < 20) {
                        if (!par1EntityPlayer.capabilities.isCreativeMode)
                            --itemstack.stackSize;

                        this.heal(itemfood.getHealAmount());

                        if (itemstack.stackSize <= 0)
                            par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack) null);

                        return true;
                    }
                }
            }
            if (par1EntityPlayer.username.equalsIgnoreCase(this.getOwnerName()) && !this.worldObj.isRemote && !this.isBreedingItem(itemstack)) {
                System.out.println(this.isSitting());
                this.setSitting(!this.isSitting());
                this.isJumping = false;
                this.setPathToEntity((PathEntity) null);
                this.setTarget((Entity) null);
                this.setAttackTarget((EntityLivingBase) null);
            }
        } else if (itemstack != null && itemstack.itemID == Item.gunpowder.itemID && !this.isAngry()) {
            if (!par1EntityPlayer.capabilities.isCreativeMode)
                --itemstack.stackSize;

            if (itemstack.stackSize <= 0)
                par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack) null);

            if (!this.worldObj.isRemote) {
                if (this.rand.nextInt(3) == 0) {
                    this.setTamed(true);
                    this.setPathToEntity((PathEntity) null);
                    this.setAttackTarget((EntityLiving) null);
                    this.setOwner(par1EntityPlayer.username);
                    this.owner = par1EntityPlayer;
                    this.playTameEffect(true);
                    this.worldObj.setEntityState(this, (byte) 7);
                } else {
                    this.playTameEffect(false);
                    this.worldObj.setEntityState(this, (byte) 6);
                }
            }

            return true;
        }

        return super.interact(par1EntityPlayer);
    }

    @Override
    public boolean isBreedingItem(ItemStack par1ItemStack) {
        return par1ItemStack != null && (par1ItemStack.getItem().itemID == Block.plantRed.blockID || par1ItemStack.getItem().itemID == Block.plantYellow.blockID);
    }

    @Override
    public boolean canMateWith(EntityAnimal par1EntityAnimal) {
        if (par1EntityAnimal == this)
            return false;
        else if (!this.isTamed())
            return false;
        else if (!(par1EntityAnimal instanceof EntityFriendlyCreeper))
            return false;
        else {
            EntityFriendlyCreeper entityCreeper = (EntityFriendlyCreeper) par1EntityAnimal;
            return !entityCreeper.isTamed() ? false : (entityCreeper.isSitting() ? false : this.isInLove() && entityCreeper.isInLove());
        }
    }

    public boolean func_70922_bv() {
        return this.dataWatcher.getWatchableObjectByte(19) == 1;
    }

    @Override
    public Entity getOwner() {
        return worldObj.getPlayerEntityByName(getOwnerName());
    }

    @Override
    public EntityAgeable createChild(EntityAgeable entityageable) {
        EntityFriendlyCreeper entity = new EntityFriendlyCreeper(this.worldObj);
        String s = this.getOwnerName();

        if (s != null && s.trim().length() > 0) {
            entity.setOwner(s);
            entity.owner = (EntityLivingBase) this.getOwner();
            entity.setTamed(true);
        }

        return entity;
    }

    public int getCreeperState() {
        return this.dataWatcher.getWatchableObjectByte(16);
    }

    public void setCreeperState(int par1) {
        this.dataWatcher.updateObject(16, Byte.valueOf((byte) par1));
    }

    @SideOnly(Side.CLIENT)
    public float getCreeperFlashIntensity(float par1) {
        return (this.lastActiveTime + (this.timeSinceIgnited - this.lastActiveTime) * par1) / (this.fuseTime - 2);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);

        par1NBTTagCompound.setShort("Fuse", (short) this.fuseTime);
        par1NBTTagCompound.setByte("ExplosionRadius", (byte) this.explosionRadius);
        par1NBTTagCompound.setInteger("coolDown", this.coolDownTime);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);

        if (par1NBTTagCompound.hasKey("Fuse"))
            this.fuseTime = par1NBTTagCompound.getShort("Fuse");

        if (par1NBTTagCompound.hasKey("ExplosionRadius"))
            this.explosionRadius = par1NBTTagCompound.getByte("ExplosionRadius");
        if (par1NBTTagCompound.hasKey("coolDown"))
            this.coolDownTime = par1NBTTagCompound.getInteger("coolDown");
    }

    @SideOnly(Side.CLIENT)
    public float getInterestedAngle(float par1) {
        return (this.field_70924_f + (this.field_70926_e - this.field_70924_f) * par1) * 0.15F * (float) Math.PI;
    }

    @SideOnly(Side.CLIENT)
    public String tamedTexture() {
        return this.isTamed() ? "textures/entities/friendlycreeper1.png" : "textures/entities/friendlycreeper0.png";
    }

    public ItemStack getArmorSlot() {
        return armor;
    }

    private void setArmorSlot(EntityPlayer player) {
        ItemStack itemstack = player.getCurrentEquippedItem();
        if (itemstack != null) {
            if (armor != null) {
                EntityItem item = new EntityItem(worldObj, posX, posY, posZ, armor);
                worldObj.spawnEntityInWorld(item);
                armor = null;
            } else {
                ItemStack copy = itemstack.copy();
                copy.stackSize = 1;
                if (!player.capabilities.isCreativeMode)
                    --itemstack.stackSize;
                if (itemstack.stackSize <= 0)
                    player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
                armor = copy;
            }
        } else {
            if (armor != null) {
                EntityItem item = new EntityItem(worldObj, posX, posY, posZ, armor);
                worldObj.spawnEntityInWorld(item);
                armor = null;
            }
        }
    }

    public void setArmor(ItemStack stack) {
        if (armor != null) {
            EntityItem item = new EntityItem(worldObj, posX, posY, posZ, armor);
            worldObj.spawnEntityInWorld(item);
            armor = null;
        }
        armor = stack;
    }
}
