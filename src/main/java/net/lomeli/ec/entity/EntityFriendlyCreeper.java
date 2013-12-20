package net.lomeli.ec.entity;

import net.lomeli.ec.entity.ai.EntityAIFriendlyCreeperSwell;

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
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityFriendlyCreeper extends EntityTameable {

    private Entity owner;
    private float moveSpeed;

    protected int lastActiveTime;
    protected int timeSinceIgnited;
    protected int fuseTime = 30;
    protected int explosionRadius = 3;

    public EntityFriendlyCreeper(World par1World) {
        super(par1World);
        this.moveSpeed = 0.2F;
        this.setSize(1.0F, 2.0F);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, this.aiSit);
        this.tasks.addTask(3, new EntityAIFriendlyCreeperSwell(this));
        this.tasks.addTask(4, new EntityAIAvoidEntity(this, EntityOcelot.class, 6.0F, 0.25F, 0.3F));
        this.tasks.addTask(5, new EntityAIAttackOnCollide(this, this.moveSpeed, true));
        this.tasks.addTask(6, new EntityAIFollowOwner(this, this.moveSpeed, 10.0F, 2.0F));
        this.tasks.addTask(7, new EntityAIMate(this, this.moveSpeed));
        this.tasks.addTask(8, new EntityAIWander(this, this.moveSpeed));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(10, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(0.25D);
    }

    public boolean isAIEnabled() {
        return true;
    }

    public int getMaxSafePointTries() {
        return this.getAttackTarget() == null ? 3 : 3 + (int) (this.getHealth() - 1.0F);
    }

    protected void fall(float par1) {
        super.fall(par1);
        this.timeSinceIgnited = (int) ((float) this.timeSinceIgnited + par1 * 1.5F);

        if (this.timeSinceIgnited > this.fuseTime - 5) {
            this.timeSinceIgnited = this.fuseTime - 5;
        }
    }

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
                this.aiSit.setSitting(!this.isSitting());
                this.isJumping = false;
                this.setPathToEntity((PathEntity) null);
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
                    this.aiSit.setSitting(true);
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

    public boolean isBreedingItem(ItemStack par1ItemStack) {
        return par1ItemStack != null && (par1ItemStack.getItem().itemID == Block.plantRed.blockID || par1ItemStack.getItem().itemID == Block.plantYellow.blockID);
    }

    protected void entityInit() {
        this.dataWatcher.addObject(16, Byte.valueOf((byte) -1));
        this.dataWatcher.addObject(17, Byte.valueOf((byte) 0));
        this.dataWatcher.addObject(20, Byte.valueOf((byte) 2));
        this.dataWatcher.addObject(21, Byte.valueOf((byte) 3));
        this.dataWatcher.addObject(19, new Byte((byte) 4));
    }

    public boolean isAngry() {
        return (this.dataWatcher.getWatchableObjectByte(16) & 2) != 0;
    }

    public void setAngry(boolean par1) {
        byte b0 = this.dataWatcher.getWatchableObjectByte(16);

        if (par1)
            this.dataWatcher.updateObject(16, Byte.valueOf((byte) (b0 | 2)));
        else
            this.dataWatcher.updateObject(16, Byte.valueOf((byte) (b0 & -3)));
    }

    @Override
    public void setAttackTarget(EntityLivingBase par1EntityLiving) {
        super.setAttackTarget(par1EntityLiving);
        if (par1EntityLiving instanceof EntityPlayer)
            this.setAngry(true);
    }

    protected boolean canDespawn() {
        return this.isAngry();
    }

    @Override
    public Entity getOwner() {
        return owner;
    }

    @Override
    public EntityAgeable createChild(EntityAgeable entityageable) {
        EntityFriendlyCreeper entity = new EntityFriendlyCreeper(this.worldObj);
        String s = this.getOwnerName();

        if (s != null && s.trim().length() > 0) {
            entity.setOwner(s);
            entity.owner = this.getOwner();
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
        return ((float) this.lastActiveTime + (float) (this.timeSinceIgnited - this.lastActiveTime) * par1) / (float) (this.fuseTime - 2);
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
                    boolean flag = this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing") || this.getOwner() != null;

                    if (this.getPowered())
                        this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, (float) (this.explosionRadius * 2), flag);
                    else
                        this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, (float) (this.explosionRadius), flag);

                    if (this.getOwner() == null)
                        this.setDead();
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public String tamedTexture() {
        return this.isTamed() ? "textures/entities/friendlycreeper1.png" : "textures/entities/friendlycreeper0.png";
    }

    public boolean getPowered() {
        return this.dataWatcher.getWatchableObjectByte(17) == 1;
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

    public void onStruckByLightning(EntityLightningBolt par1EntityLightningBolt) {
        super.onStruckByLightning(par1EntityLightningBolt);
        this.dataWatcher.updateObject(17, Byte.valueOf((byte) 1));
    }

    public boolean attackEntityAsMob(Entity par1Entity) {
        return true;
    }

    protected String getHurtSound() {
        return "mob.creeper.say";
    }

    protected String getDeathSound() {
        return "mob.creeper.death";
    }

    protected int getDropItemId() {
        return Item.gunpowder.itemID;
    }

}
